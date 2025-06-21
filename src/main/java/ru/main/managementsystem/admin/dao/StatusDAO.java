package ru.main.managementsystem.admin.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.main.managementsystem.DataBase.DB;
import ru.main.managementsystem.admin.entity.*;

import java.sql.*;

public class StatusDAO {
    public ObservableList<Status> getAllStatuses() throws SQLException {
        ObservableList<Status> statuses = FXCollections.observableArrayList();
        String sql = "SELECT status_id, status_name, is_final FROM statuses";

        try (Connection conn = DB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                statuses.add(new Status(
                        rs.getInt("status_id"),
                        rs.getString("status_name"),
                        rs.getBoolean("is_final")
                ));
            }
        }
        return statuses;
    }
}
