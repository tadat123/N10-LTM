package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.History;

public class HistoryDAO extends dao1 {
    public List<History> getAllHistories() {
        List<History> histories = new ArrayList<>();
        String query = "SELECT * FROM history";
        try (
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                History history = new History();
                history.setId(rs.getInt("id"));
                history.setRoomId(rs.getInt("id_room"));
                history.setNicknamePlayer1(rs.getString("nickname_player1"));
                history.setScorePlayer1(rs.getInt("score_player1"));
                history.setNicknamePlayer2(rs.getString("nickname_player2"));
                history.setScorePlayer2(rs.getInt("score_player2"));
                histories.add(history);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return histories;
    }
}