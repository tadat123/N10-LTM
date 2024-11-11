package controller;

import DAO.UserDAO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

public class ServerThread implements Runnable{
    private User user;
    private Socket socketofServer;
    private int clientNumber;
    private BufferedReader is;
    private BufferedWriter os;
    private boolean isClosed;
    private UserDAO userDAO;
    private String clientIP;
    private Room room;
    
    public void setRoom(Room room){
        this.room = room;
    }
    
    public Room getRoom(){
        return room;
    }
    
    public BufferedReader getIs(){
        return is;
    }
    
    public BufferedWriter getOs(){
        return os;
    }
    
    public int getClientNumber(){
        return clientNumber;
    }
    
    public User getUser(){
        return user;
    }
    
    public String getclientIP(){
        return clientIP;
    }
    
    public void setUser(User user){
        this.user = user;
    }
    
    public ServerThread(Socket socketOfServer, int clientNumber){
        this.socketofServer = socketOfServer;
        this.clientNumber = clientNumber;
        System.out.println("Server thread number " + clientNumber + " Started");
        userDAO = new UserDAO();
        isClosed = false;
        
        
        if(this.socketofServer.getInetAddress().getHostAddress().equals("127.0.0.1")){
            clientIP = "127.0.0.1";
        }
        else{
            clientIP = this.socketofServer.getInetAddress().getHostAddress();
        }
    }
    
    public String getStringFromUser(User user1){
        return ""+user1.getID()+","+user1.getUsername()+","+user1.getPassword()+","+user1.getNickname()+","+user1.getNOG()+","+user1.getNOW()+","+user1.getRank();
    }
    
    public void goToPartnerRoom() throws IOException{
        write("go-to-room," + room.getID()+","+room.getCompetitor(this.getClientNumber()).getclientIP()+",0,"+getStringFromUser(room.getCompetitor(this.getClientNumber()).getUser()));
         room.getCompetitor(this.clientNumber).write("go-to-room,"+ room.getID()+","+this.clientIP+",1,"+getStringFromUser(user));
    }
    
    @Override
    public void run() {
        try{
            is = new BufferedReader(new InputStreamReader(socketofServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketofServer.getOutputStream()));
            System.out.println("Starting new thread conpleted!, ID:" + clientNumber);
            write("server-send-id"+"," + this.clientNumber);
            String msg;
            while(!isClosed){
                msg = is.readLine();
                if(msg == null) break;
                String[] msgSplit = msg.split(",");
                //Authen
                if(msgSplit[0].equals("client-verify")){
                    System.out.println(msg);
                    User user1 = userDAO.Auth(new User(msgSplit[1], msgSplit[2]));
                    if(user1 == null) write("wrong-user,"+msgSplit[1]+","+msgSplit[2]);
                    if(!user1.getIsOnline()){
                        write("login-success,"+getStringFromUser(user1));
                        this.user = user1;
                        userDAO.updateToOnline(this.user.getID());
                        Server.serverThreadBus.boardCast(clientNumber,"chat-server,"+user1.getNickname()+"online");
                        Server.admin.addMessage("["+user1.getID()+"]"+user1.getNickname()+"online");
                    }
                }
                //REGISTER
                if(msgSplit[0].equals("register")){
                    boolean checkdup = userDAO.checkDuplicated(msgSplit[1]);
                    if(checkdup) write("existed-username,");
                    else{
                        User userRegister =new User(msgSplit[1],msgSplit[2],msgSplit[3]);
                        userDAO.addUser(userRegister);
                        User userRegistered = userDAO.Auth(userRegister);
                        this.user = userRegistered;
                        userDAO.updateToOnline(this.user.getID());
                        Server.serverThreadBus.boardCast(clientNumber,"chat-server,"+this.user.getNickname());
                        write("login-success,"+getStringFromUser(this.user));          
                    }
                }
                //LOG OUT
                if(msgSplit[0].equals("offline")){
                    userDAO.updateToOffline(this.user.getID());
                    Server.admin.addMessage("["+user.getID()+"]"+user.getNickname()+ "log out!");
                    Server.serverThreadBus.boardCast(clientNumber, "chat-server,"+this.user.getNickname()+"log out!");
                    this.user = null;
                }
                //CHAT
                if(msgSplit[0].equals("chat-server")){
                    Server.serverThreadBus.boardCast(clientNumber, msgSplit[0]+","+ user.getNickname()+": "+ msgSplit[1]);
                    Server.admin.addMessage("["+user.getID()+"]"+user.getNickname()+": "+msgSplit[1]);
                }
                if(msgSplit[0].equals("chat")){
                    room.getCompetitor(clientNumber).write(msg);
                }
                //FINDROOM
                if (msgSplit[0].equals("quick-room")) {
                    boolean isFinded = false;
                    for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                        if (serverThread.room != null && serverThread.room.getNOU() == 1) {
                            serverThread.room.setUser2(this);
                            this.room = serverThread.room;
//                            room.increaseNumberOfGame();
                            System.out.println("Đã vào phòng " + room.getID());
                            goToPartnerRoom();
//                            userDAO.updateToPlaying(this.user.getID());
                            isFinded = true;
                            //Xử lý phần mời cả 2 người chơi vào phòng
                            break;
                        }
                    }
                    
                    if (!isFinded) {
                        this.room = new Room(this);
                        userDAO.updateToPlaying(this.user.getID());
                        System.out.println("Không tìm thấy phòng, tạo phòng mới");
                    }
                }
                //NOT FIND THE ROOM
                if (msgSplit[0].equals("cancel-room")) {
                    userDAO.updateToNotPlaying(this.user.getID());
                    System.out.println("Đã hủy phòng");
                    this.room = null;
                }
                
                // Đoạn này nằm trong phương thức xử lý tin nhắn (vd: trong phần `if-else` của server)
                if (msgSplit[0].equals("ready")) {
                    int userID = Integer.parseInt(msgSplit[1]);
                    room.setReady(userID, true); // Đánh dấu người chơi đã sẵn sàng

                // Kiểm tra nếu cả hai người chơi đều sẵn sàng
                    if (room.isBothReady()) {
                        room.increaseNumberOfGame();
                        userDAO.updateToPlaying(room.getUser1().getUser().getID());
                        userDAO.updateToPlaying(room.getUser2().getUser().getID());
        
                // Gửi thông điệp "all-ready" cho cả hai người chơi để bắt đầu trò chơi
                        room.getUser1().write("all-ready");
                        room.getUser2().write("all-ready");
                    }
                }
                if (msgSplit[0].equals("cancel-ready")) {
                    int userID = Integer.parseInt(msgSplit[1]);
                    room.cancelReady(userID); // Đặt lại trạng thái sẵn sàng của người chơi

                    // Cập nhật trạng thái trong cơ sở dữ liệu
                    userDAO.updateToNotPlaying(userID);

                    // Gửi thông điệp "not-ready" cho cả hai người chơi để cập nhật giao diện
                    room.getUser1().write("not-ready," + userID);
                    room.getUser2().write("not-ready," + userID);
                }
//                if(msgSplit[0].equals("left-room")){
//                    if (room != null) {
////                        room.setUsersToNotPlaying();
////                        room.decreaseNumberOfGame();
//                        room.getCompetitor(clientNumber).write("left-room,");
//                        room.getCompetitor(clientNumber).room = null;
//                        this.room = null;
//                    }
//                }
                   if(msgSplit[0].equals("get-rank-charts")){
                    List<User> ranks = userDAO.getUserStaticRank();
                    String res = "return-get-rank-charts,";
                    for(User user : ranks){
                        res += getStringFromUser(user)+",";
                    }
                    System.out.println(res);
                    write(res);
                }
            }
        } catch (IOException e){
               //Thay đổi giá trị cờ để thoát luồng
            isClosed = true;
            //Cập nhật trạng thái của user
            if(this.user!=null){
                userDAO.updateToOffline(this.user.getID());
                userDAO.updateToNotPlaying(this.user.getID());
                Server.serverThreadBus.boardCast(clientNumber, "chat-server,"+this.user.getNickname()+" đã offline");
                Server.admin.addMessage("["+user.getID()+"] "+user.getNickname()+" đã offline");
            }
            
            //remove thread khỏi bus
            Server.serverThreadBus.remove(clientNumber);
            System.out.println(this.clientNumber + " đã thoát");
        }
    }
    
    public void write(String msg) throws IOException {
        os.write(msg);
        os.newLine();
        os.flush();
    }

}