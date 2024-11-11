package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerThreadBus{
    private List<ServerThread> listServerThreads;
    
    public List<ServerThread> getListServerThreads(){
        return listServerThreads;
    }
    
    public ServerThreadBus(){
        listServerThreads = new ArrayList<>();
    }
    
    void add(ServerThread serverThread) {
        listServerThreads.add(serverThread);
    }

    public int getLength() {
       return listServerThreads.size();
    }

    public void boardCast(int id, String msg) {
        for(ServerThread serverThread : Server.serverThreadBus.getListServerThreads()){
            if(serverThread.getClientNumber()== id) continue;
            else{
                try{
                    serverThread.write(msg);
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void remove(int id){
        for(int i=0; i<Server.serverThreadBus.getLength(); i++){
            if(Server.serverThreadBus.getListServerThreads().get(i).getClientNumber()==id){
                Server.serverThreadBus.listServerThreads.remove(i);
            }
        }
    }

}