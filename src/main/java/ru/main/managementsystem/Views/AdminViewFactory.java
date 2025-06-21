package ru.main.managementsystem.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.main.managementsystem.Model.Model;
import ru.main.managementsystem.admin.AdminController;
import ru.main.managementsystem.admin.submenues.UsersController;

import java.io.IOException;


public class AdminViewFactory extends ViewFactory{
    private final StringProperty adminSelectedMenuItem;
    private AnchorPane usersView;
    private AnchorPane requestsView;
    private AnchorPane reportsView;
    private AnchorPane settingsView;

    public AdminViewFactory(){
        this.adminSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }

    //окно после авторизации
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
        Model.getInstance().getViewFactory().createStage(root, "Панель администратора");
    }

    //  Окно Админ-Пользователи
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

    //  Окно Админ-Заявки
    public AnchorPane getRequestsView(){
        if (requestsView == null){
            try{
                requestsView = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/admin/requests.fxml")).load();
                System.out.println("Requests view загружен");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return requestsView;
    }
    //  Окно Админ-Отчеты
    public AnchorPane getReportsView(){
        if (reportsView == null){
            try{
                reportsView = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/admin/reports.fxml")).load();
                System.out.println("Reports view загружен");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return reportsView;
    }

    // Окно Админ-настройки
    public AnchorPane getSettingsView(){
        if (settingsView == null){
            try{
                settingsView = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/admin/settings.fxml")).load();
                System.out.println("Settings View загружен");
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return settingsView;
    }

}
