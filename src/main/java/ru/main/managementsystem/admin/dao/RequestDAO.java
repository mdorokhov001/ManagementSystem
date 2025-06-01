package ru.main.managementsystem.admin.dao;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.main.managementsystem.DataBase.DB;
import ru.main.managementsystem.admin.entity.Request;
import ru.main.managementsystem.admin.entity.RequestFilter;
import ru.main.managementsystem.admin.entity.RequestType;
import ru.main.managementsystem.admin.entity.User;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
                            rs.getString("request_type"), // ToDo из другой таблицы
                            rs.getString("status"),
                            rs.getString("priority"),
                            rs.getString("created_by"), // ToDo из другой таблицы
                            rs.getString("assigned_to"), // ToDo из другой таблицы
                            rs.getString("created_at"),
                            rs.getString("updated_at")
                    );
                    requests.add(request);
                }
            }
        }
        return requests;
    }

    private String buildFilterQuery(RequestFilter filter) {
        return """
            SELECT r.request_id, r.title, r.description, rt.type_name as request_type,
                   rs.status_name as status, r.priority,
                   uc.full_name as created_by, ua.full_name as assigned_to,
                   r.created_at, r.updated_at
            FROM requests r
            JOIN request_statuses rs ON r.status_id = rs.status_id
            JOIN request_types rt ON r.request_type_id = rt.type_id
            JOIN users uc ON r.created_by = uc.user_id
            LEFT JOIN users ua ON r.assigned_to = ua.user_id
            WHERE 1=1
            """ + buildWhereClause(filter) + """
            ORDER BY
                CASE WHEN rs.status_name = 'Открыта' THEN 1
                     WHEN rs.status_name = 'В работе' THEN 2
                     ELSE 3 END,
                CASE WHEN r.priority = 'Высокий' THEN 1
                     WHEN r.priority = 'Средний' THEN 2
                     ELSE 3 END,
                r.created_at DESC
            """;
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
            where.append(" AND r.assigned_to = ?");
        }
        if (filter.priority() != null) {
            where.append(" AND r.priority = ?");
        }

        return where.toString();
    }


    private void setFilterParameters(PreparedStatement pstmt, RequestFilter filter) throws SQLException {
        int paramIndex = 1;

        if (!filter.searchText().isEmpty()) {
            String searchPattern = "%" + filter.searchText() + "%";
            pstmt.setString(paramIndex++, searchPattern);
            pstmt.setString(paramIndex++, searchPattern);
        }
        if (filter.type() != null) {
            pstmt.setString(paramIndex++, filter.type());
        }
        for (String status : filter.statuses()) {
            pstmt.setString(paramIndex++, status);
        }
        if (filter.fromDate() != null) {
            pstmt.setTimestamp(paramIndex++, Timestamp.valueOf(filter.fromDate().atStartOfDay()));
        }
        if (filter.toDate() != null) {
            pstmt.setTimestamp(paramIndex++, Timestamp.valueOf(filter.toDate().atTime(LocalTime.MAX)));
        }
        if (filter.assignedTo() != null) {
            pstmt.setInt(paramIndex++, filter.assignedTo());
        }
        if (filter.priority() != null) {
            pstmt.setString(paramIndex++, filter.priority());
        }
    }

}
