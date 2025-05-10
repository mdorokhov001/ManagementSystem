package ru.main.managementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.main.managementsystem.Model.Model;
import ru.main.managementsystem.Views.ViewFactory;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage){
        Model.getInstance().getViewFactory().showLoginWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}