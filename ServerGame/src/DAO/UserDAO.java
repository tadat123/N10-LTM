package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO extends dao1{
    public UserDAO(){
        super();
    }
    public User Auth(User user){ //LOGIN
        try{
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new User(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),(rs.getInt(7) != 0),(rs.getInt(8) != 0), getRank(rs.getInt(1)));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public User getUserbyID(int ID){
        try{
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user WHERE ID=?");
            preparedStatement.setInt(1,ID);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new User(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),(rs.getInt(7) != 0),(rs.getInt(8) != 0), getRank(rs.getInt(1)));
            } 
            }catch(SQLException e){
                    e.printStackTrace();
            }
        return null;
        }
    
    public User addUser(User user){ //REGISTER
        try{
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO user (username, password, nickname) VALUES (?, ?, ?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getNickname());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private int getRank(int ID) {
        int rank = 1;
        try{
            PreparedStatement preparedStatement = con.prepareStatement("SELECT user.ID FROM user ORDER BY(user.NOG+user.NOW*10) DESC");
            ResultSet rs =preparedStatement.executeQuery();
            while(rs.next()){
                if(rs.getInt(1) == ID) return rank;
                rank++;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public void updateToOnline(int ID) {
        try{
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user SET online = 1 WHERE ID = ?");
            preparedStatement.setInt(1,ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean checkDuplicated(String username){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user WHERE username = ?");
            preparedStatement.setString(1, username);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public void updateToOffline(int ID) {
        try{
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user SET online = 0 WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    //Get and add NOW & NOG
    public int getNOW(int ID){
        try{
            PreparedStatement preparedStatement = con.prepareStatement("SELECT user.NOW FROM user where user.ID");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    public void addWinGame(int ID){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET user.NOW = ?\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, new UserDAO().getNOW(ID)+1);
            preparedStatement.setInt(2, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
        public int getNOG(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT user.NOG\n"
                    + "FROM user\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    public void addGame(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET user.NOG = ?\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, new UserDAO().getNOG(ID) + 1);
            preparedStatement.setInt(2, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public String getNickNameByID(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT user.nickname\n"
                    + "FROM user\n"
                    + "WHERE user.ID=?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    public void updateToNotPlaying(int ID){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET playing = 0\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void updateToPlaying(int ID){
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET playing = 1\n"
                    + "WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

   public List<User> getUserStaticRank() {
        List<User> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT *\n"
                    + "FROM user\n"
                    + "ORDER BY(user.NumberOfGame+user.NumberOfWin*10) DESC\n"
                    + "LIMIT 8");
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        (rs.getInt(7) != 0),
                        (rs.getInt(8) != 0),
                        getRank(rs.getInt(1))));
            }
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }
}