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

        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            System.out.println("Новое значение: " + newVal);
            switch (newVal){
               case "Пользователи" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getUsersView());
               case "Заявки"  ->  admin_parent.setCenter(Model.getInstance().getViewFactory().getRequestsView());
                case "Отчеты"  ->  admin_parent.setCenter(Model.getInstance().getViewFactory().getReportsView());
                case "Настройки" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getSettingsView());
               default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAdminMainView());
           }
        } );
    }
}
