package ru.main.managementsystem.admin.entity;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Request {
    private final IntegerProperty request_id;
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty request_type;
    private final StringProperty status;
    private final StringProperty priority;
    private final StringProperty createdBy;
    private final StringProperty assignedTo;
    private final SimpleObjectProperty<LocalDateTime> createdAt;
    private final SimpleObjectProperty<LocalDateTime> updatedAt;

    public Request() {
        this.request_id = new SimpleIntegerProperty(0);
        this.title = new SimpleStringProperty("Пустая заявка");
        this.description = new SimpleStringProperty("");
        this.request_type = new SimpleStringProperty("");
        this.status = new SimpleStringProperty("");
        this.priority = new SimpleStringProperty("");
        this.createdBy = new SimpleStringProperty("");
        this.assignedTo = new SimpleStringProperty("");
        this.createdAt = new SimpleObjectProperty<>(LocalDateTime.now());
        this.updatedAt = new SimpleObjectProperty<>(null);
    }

    public Request(int requestId, String title, String description, String requestType, String status, String priority,
                   String createdBy, String assignedTo, String createdAt, String updatedAt) {
        this.request_id = new SimpleIntegerProperty(requestId);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.request_type = new SimpleStringProperty(requestType);
        this.status = new SimpleStringProperty(status);
        this.priority = new SimpleStringProperty(priority);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.assignedTo = new SimpleStringProperty(assignedTo);
        this.createdAt = new SimpleObjectProperty<>(convertDate(createdAt));
        this.updatedAt = new SimpleObjectProperty<>(convertDate(updatedAt));
    }

    private LocalDateTime convertDate(String dateStr){
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Ошибка парсинга даты: " + dateStr);
            return null;
        }
    }

    public int getRequest_id() {
        return request_id.get();
    }

    public IntegerProperty request_idProperty() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id.set(request_id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getRequest_type() {
        return request_type.get();
    }

    public StringProperty request_typeProperty() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type.set(request_type);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getPriority() {
        return priority.get();
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public String getCreatedBy() {
        return createdBy.get();
    }

    public StringProperty createdByProperty() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy.set(createdBy);
    }

    public String getAssignedTo() {
        return assignedTo.get();
    }

    public StringProperty assignedToProperty() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo.set(assignedTo);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt.set(createdAt);
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt.get();
    }

    public ObjectProperty<LocalDateTime> updatedAtProperty() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt.set(updatedAt);
    }
}
