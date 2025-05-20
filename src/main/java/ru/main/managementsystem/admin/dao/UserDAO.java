package ru.main.managementsystem.admin.dao;

import ru.main.managementsystem.DataBase.DB;
import ru.main.managementsystem.admin.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DepartmentDAO departmentDAO = new DepartmentDAO();

    private static final String SELECT_ALL_USERS =
            "SELECT u.user_id, u.username, u.full_name, u.password_hash, u.email, " +
                    "d.name as department_name, u.is_active, u.created_at, u.last_login " +
                    "FROM users u LEFT JOIN departments d ON u.department_id = d.department_id";

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        try (Connection conn = DB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS)) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("department_name"),
                        rs.getBoolean("is_active"),
                        rs.getString("created_at"),
                        rs.getString("last_login")
                ));
            }
        }
        return users;
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users(username, password_hash, full_name, email, department_id, is_active, created_at) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        System.out.println("Подготовка данных к записи");
        try (Connection conn = DB.getConnection()) {
            int departmentId = departmentDAO.getDepartmentIdByName(conn, user.getDepartmentName());

            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getFullName());
                pstmt.setString(4, user.getEmail());
                pstmt.setInt(5, departmentId);
                pstmt.setBoolean(6, user.isActive());
                pstmt.setString(7, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

                pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }
            }
        }
        System.out.println("Данные записались в базу");
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, password_hash = ?, full_name = ?, email = ?, " +
                "department_id = ?, is_active = ? WHERE user_id = ?";
        System.out.println("Подготовка данных к записи");
        try (Connection conn = DB.getConnection()) {

            int departmentId = departmentDAO.getDepartmentIdByName(conn, user.getDepartmentName());

            try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
                System.out.println("Коннект открыт");
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getFullName());
                pstmt.setString(4, user.getEmail());
                pstmt.setInt(5, departmentId);
                pstmt.setBoolean(6, user.isActive());
                pstmt.setInt(7, user.getUserId());

                pstmt.executeUpdate();
            }
            System.out.println("Данные записаны в базу");
        }
    }

    public List<String> getAllEmails(){
        String sql = "SELECT email FROM users";
        List<String> emails = new ArrayList<>();

        try (Connection conn = DB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("getAllEmails Коннект открыт");
            while (rs.next()) {
                emails.add(rs.getString("email"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return emails;
    }

}
