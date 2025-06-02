package ru.main.managementsystem.user;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ru.main.managementsystem.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {
    public Label logoLabel;
    public Button myRequestsButton;
    public Button createRequestButton;
    public Button reportsButton;
    public Button logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        myRequestsButton.setOnAction(event -> onMyRequests());
        createRequestButton.setOnAction(event -> onAddRequest());
        reportsButton.setOnAction(event -> onReports());
        logoutButton.setOnAction(event -> onLogout());
    }

    private void onMyRequests(){
        System.out.println("Кнопка нажата");
        Model.getInstance().getUserViewFactory().getUserSelectedMenuItem().set("Мои заявки");
    }

    private void onAddRequest(){
        System.out.println("Кнопка нажата");
        Model.getInstance().getUserViewFactory().getUserSelectedMenuItem().set("Создать заявку");
    }
    private void onReports(){
        Model.getInstance().getUserViewFactory().getUserSelectedMenuItem().set("Отчеты");
    }

    private void onLogout(){
        Model.getInstance().getUserViewFactory().getUserSelectedMenuItem().set("Выйти");
    }

}
