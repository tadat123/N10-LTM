package model;

public class History {

    private int id;
    private int roomId;
    private String nicknamePlayer1;
    private int scorePlayer1;
    private String nicknamePlayer2;
    private int scorePlayer2;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getNicknamePlayer1() {
        return nicknamePlayer1;
    }

    public void setNicknamePlayer1(String nicknamePlayer1) {
        this.nicknamePlayer1 = nicknamePlayer1;
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public String getNicknamePlayer2() {
        return nicknamePlayer2;
    }

    public void setNicknamePlayer2(String nicknamePlayer2) {
        this.nicknamePlayer2 = nicknamePlayer2;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }
}
