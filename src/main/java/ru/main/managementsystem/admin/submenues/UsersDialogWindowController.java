package ru.main.managementsystem.admin.submenues;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ru.main.managementsystem.DataBase.DB;
import ru.main.managementsystem.admin.dao.DepartmentDAO;
import ru.main.managementsystem.admin.entity.User;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UsersDialogWindowController implements Initializable {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passField;
    @FXML
    public ComboBox<String> departmentDropbox;
    private final DepartmentDAO departmentDao = new DepartmentDAO();
    @FXML
    public TextField fullNameField;
    @FXML
    public TextField emailField;
    @FXML
    public CheckBox closeAfterSaveCheckBox;
    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;

    @FXML
    public Label errorLabel;

    private Stage dialogStage;
    private User user;
    private boolean saveClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setUser(User user) {
        this.user = user;
        usernameField.setText(user.getUsername());
        passField.setText(user.getPassword());
        if (user.getDepartmentName() != null && !user.getDepartmentName().isEmpty()) {
            departmentDropbox.getSelectionModel().select(user.getDepartmentName());
        }
        fullNameField.setText(user.getFullName());
        emailField.setText(user.getEmail());
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            user.setUsername(usernameField.getText());
            user.setPassword(passField.getText());
            user.setDepartmentName(departmentDropbox.getValue());
            user.setFullName(fullNameField.getText());
            user.setEmail(emailField.getText());

            saveClicked = true;
            if (closeAfterSaveCheckBox.isSelected()) {
                dialogStage.close();
            }
            errorLabel.setText("Изменения внесены");
        }
        System.out.println("handleSave Кнопка сохранить нажалась");
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<String> departments = departmentDao.getAllDepartmentNames();
            departmentDropbox.getItems().setAll(departments);
        } catch (SQLException e) {
            System.out.println("initialize Не удалось загрузить список отделов");
            e.printStackTrace();
        }
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        if (usernameField.getText() == null || usernameField.getText().isEmpty()) {
            errorMessage.append("Логин не может быть пустым!\n");
        }
        if (passField.getText() == null || passField.getText().isEmpty()) {
            errorMessage.append("Пароль не может быть пустым!\n");
        }

        if (departmentDropbox.getValue() == null) {
            errorMessage.append("Необходимо выбрать отдел!\n");
        }

        if (emailField.getText() == null || emailField.getText().isEmpty()) {
            errorMessage.append("Email не может быть пустым!\n");
        } else if (!emailField.getText().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessage.append("Некорректный формат email!\n");
        }

        if (errorMessage.length() == 0) {
            try (Connection conn = DB.getConnection()) {
                // Проверка уникальности логина
                if (isUsernameExists(conn, usernameField.getText(), user.getUserId())) {
                    errorMessage.append("Логин уже занят!\n");
                }

                // Проверка уникальности email
                if (isEmailExists(conn, emailField.getText(), user.getUserId())) {
                    errorMessage.append("Email уже используется!\n");
                }
            } catch (SQLException e) {
                errorMessage.append("Ошибка проверки данных в базе!\n");
                e.printStackTrace();
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            errorLabel.setText("Ошибка: " + errorMessage);
            return false;
        }
    }

    private boolean isUsernameExists(Connection conn, String username, int currentUserId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND user_id != ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, currentUserId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private boolean isEmailExists(Connection conn, String email, int currentUserId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ? AND user_id != ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setInt(2, currentUserId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

}
