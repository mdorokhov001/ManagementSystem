package ru.main.managementsystem.admin;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import ru.main.managementsystem.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public BorderPane admin_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("AdminController инициализирован");

        Model.getInstance().getAdminViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            System.out.println("Новое значение: " + newVal);
            switch (newVal){
               case "Пользователи" -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getUsersView());
               case "Заявки"  ->  admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getRequestsView());
                case "Отчеты"  ->  admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getReportsView());
                case "Настройки" -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getSettingsView());
             //  default -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getRequestsView());
           }
        } );
    }
}
