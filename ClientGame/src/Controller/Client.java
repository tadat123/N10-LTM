/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import View.DashBoardFrm;
import javax.swing.JFrame;
import View.GameNoticeFrm;
import View.GamePlayFrm;
import View.LoginFrm;
import View.MainFrm;
import View.RegisterFrm;
import View.TurtorialFrm;
import View.GamePlayMutilFrm;
import Model.User;
import View.FindRoomFrm;


    
public class Client {
    public enum View{
        LOGIN,
        REGISTER,
        MAIN,
        GAMENOTICE,
        GAMEPLAY,
        TURTORIAL,
        DASHBOARD,
        GAMEMULTI,
        FINDROOM
    }
    public static User user;
    
    public static LoginFrm loginFrm;
    public static RegisterFrm registerFrm;
    public static MainFrm mainFrm;
    public static GameNoticeFrm gameNoticeFrm;
    public static GamePlayFrm gamePlayFrm;
    public static TurtorialFrm turtorialFrm;
    public static DashBoardFrm dashBoardFrm;
    public static GamePlayMutilFrm gamePlayMutilFrm;
    public static FindRoomFrm findRoomFrm;
    
    public static SocketHandle socketHandle;
    
    public Client(){
    }
    public void initView(){
        loginFrm = new LoginFrm();
        loginFrm.setVisible(true);
        socketHandle = new SocketHandle();
        socketHandle.run();
    }
    
    public static void main(String[] args) {
        new Client().initView();
    }
    
    public static JFrame getVisibleJFrame(){
        return mainFrm;
    }
       
    public static void closeAllViews(){
        if(loginFrm != null) loginFrm.dispose();
        if(registerFrm != null) registerFrm.dispose();
        if(mainFrm != null) mainFrm.dispose();
        if(gameNoticeFrm != null) gameNoticeFrm.dispose();
        if(gamePlayFrm != null) gamePlayFrm.dispose();
        if(turtorialFrm != null) turtorialFrm.dispose();
        if(dashBoardFrm != null) dashBoardFrm.dispose();
        if(gamePlayMutilFrm != null){ 
//            gamePlayMutilFrm.stopAllThread();    
            gamePlayMutilFrm.dispose();       
        }
        if(findRoomFrm != null) findRoomFrm.dispose();
}
    
    public static void closeView(View viewName){
         if(viewName != null){
            switch(viewName){
                case LOGIN:
                    loginFrm.dispose();
                    break;
                case REGISTER:
                    registerFrm.dispose();
                    break;
                case MAIN:
                    mainFrm.dispose();
                    break;
                case GAMENOTICE:
                    gameNoticeFrm.dispose();
                    break;
                case DASHBOARD:
                     dashBoardFrm.dispose();
                     break;
                case GAMEPLAY:
                     gamePlayFrm.dispose();
                     break;
                case TURTORIAL:
                     turtorialFrm.dispose();
                     break;
                case GAMEMULTI:
                     gamePlayMutilFrm.dispose();
                     break;
                case FINDROOM:
                    findRoomFrm.dispose();
                    break;
            }
        }
    }
    public static void openView(View viewName){
        if(viewName != null){
            switch(viewName){
                case LOGIN:
                    loginFrm = new LoginFrm();
                    loginFrm.setVisible(true);
                    break;
                case REGISTER:
                    registerFrm = new RegisterFrm();
                    registerFrm.setVisible(true);
                    break;
                case MAIN:
                    mainFrm = new MainFrm();
                    mainFrm.setVisible(true);
                    break;
                case DASHBOARD:
                    dashBoardFrm = new DashBoardFrm();
                    dashBoardFrm.setVisible(true);
                    break;
                case GAMEPLAY:
                    gamePlayFrm = new GamePlayFrm();
                    gamePlayFrm.setVisible(true);
                    break;
                case TURTORIAL:
                    turtorialFrm = new TurtorialFrm();
                    turtorialFrm.setVisible(true);
                    break;
                case FINDROOM:
                    findRoomFrm = new FindRoomFrm();
                    findRoomFrm.setVisible(true); 
            }
        }
    }
    
    public static void openView(View viewName, User competitor, int room_ID, int isStart, String competitorIP){
        if(viewName != null){
            switch(viewName){
                case GAMEMULTI:
                    gamePlayMutilFrm = new GamePlayMutilFrm(competitor, room_ID, isStart, competitorIP);
                    gamePlayMutilFrm.setVisible(true);
                    break;
            }
        }
    }
    
    public static void openView(View viewName, String arg1, String arg2){
    if(viewName != null){
        switch(viewName){
            case GAMENOTICE:
                gameNoticeFrm = new GameNoticeFrm(arg1, arg2);
                gameNoticeFrm.setVisible(true);
                    break;
            case LOGIN:
                    loginFrm = new LoginFrm(arg1, arg2);
                loginFrm.setVisible(true);
            }
        }
    }
}
