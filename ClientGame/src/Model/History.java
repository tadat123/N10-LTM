package Model;

public class History {

    public History(int id, int id_room, String nickname_player1, int score_player1, String nickname_player2, int score_player2) {
        this.id = id;
        this.roomId = roomId;
        this.nicknamePlayer1 = nickname_player1;
        this.scorePlayer1 = score_player1;
        this.nicknamePlayer2 = nickname_player2;
        this.scorePlayer2 = score_player2;
    }
    private int id;
    private int roomId;
    private String nicknamePlayer1;
    private int scorePlayer1;
    private String nicknamePlayer2;
    private int scorePlayer2;
    private String result;

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho roomId
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    // Getter và Setter cho nicknamePlayer1
    public String getNicknamePlayer1() {
        return nicknamePlayer1;
    }

    public void setNicknamePlayer1(String nicknamePlayer1) {
        this.nicknamePlayer1 = nicknamePlayer1;
    }

    // Getter và Setter cho scorePlayer1
    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    // Getter và Setter cho nicknamePlayer2
    public String getNicknamePlayer2() {
        return nicknamePlayer2;
    }

    public void setNicknamePlayer2(String nicknamePlayer2) {
        this.nicknamePlayer2 = nicknamePlayer2;
    }

    // Getter và Setter cho scorePlayer2
    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }
}
