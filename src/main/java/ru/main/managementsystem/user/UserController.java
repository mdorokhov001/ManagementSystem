package ru.main.managementsystem.user;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import ru.main.managementsystem.Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    public BorderPane user_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UserController инициализирован");

        Model.getInstance().getUserViewFactory().getUserSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            System.out.println("Новое значение: " + newVal);
            switch (newVal){
                case "Мои заявки" -> user_parent.setCenter(Model.getInstance().getUserViewFactory().getRequestsView());
                case "Создать заявку"  ->  user_parent.setCenter(Model.getInstance().getUserViewFactory().getAddRequestView());
                case "Отчеты"  ->  user_parent.setCenter(Model.getInstance().getUserViewFactory().getReportsView());
                case "Выйти" -> {
                   // ToDo сделать разлогин
                }
                //  default -> admin_parent.setCenter(Model.getInstance().getAdminViewFactory().getRequestsView());
            }
        } );
    }
}
