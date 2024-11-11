package Controller;

import Model.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class SocketHandle implements Runnable{
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
    private int ID_server;
    
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
            socketOfClient = new Socket("127.0.0.1", 7777);
            System.out.println("Connect Completed");
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
            String msg;
            while(true){
                msg = is.readLine();
                if(msg == null) break;
                
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
                    Client.loginFrm.showError("Account logged in ");
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
//                     else if(Client.waitingRoomFrm!=null){
//                        Client.waitingRoomFrm.showFindedCompetitor();
//                        try {
//                            Thread.sleep(3000);
//                        } catch (InterruptedException ex) {
//                            JOptionPane.showMessageDialog(Client.waitingRoomFrm, "Lỗi khi sleep thread");
//                        }
//                    }
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
                     // Gọi phương thức để bắt đầu trò chơi ở client
                    Client.gamePlayMutilFrm.startGame();
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