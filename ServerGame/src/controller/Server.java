package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import view.Admin;

public class Server{

    public static volatile ServerThreadBus serverThreadBus;
    public static volatile Admin admin;
    public static int ID_room;
    public static Socket socketOfServer;
    
    public static void main(String[] args) {
        ServerSocket lis = null;
        serverThreadBus = new ServerThreadBus();
        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;
        ID_room = 100;
        try{
            lis = new ServerSocket(7777);
        } catch (IOException e){
            System.out.println(e);
            System.exit(1);
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8));
        admin = new Admin();
        admin.run();
        try{
            while(true){
                socketOfServer = lis.accept();
                System.out.println(socketOfServer.getInetAddress().getHostAddress());
                ServerThread serverThread = new ServerThread(socketOfServer, clientNumber++);
                serverThreadBus.add(serverThread);
                System.out.println("Number of thread running:"+serverThreadBus.getLength());
                executor.execute(serverThread);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                lis.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}