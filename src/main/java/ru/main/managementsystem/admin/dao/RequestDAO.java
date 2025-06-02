package ru.main.managementsystem.admin.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.main.managementsystem.DataBase.DB;
import ru.main.managementsystem.admin.entity.*;

import java.sql.*;
import java.time.LocalTime;
import java.util.stream.Collectors;

public class RequestDAO {
    public ObservableList<Request> getFilteredRequests(RequestFilter filter) throws SQLException {
        ObservableList<Request> requests = FXCollections.observableArrayList();
        String sql = buildFilterQuery(filter);

        try (Connection conn = DB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setFilterParameters(pstmt, filter);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Request request = new Request(
                            rs.getInt("request_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            new RequestType(
                                    rs.getInt("type_id"),
                                    rs.getString("type_name"),
                                    rs.getString("type_description")
                            ),
                            rs.getString("status"),
                            Priority.fromCode(rs.getInt("priority")),
                            rs.getString("created_by"),
                            rs.getString("assigned_to"),
                            rs.getString("created_at"),
                           // rs.getString("created_at"),
                            rs.getString("updated_at")
                    );
                    System.out.println(request);
                    requests.add(request);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return requests;
    }

    private String buildFilterQuery(RequestFilter filter) {
        return """
            SELECT r.request_id, r.title, r.description, rt.type_id as type_id,
                   rt.type_name as type_name, rt.description as type_description,
                   rs.status_name as status, r.priority,
                   uc.full_name as created_by, ua.full_name as assigned_to,
                   r.created_at, r.updated_at
            FROM requests r
            JOIN request_statuses rs ON r.status_id = rs.status_id
            JOIN request_types rt ON r.request_type_id = rt.type_id
            JOIN users uc ON r.created_by = uc.user_id
            LEFT JOIN users ua ON r.assigned_to = ua.user_id
            WHERE 1=1
            """ + buildWhereClause(filter)
//        """
//             ORDER BY
//                CASE WHEN rs.status_name = 'Открыта' THEN 1
//                     WHEN rs.status_name = 'В работе' THEN 2
//                     ELSE 3 END,
//                CASE WHEN r.priority = 'Высокий' THEN 1
//                     WHEN r.priority = 'Средний' THEN 2
//                     ELSE 3 END,
//                r.created_at DESC
//            """
                ;
    }

    private String buildWhereClause(RequestFilter filter) {
        StringBuilder where = new StringBuilder();

        if (!filter.searchText().isEmpty()) {
            where.append(" AND (r.title LIKE ? OR r.description LIKE ?)");
        }
        if (filter.type() != null) {
            where.append(" AND rt.type_name = ?");
        }
        if (!filter.statuses().isEmpty()) {
            where.append(" AND rs.status_name IN (")
                    .append(filter.statuses().stream().map(s -> "?").collect(Collectors.joining(",")))
                    .append(")");
        }
        if (filter.fromDate() != null) {
            where.append(" AND r.created_at >= ?");
        }
        if (filter.toDate() != null) {
            where.append(" AND r.created_at <= ?");
        }
        if (filter.assignedTo() != null) {
            where.append(" AND ua.user_id = ?");
        }
        if (filter.priority() != null) {
            where.append(" AND r.priority = ?");
        }

        return where.toString();
    }


    private void setFilterParameters(PreparedStatement pstmt, RequestFilter filter) throws SQLException {
        int paramIndex = 1;
        System.out.println("setFilterParameters starts...");
        if (!filter.searchText().isEmpty()) {
            String searchPattern = "%" + filter.searchText() + "%";
            pstmt.setString(paramIndex++, searchPattern);
            pstmt.setString(paramIndex++, searchPattern);
            System.out.println("searchPattern: " + searchPattern);
        }
        if (filter.type() != null) {
            pstmt.setString(paramIndex++, String.valueOf(filter.type()));
            System.out.println("String.valueOf(filter.type()): " + filter.type());
        }
        for (String status : filter.statuses()) {
            pstmt.setString(paramIndex++, status);
            System.out.println("status: " + status);
        }
        if (filter.fromDate() != null) {
            pstmt.setString(paramIndex++, String.valueOf(filter.fromDate().atStartOfDay()));

            System.out.println("fromDate(): " + String.valueOf(filter.fromDate().atStartOfDay()));
        }
        if (filter.toDate() != null) {
            pstmt.setString(paramIndex++, String.valueOf(filter.toDate().atTime(LocalTime.MAX)));

            System.out.println("toDate(): " + String.valueOf(filter.toDate().atTime(LocalTime.MAX)));
        }
        if (filter.assignedTo() != null) {
            pstmt.setInt(paramIndex++, filter.assignedTo());
            System.out.println("assignedTo: " + filter.assignedTo());
        }
        if (filter.priority() != null) {
            pstmt.setInt(paramIndex++, filter.priority().getCode());
            System.out.println("priority: " + filter.priority().getCode());
        }

        System.out.println(pstmt);

        System.out.println("setFilterParameters ends...");
    }

}
