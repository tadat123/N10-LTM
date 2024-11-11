package model;

public class User{
    private int ID;
    private String username;
    private String password;
    private String nickname;
    private int NOG; // Number of Games
    private int NOW; // Number of Wins
    private boolean isOnline;
    private boolean isPlaying;
    private int rank;

    public User(String username, String password){
        this.username =username;
        this.password= password;
    }

    public User(String username, String password, String nickname) {
       this.username = username;
       this.password = password;
       this.nickname = nickname;
    }
    
    // Getter và Setter cho rank
    public int getRank() {
        return rank;
    }

    public User() {
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
    public User(int ID, String username, String password, String nickname, int NOG, int NOW, boolean isOnline, boolean isPlaying, int rank){
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.NOG = NOG;
        this.NOW = NOW;
        this.isOnline = isOnline;
        this.isPlaying = isPlaying;
        this.rank = rank;
    }
    public User(int ID, String username, String password, String nickname, int NOG, int NOW, boolean isOnline, boolean isPlaying){
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.NOG = NOG;
        this.NOW = NOW;
        this.isOnline = isOnline;
        this.isPlaying = isPlaying;
    }
    
    // Getter và Setter cho ID
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    // Getter và Setter cho username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter và Setter cho password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter và Setter cho nickname
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    // Getter và Setter cho NOG
    public int getNOG() {
        return NOG;
    }

    public void setNOG(int NOG) {
        this.NOG = NOG;
    }

    // Getter và Setter cho NOW
    public int getNOW() {
        return NOW;
    }

    public void setNOW(int NOW) {
        this.NOW = NOW;
    }

    // Getter và Setter cho online
    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    // Getter và Setter cho playing
    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}