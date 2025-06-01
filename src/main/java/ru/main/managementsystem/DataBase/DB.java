package ru.main.managementsystem.DataBase;

import ru.main.managementsystem.admin.dao.UserDAO;
import ru.main.managementsystem.admin.entity.User;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    private static final String DB_URL = "jdbc:sqlite:management.db";
    public static void initializeDatabase() {
        try (Connection conn = DB.getConnection()) {
            createUsersTable(conn);
            createDepartmentsTable(conn);
            createRolesTable(conn);
            createPermissionsTable(conn);
            createRolePermissionsTable(conn);
            createUserRolesTable(conn);
            createRequestTypesTable(conn);
            createRequestStatusesTable(conn);
            createRequestsTable(conn);
            createDeliveryRequestsTable(conn);
            createServiceRequestsTable(conn);
            createRequestHistoryTable(conn);
            createReportsTable(conn);

            System.out.println("Database initialization completed successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }

        try {
            UserDAO userDao = new UserDAO();
            if (!userDao.adminAccountExists()) {
                createDefaultAdmin();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при проверке администратора: " + e.getMessage());
        }
    }

    private static void createDefaultAdmin() throws SQLException {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("123");
        admin.setActive(true);

        UserDAO userDao = new UserDAO();
        userDao.addUser(admin);

        System.out.println("Создана учетная запись администратора по умолчанию");
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("Вызов коннекта");
        return DriverManager.getConnection(DB_URL);
    }

    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet rs = meta.getTables(null, null, tableName, null)) {
            return rs.next();
        }
    }

    private static void createUsersTable(Connection conn) throws SQLException {
        if (tableExists(conn, "users")) return;

        String sql = "CREATE TABLE users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password_hash TEXT NOT NULL, " +
                "full_name TEXT NOT NULL, " +
                "email TEXT UNIQUE NOT NULL, " +
                "department_id INTEGER, " +
                "is_active BOOLEAN DEFAULT TRUE, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "last_login TIMESTAMP, " +
                "FOREIGN KEY (department_id) REFERENCES departments(department_id)" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createDepartmentsTable(Connection conn) throws SQLException {
        if (tableExists(conn, "departments")) return;

        String sql = "CREATE TABLE departments (" +
                "department_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT UNIQUE NOT NULL, " +
                "description TEXT" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createRolesTable(Connection conn) throws SQLException {
        if (tableExists(conn, "roles")) return;

        String sql = "CREATE TABLE roles (" +
                "role_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "role_name TEXT UNIQUE NOT NULL, " +
                "description TEXT" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createPermissionsTable(Connection conn) throws SQLException {
        if (tableExists(conn, "permissions")) return;

        String sql = "CREATE TABLE permissions (" +
                "permission_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "permission_name TEXT UNIQUE NOT NULL, " +
                "description TEXT" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createRolePermissionsTable(Connection conn) throws SQLException {
        if (tableExists(conn, "role_permissions")) return;

        String sql = "CREATE TABLE role_permissions (" +
                "role_id INTEGER NOT NULL, " +
                "permission_id INTEGER NOT NULL, " +
                "PRIMARY KEY (role_id, permission_id), " +
                "FOREIGN KEY (role_id) REFERENCES roles(role_id), " +
                "FOREIGN KEY (permission_id) REFERENCES permissions(permission_id)" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createUserRolesTable(Connection conn) throws SQLException {
        if (tableExists(conn, "user_roles")) return;

        String sql = "CREATE TABLE user_roles (" +
                "user_id INTEGER NOT NULL, " +
                "role_id INTEGER NOT NULL, " +
                "PRIMARY KEY (user_id, role_id), " +
                "FOREIGN KEY (user_id) REFERENCES users(user_id), " +
                "FOREIGN KEY (role_id) REFERENCES roles(role_id)" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createRequestTypesTable(Connection conn) throws SQLException {
        if (tableExists(conn, "request_types")) return;

        String sql = "CREATE TABLE request_types (" +
                "type_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "type_name TEXT UNIQUE NOT NULL, " +
                "description TEXT" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createRequestStatusesTable(Connection conn) throws SQLException {
        if (tableExists(conn, "request_statuses")) return;

        String sql = "CREATE TABLE request_statuses (" +
                "status_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "status_name TEXT UNIQUE NOT NULL, " +
                "description TEXT, " +
                "is_final BOOLEAN DEFAULT FALSE" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createRequestsTable(Connection conn) throws SQLException {
        if (tableExists(conn, "requests")) return;

        String sql = "CREATE TABLE requests (" +
                "request_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "description TEXT, " +
                "request_type_id INTEGER NOT NULL, " +
                "status_id INTEGER NOT NULL, " +
                "created_by INTEGER NOT NULL, " +
                "assigned_to INTEGER, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "updated_at TIMESTAMP, " +
                "priority INTEGER DEFAULT 2, " + // 1=High, 2=Medium, 3=Low
                "FOREIGN KEY (request_type_id) REFERENCES request_types(type_id), " +
                "FOREIGN KEY (status_id) REFERENCES request_statuses(status_id), " +
                "FOREIGN KEY (created_by) REFERENCES users(user_id), " +
                "FOREIGN KEY (assigned_to) REFERENCES users(user_id)" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createDeliveryRequestsTable(Connection conn) throws SQLException {
        if (tableExists(conn, "delivery_requests")) return;

        String sql = "CREATE TABLE delivery_requests (" +
                "delivery_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "request_id INTEGER NOT NULL, " +
                "delivery_address TEXT NOT NULL, " +
                "expected_date TIMESTAMP NOT NULL, " +
                "contact_person TEXT NOT NULL, " +
                "contact_phone TEXT NOT NULL, " +
                "FOREIGN KEY (request_id) REFERENCES requests(request_id) ON DELETE CASCADE" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createServiceRequestsTable(Connection conn) throws SQLException {
        if (tableExists(conn, "service_requests")) return;

        String sql = "CREATE TABLE service_requests (" +
                "service_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "request_id INTEGER NOT NULL, " +
                "service_type TEXT NOT NULL, " +
                "problem_description TEXT, " +
                "scheduled_date TIMESTAMP, " +
                "FOREIGN KEY (request_id) REFERENCES requests(request_id) ON DELETE CASCADE" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createRequestHistoryTable(Connection conn) throws SQLException {
        if (tableExists(conn, "request_history")) return;

        String sql = "CREATE TABLE request_history (" +
                "history_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "request_id INTEGER NOT NULL, " +
                "changed_by INTEGER NOT NULL, " +
                "change_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "field_changed TEXT NOT NULL, " +
                "old_value TEXT, " +
                "new_value TEXT, " +
                "change_description TEXT, " +
                "FOREIGN KEY (request_id) REFERENCES requests(request_id) ON DELETE CASCADE, " +
                "FOREIGN KEY (changed_by) REFERENCES users(user_id)" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void createReportsTable(Connection conn) throws SQLException {
        if (tableExists(conn, "reports")) return;

        String sql = "CREATE TABLE reports (" +
                "report_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "report_name TEXT NOT NULL, " +
                "report_type TEXT NOT NULL, " +
                "generated_by INTEGER NOT NULL, " +
                "generation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "parameters_used TEXT, " +
                "file_path TEXT UNIQUE, " +
                "FOREIGN KEY (generated_by) REFERENCES users(user_id)" +
                ")";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}

