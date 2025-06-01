package ru.main.managementsystem;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.main.managementsystem.Model.Model;

public class MainApp extends Application {
    @Override
    public void start(Stage stage){
        Model.getInstance().getViewFactory().showLoginWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}