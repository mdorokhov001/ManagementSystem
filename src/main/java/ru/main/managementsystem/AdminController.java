package ru.main.managementsystem;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import ru.main.managementsystem.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AdminController инициализирован");

        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            System.out.println("Новое значение: " + newVal);
            switch (newVal){
               case "Пользователи" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getUsersView());
               default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAdminMainView());
           }
        } );
    }
}
