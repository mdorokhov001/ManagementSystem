package ru.main.managementsystem.admin.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.main.managementsystem.DataBase.DB;
import ru.main.managementsystem.admin.entity.*;

import java.sql.*;

public class RequestTypeDAO {
    public ObservableList<RequestType> getAllRequestTypes() throws SQLException {
        ObservableList<RequestType> types = FXCollections.observableArrayList();
        String sql = "SELECT type_id, type_name, description FROM request_types";

        try (Connection conn = DB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                types.add(new RequestType(
                        rs.getInt("type_id"),
                        rs.getString("type_name"),
                        rs.getString("description")
                ));
            }
        }
        return types;
    }

}
