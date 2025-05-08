package ru.main.managementsystem.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.main.managementsystem.AdminController;
import ru.main.managementsystem.UsersController;

import java.io.IOException;


public class ViewFactory {
    private final StringProperty adminSelectedMenuItem;
    private AnchorPane usersView;
    private AnchorPane adminMainView;

    public ViewFactory(){
        this.adminSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }

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

    public void showUsersWindow() {
        FXMLLoader loader = null;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/admin/users.fxml"));
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsersController usersController = new UsersController();
        loader.setController(usersController);
        createStage(root, "Пользователи");
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
