package ru.main.managementsystem.admin.entity;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class User {
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final IntegerProperty userId;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty fullName;
    private final StringProperty email;
    private final StringProperty departmentName;
    private final BooleanProperty active;
    private final SimpleObjectProperty<LocalDateTime> createdAt;
    private final SimpleObjectProperty<LocalDateTime> lastLogin;

    public User() {
        this.userId = new SimpleIntegerProperty(0);
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.fullName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.departmentName = new SimpleStringProperty("");
        this.active = new SimpleBooleanProperty(true);
        this.createdAt = new SimpleObjectProperty<>(LocalDateTime.now());
        this.lastLogin = new SimpleObjectProperty<>(null);
    }

    public User(int userId, String username, String password, String fullName, String email,
                String departmentName, boolean active, String createdAt, String lastLogin) {
        this.userId = new SimpleIntegerProperty(userId);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.fullName = new SimpleStringProperty(fullName);
        this.email = new SimpleStringProperty(email);
        this.departmentName = new SimpleStringProperty(departmentName);
        this.active = new SimpleBooleanProperty(active);
        this.createdAt = new SimpleObjectProperty<>(convertDate(createdAt));
        this.lastLogin = new SimpleObjectProperty<>(convertDate(lastLogin));
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

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }
    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getFullName() {
        return fullName.get();
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getDepartmentName() {
        return departmentName.get();
    }

    public StringProperty departmentNameProperty() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName.set(departmentName);
    }

    public Boolean isActive() {
        return active.get();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active.set(active);
    }
    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    public SimpleObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt.set(createdAt);
    }

    public LocalDateTime getLastLogin() {
        return lastLogin.get();
    }

    public SimpleObjectProperty<LocalDateTime> lastLoginProperty() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin.set(lastLogin);
    }
}
