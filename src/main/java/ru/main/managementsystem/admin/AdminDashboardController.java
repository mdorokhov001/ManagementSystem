package ru.main.managementsystem.admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ru.main.managementsystem.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {
    public Label logoLabel;
    public Button usersButton;
    public Button ordersButton;
    public Button reportsButton;
    public Button settingsButton;
    public Button logoutButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        usersButton.setOnAction(event -> onUsers());
        ordersButton.setOnAction(event -> onOrders());
        reportsButton.setOnAction(event -> onReports());
        settingsButton.setOnAction(event -> onSettingsButton());
    }

    private void onUsers(){
        System.out.println("Кнопка нажата");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("Пользователи");
    }

    private void onOrders(){
        System.out.println("Кнопка нажата");
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("Заявки");
    }
    private void onReports(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("Отчеты");
    }
    private void onSettingsButton(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("Настройки");
    }




}
