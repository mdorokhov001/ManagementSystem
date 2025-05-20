package ru.main.managementsystem;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.main.managementsystem.Model.Model;
import ru.main.managementsystem.DataBase.DB;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {
    public TextField loginField;
    public PasswordField passwordField;
    public Button loginButton;
    public Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(event -> onLogin());
    }

    private void onLogin(){
        DB.initializeDatabase();

        Stage stage = (Stage) errorLabel.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showAdminWindow();
    }
}
