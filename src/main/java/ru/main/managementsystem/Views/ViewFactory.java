package ru.main.managementsystem.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.main.managementsystem.AdminController;

import java.io.IOException;


public class ViewFactory {
    private AnchorPane usersView;

    public ViewFactory(){}

    public AnchorPane getUsersView(){
        if (usersView == null){
            try{
                usersView = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/admin/users.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return usersView;
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
}
