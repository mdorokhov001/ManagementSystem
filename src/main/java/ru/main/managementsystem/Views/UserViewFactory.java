package ru.main.managementsystem.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import ru.main.managementsystem.Model.Model;
import ru.main.managementsystem.user.UserController;

import java.io.IOException;


public class UserViewFactory extends ViewFactory{
    private final StringProperty userSelectedMenuItem;
    private AnchorPane addRequestView;
    private AnchorPane requestsView;
    private AnchorPane reportsView;

    public UserViewFactory(){
        this.userSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getUserSelectedMenuItem(){
        return userSelectedMenuItem;
    }

    //окно после авторизации
    public void showUserWindow() {
        FXMLLoader loader = null;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/user/user.fxml"));
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UserController userController = new UserController();
        loader.setController(userController);
        Model.getInstance().getViewFactory().createStage(root, "Панель пользователя");
    }


    //  Окно Заявки
    public AnchorPane getRequestsView(){
        if (requestsView == null){
            try{
                requestsView = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/user/requests.fxml")).load();
                System.out.println("Requests view загружен");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return requestsView;
    }

    //  Окно создать Заявку
    public AnchorPane getAddRequestView(){
        if (addRequestView == null){
            try{
                addRequestView = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/user/add_request.fxml")).load();
                System.out.println("Add Request view загружен");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return addRequestView;
    }

    //  Окно Отчеты
    public AnchorPane getReportsView(){
        if (reportsView == null){
            try{
                reportsView = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/user/reports.fxml")).load();
                System.out.println("Reports view загружен");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return reportsView;
    }

}
