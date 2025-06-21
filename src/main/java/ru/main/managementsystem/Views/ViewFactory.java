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

    public ViewFactory(){

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

    void createStage(Parent root, String title){
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
