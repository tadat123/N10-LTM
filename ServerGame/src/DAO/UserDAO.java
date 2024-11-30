package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.User;

public class UserDAO extends dao1 {

    public UserDAO() {
        super();
    }

    public User Auth(User user) { //LOGIN
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), (rs.getInt(7) != 0), (rs.getInt(8) != 0), getRank(rs.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserbyID(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user WHERE ID=?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), (rs.getInt(7) != 0), (rs.getInt(8) != 0), getRank(rs.getInt(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User addUser(User user) { //REGISTER
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO user (username, password, nickname) VALUES (?, ?, ?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getNickname());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getRank(int ID) {
        int rank = 1;
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT user.ID FROM user ORDER BY(user.NOG+user.NOW*10) DESC");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) == ID) {
                    return rank;
                }
                rank++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateToOnline(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user SET online = 1 WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkDuplicated(String username) {
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
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user SET online = 0 WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Get and add NOW & NOG
    public int getNOW(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT user.NOW FROM user where user.ID = ?");
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addWinGame(int ID) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE user\n"
                    + "SET user.NOW = ?\n"
                    + "WHERE user.ID = ?");
            preparedStatement.setInt(1, new UserDAO().getNOW(ID) + 1);
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

    public void updateToNotPlaying(int ID) {
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

    public void updateToPlaying(int ID) {
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
                    + "ORDER BY(user.NOG+user.NOW*10) DESC\n"
                    + "LIMIT 6");
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
            e.printStackTrace();
        }
        return list;
    }
//
//    public String saveScore(int id_room, int player_id, int score) {
//        try {
//            // Lưu điểm vào bảng tạm
//            PreparedStatement ps = con.prepareStatement(
//                    "INSERT INTO temp_game_scores (id_room, player_id, score) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE score = ?"
//            );
//            ps.setInt(1, id_room);
//            ps.setInt(2, player_id);
//            ps.setInt(3, score);
//            ps.setInt(4, score); // Cập nhật nếu đã tồn tại
//            int rows = ps.executeUpdate();
//            System.out.println("Rows affected by saveScore: " + rows);
//
//            // Kiểm tra xem cả hai người chơi đã gửi điểm hay chưa
//            PreparedStatement checkPs = con.prepareStatement(
//                    "SELECT COUNT(*) FROM temp_game_scores WHERE id_room = ?"
//            );
//            checkPs.setInt(1, id_room);
//            ResultSet rs = checkPs.executeQuery();
//
//            if (rs.next()) {
//                int playerCount = rs.getInt(1);
//                System.out.println("Number of players in room " + id_room + ": " + playerCount);
//                if (playerCount == 2) {
//                    return processGameResult(id_room);
//                }
//            }
//            return "Waiting for the other player's result...";
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return "Error saving score.";
//        }
//    }
//
//    public String processGameResult(int id_room) {
//        try {
//            // Lấy điểm và ID người chơi từ bảng tạm
//            PreparedStatement ps = con.prepareStatement(
//                    "SELECT player_id, score FROM temp_game_scores WHERE id_room = ?"
//            );
//            ps.setInt(1, id_room);
//            ResultSet rs = ps.executeQuery();
//
//            Map<Integer, Integer> scores = new HashMap<>();
//            while (rs.next()) {
//                scores.put(rs.getInt("player_id"), rs.getInt("score"));
//            }
//
//            if (scores.size() == 2) {
//                List<Integer> players = new ArrayList<>(scores.keySet());
//                int player1ID = players.get(0);
//                int player2ID = players.get(1);
//                int player1Score = scores.get(player1ID);
//                int player2Score = scores.get(player2ID);
//
//                // Lấy nickname từ bảng user
//                String nicknamePlayer1 = getUserNickName(player1ID);
//                String nicknamePlayer2 = getUserNickName(player2ID);
//
//                // So sánh điểm và xác định kết quả
//                String result;
//                if (player1Score > player2Score) {
//                    result = nicknamePlayer1 + "," + "wins" + "," + nicknamePlayer2 + ",";
//                    saveGameHistory(id_room, nicknamePlayer1, player1Score, nicknamePlayer2, player2Score, result);
//                } else if (player1Score < player2Score) {
//                    result = nicknamePlayer2 + "," + "wins" + "," + nicknamePlayer1 + ",";
//                    saveGameHistory(id_room, nicknamePlayer1, player1Score, nicknamePlayer2, player2Score, result);
//                } else {
//                    result = nicknamePlayer1 + "," + "draw" + "," + nicknamePlayer2 + ",";
//                    saveGameHistory(id_room, nicknamePlayer1, player1Score, nicknamePlayer2, player2Score, result);
//                }
//
//                // Xóa dữ liệu tạm
//                PreparedStatement deletePs = con.prepareStatement(
//                        "DELETE FROM temp_game_scores WHERE id_room = ?"
//                );
//                deletePs.setInt(1, id_room);
//                deletePs.executeUpdate();
//                System.out.println(result);
//                return result;
//            }
//
//            return "Error: Not enough data.";
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return "Error processing game result.";
//        }
//    }

    public void saveGameHistory(int idRoom, String nicknamePlayer1, int scorePlayer1, String nicknamePlayer2, int scorePlayer2) {
        try {
            String query = "INSERT INTO history (id_room, nickname_player1, score_player1, nickname_player2, score_player2) "
                    + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, idRoom);
            preparedStatement.setString(2, nicknamePlayer1);
            preparedStatement.setInt(3, scorePlayer1);
            preparedStatement.setString(4, nicknamePlayer2);
            preparedStatement.setInt(5, scorePlayer2);

            preparedStatement.executeUpdate();
            System.out.println("Game history saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// Lấy nickname từ bảng user
    private String getUserNickName(int userID) throws SQLException {
        String query = "SELECT nickname FROM user WHERE ID = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getString("nickname") : "Unknown";
    }
}
