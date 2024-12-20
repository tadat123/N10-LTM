/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.UserDAO;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author tadat
 */
public class Room {

    private int ID;
    private ServerThread user1;
    private ServerThread user2;
    private UserDAO userDAO;
    private boolean isUser1Ready = false;
    private boolean isUser2Ready = false;

    public int getID() {
        return ID;
    }

    public ServerThread getUser1() {
        return user1;
    }

    public ServerThread getUser2() {
        return user2;
    }

    public Room(ServerThread user1) {
        System.out.println("Create completed:" + Server.ID_room);
        this.ID = Server.ID_room++;
        userDAO = new UserDAO();
        this.user1 = user1;
        this.user2 = null;
    }

    public int getNOU() {
        return user2 == null ? 1 : 2;
    }

    public void setUser2(ServerThread user2) {
        this.user2 = user2;
    }

    public void boardCast(String message) {
        try {
            user1.write(message);
            user2.write(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int getCompetitorID(int ID_ClientNumber) {
        if (user1.getClientNumber() == ID_ClientNumber) {
            return user2.getUser().getID();
        }
        return user1.getUser().getID();
    }

    public ServerThread getCompetitor(int ID_ClientNumber) {
        if (user1.getClientNumber() == ID_ClientNumber) {
            return user2;
        }
        return user1;
    }

    public void increaseNumberOfGame() {
        userDAO.addGame(user1.getUser().getID());
        userDAO.addGame(user2.getUser().getID());
    }

    public void setReady(int userID, boolean ready) {
        if (user1 != null && user1.getUser().getID() == userID) {
            isUser1Ready = ready;
        } else if (user2 != null && user2.getUser().getID() == userID) {
            isUser2Ready = ready;
        }
    }

    // Hàm kiểm tra nếu cả hai người chơi đã sẵn sàng
    public boolean isBothReady() {
        return isUser1Ready && isUser2Ready;
    }

    // Hàm để hủy trạng thái sẵn sàng (cancel-ready)
    public void cancelReady(int userID) {
        if (user1 != null && user1.getUser().getID() == userID) {
            isUser1Ready = false;
        } else if (user2 != null && user2.getUser().getID() == userID) {
            isUser2Ready = false;
        }
    }

    public void setUsersToNotPlaying() {
        userDAO.updateToNotPlaying(user1.getUser().getID());
        if (user2 != null) {
            userDAO.updateToNotPlaying(user2.getUser().getID());
        }
    }

    public void setUsersToPlaying() {
        userDAO.updateToPlaying(user1.getUser().getID());
        if (user2 != null) {
            userDAO.updateToPlaying(user2.getUser().getID());
        }
    }
    private Map<Integer, Integer> playerScores = new HashMap<>();

    public void updateScore(int userId, int score) {
        playerScores.put(userId, score);
    }

    public boolean isBothPlayersScored() {
        return playerScores.size() == 2;
    }

    public String calculateResult() {
        Object[] players = playerScores.keySet().toArray();
        int player1Id = (int) players[0];
        int player2Id = (int) players[1];

        int score1 = playerScores.get(player1Id);
        int score2 = playerScores.get(player2Id);
        
        String nicknamePlayer1 = userDAO.getNickNameByID(player1Id);
        String nicknamePlayer2 = userDAO.getNickNameByID(player2Id);
        
        userDAO.saveGameHistory(Server.ID_room, nicknamePlayer1, score1, nicknamePlayer2, score2);
        if (score1 > score2) {
            userDAO.addWinGame(player1Id);
            return "win," + player1Id;
        } else if (score1 < score2) {
            userDAO.addWinGame(player2Id); 
            return "win," + player2Id;
        } else {
            return "draw," + 0;
        }
    }

    public void resetScores() {
        playerScores.clear();
    }
}
