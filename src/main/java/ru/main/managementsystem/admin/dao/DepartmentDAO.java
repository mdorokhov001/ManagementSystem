package ru.main.managementsystem.admin.dao;

import ru.main.managementsystem.DataBase.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    public List<String> getAllDepartmentNames() throws SQLException {
        List<String> departments = new ArrayList<>();
        String sql = "SELECT name FROM departments";

        try (Connection conn = DB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("getAllDepartmentNames Коннект открыт");
            while (rs.next()) {
                departments.add(rs.getString("name"));
            }
        }
        System.out.println("getAllDepartmentNames Данные загружены");
        return departments;
    }

    public int getDepartmentIdByName(Connection conn, String name) throws SQLException {
        String sql = "SELECT department_id FROM departments WHERE name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }
}
