package ru.main.managementsystem;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.main.managementsystem.Model.Model;
import ru.main.managementsystem.DataBase.DB;
import ru.main.managementsystem.admin.dao.UserDAO;
import ru.main.managementsystem.admin.entity.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AuthController implements Initializable {
    public TextField loginField;
    public PasswordField passwordField;
    public Button loginButton;
    public Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(event -> onLogin());
        DB.initializeDatabase();

    }

    private void onLogin() {
        try {
            // 1. Валидация ввода
            if (isNullOrEmpty(loginField.getText()) || isNullOrEmpty(passwordField.getText())) {
                errorLabel.setText("Заполните все поля");
                return;
            }

            // 2. Подготовка данных
            String username = loginField.getText().trim();
            String password = passwordField.getText();

            try {
                // 3. Аутентификация
                User authenticatedUser = new UserDAO().authenticate(username, password);

                if (authenticatedUser != null) {
                    // 4. Успешная авторизация
                    Model model = Model.getInstance();
                    Stage currentStage = (Stage) errorLabel.getScene().getWindow();

                    model.setCurrentUser(authenticatedUser);
                    model.getViewFactory().closeStage(currentStage);

                    if (authenticatedUser.isAdmin()) {
                        model.getViewFactory().showAdminWindow();
                    } else {
                        model.getViewFactory().showAdminWindow(); // ToDo : user окно
                    }
                } else {
                    errorLabel.setText("Неверные учётные данные");
                }
            } finally {
                // 5. Очистка пароля из памяти
                password = "";
                passwordField.clear();
            }
        } catch (SQLException e) {
            errorLabel.setText("Ошибка системы. Попробуйте позже");
            System.out.println("Ошибка авторизации" + e);
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }


}
