package ru.main.managementsystem.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.main.managementsystem.admin.AdminController;
import ru.main.managementsystem.admin.submenues.UsersController;

import java.io.IOException;


public class ViewFactory {
    private final StringProperty adminSelectedMenuItem;
    private AnchorPane usersView;
    private AnchorPane ordersView;
    private AnchorPane reportsView;
    private AnchorPane settingsView;
    private AnchorPane adminMainView;


    public ViewFactory(){
        this.adminSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }

    //  Окно Админ
    public AnchorPane getAdminMainView(){
        if (adminMainView == null){
            try{
                adminMainView = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/admin/admin_main.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return adminMainView;
    }

    //  Окно Админ-Пользователи
    public AnchorPane getUsersView(){
        if (usersView == null){
            try{
                usersView = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/admin/users.fxml")).load();
                System.out.println("Users view загружен");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return usersView;
    }

    //  Окно Админ-Заявки
    public AnchorPane getOrdersView(){
        if (ordersView == null){
            try{
                ordersView = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/admin/orders.fxml")).load();
                System.out.println("Orders view загружен");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return ordersView;
    }
    //  Окно Админ-Отчеты
    public AnchorPane getReportsView(){
        if (reportsView == null){
            try{
                reportsView = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/admin/reports.fxml")).load();
                System.out.println("Reports view загружен");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return reportsView;
    }

    // Окно Админ-настройки
    public AnchorPane getSettingsView(){
        if (settingsView == null){
            try{
                settingsView = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/admin/settings.fxml")).load();
                System.out.println("Settings View загружен");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return settingsView;
    }
    public void showLoginWindow() {
        FXMLLoader loader = null;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/auth.fxml"));
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        createStage(root, "Авторизация");

    }

    //окно после авторизации
    public void showAdminWindow() {
        FXMLLoader loader = null;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/admin/admin.fxml"));
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(root, "Панель администратора");
    }

    //окно после авторизации
    public void showUserWindow() {
        FXMLLoader loader = null;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/users/user.fxml"));
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsersController usersController = new UsersController();
        loader.setController(usersController);
        createStage(root, "Панель пользователя");
    }


    private void createStage(Parent root, String title){
        Scene scene = null;
        try{
            scene = new Scene(root);
        } catch (Exception e){
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();
    }

}
