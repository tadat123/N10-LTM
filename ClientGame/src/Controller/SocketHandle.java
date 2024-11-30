package Controller;

import Model.History;
import Model.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class SocketHandle implements Runnable{
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
    private int ID_server;
    
    public List<User> getListRank(String[] msg){
        List<User> friend = new ArrayList<>();
        for(int i=1; i<msg.length; i=i+7){
            friend.add(new User(Integer.parseInt(msg[i]),
                msg[i+1],
                msg[i+2],
                msg[i+3],
                Integer.parseInt(msg[i+4]),
                Integer.parseInt(msg[i+5]),
                Integer.parseInt(msg[i+6])
                ));
        }
        return friend;
    }
    public User getUserFromString(int start, String[] msg) {
        return new User(Integer.parseInt(msg[start]),
        msg[start+1],
        msg[start+2],
        msg[start+3],
        Integer.parseInt(msg[start+4]),
        Integer.parseInt(msg[start+5]),
        Integer.parseInt(msg[start+6]));
    }

    /**
     *
     */
    @Override
    public void run(){
        try{
            socketOfClient = new Socket("localhost", 7777);
            System.out.println("Connect Completed");
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
            String msg;
            while(true){
                msg = is.readLine();
                if(msg == null) break;
                System.out.println(msg);
                
                String[] msgSplit = msg.split(",");
                if(msgSplit[0].equals("server-send-id")){
                    ID_server = Integer.parseInt(msgSplit[1]);
                }
                
                //LOGIN
                if(msgSplit[0].equals("login-success")){
                    System.out.println("LOGIN SUCCESS!");
                    Client.closeAllViews();
                    User user = getUserFromString(1,msgSplit);
                    Client.user = user;
                    Client.openView(Client.View.MAIN);
                }
                //CASE ERROR LOGIN
                //Wrong info
                if(msgSplit[0].equals("wrong-user")){
                    System.out.println("WRONG INFORMATION!");
                    Client.closeView(Client.View.GAMENOTICE);
                    Client.openView(Client.View.LOGIN,msgSplit[1],msgSplit[2]);
                    Client.loginFrm.showError("Username or password inacurated ");
                }
                //Duplicated login
                if(msgSplit[0].equals("duplicate-login")){
                    System.out.println("LOGGED IN!");
                    Client.closeView(Client.View.GAMENOTICE);
                    Client.openView(Client.View.LOGIN,msgSplit[1],msgSplit[2]);
                    Client.loginFrm.showError("Account logged in");
                }
                //CASE ERROR REGISTER
                //Username existed
                if(msgSplit[0].equals("existed-username")){
                    Client.closeAllViews();
                    Client.openView(Client.View.REGISTER);
                    JOptionPane.showMessageDialog(Client.registerFrm, "Another user have username!");
                }
                //CHAT SERVER
                if(msgSplit[0].equals("chat-server")){
                    if(Client.mainFrm!=null){
                        Client.mainFrm.addMessage(msgSplit[1]);
                    }
                }
                //CHAT
                if(msgSplit[0].equals("chat")){
                    Client.gamePlayMutilFrm.addMessage(msgSplit[1]);
                }
                
                if(msgSplit[0].equals("go-to-room")){
                    System.out.println("Vào phòng");
                    int roomID = Integer.parseInt(msgSplit[1]);
                    String competitorIP = msgSplit[2];
                    int isStart = Integer.parseInt(msgSplit[3]);
                    
                    User competitor = getUserFromString(4, msgSplit);
                    if(Client.findRoomFrm!=null){
                        Client.findRoomFrm.showFindedRoom();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            JOptionPane.showMessageDialog(Client.findRoomFrm, "Lỗi khi sleep thread");
                        }
                    }
                    Client.closeAllViews();
                    System.out.println("Đã vào phòng: "+roomID);
                    //Xử lý vào phòng
                    Client.openView(Client.View.GAMEMULTI
                            , competitor
                            , roomID
                            ,isStart
                            ,competitorIP);
//                  Client.gameClientFrm.newgame();
                }
                if (msgSplit[0].equals("all-ready")) {
                    JOptionPane.showMessageDialog(null, "Cả hai người chơi đã sẵn sàng! Trò chơi bắt đầu.");
                    Client.gamePlayMutilFrm.startGame();
                }
                if(msgSplit[0].equals("return-get-rank-charts")){
                    if(Client.dashBoardFrm!=null){
                        Client.dashBoardFrm.setDataToTable(getListRank(msgSplit));
                    }
                }
                if (msgSplit[0].equals("return-history")) {
                    List<History> data = new ArrayList<>();
                    for (int i = 1; i < msgSplit.length; i += 6) {
                        data.add(new History(
                            Integer.parseInt(msgSplit[i]),
                            Integer.parseInt(msgSplit[i+1]),
                            msgSplit[i+2],
                            Integer.parseInt(msgSplit[i+3]),
                            msgSplit[i+4],
                            Integer.parseInt(msgSplit[i+5])
                        ));
                    }
                    Client.historyFrm.setDataToTable(data);
                }
                
                if(msgSplit[0].equals("return-game-result")){
                   String trangthai = msgSplit[1];
                   String winnerID = msgSplit[2];
                   Client.gamePlayMutilFrm.handleGameResult(trangthai, winnerID);
                }
            }
        }
        catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    
    
    public void write(String message) throws IOException{
        os.write(message);
        os.newLine();
        os.flush();
    }

    public Socket getSocketOfClient() {
        return socketOfClient;
    }

}