package ru.main.managementsystem.admin.submenues;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.main.managementsystem.admin.dao.UserDAO;
import ru.main.managementsystem.admin.entity.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UsersController implements Initializable {
    @FXML
    public ImageView refreshButton;
    @FXML
    public Button addButton;
    @FXML
    public Button editButton;
    @FXML
    public Button lockButton;
    @FXML
    public TextField searchField;
    @FXML
    public ImageView cleanButton;
    @FXML
    public TableView<User> usersTable;

    private ObservableList<User> usersData = FXCollections.observableArrayList();
    private UserDAO userDAO = new UserDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillUsersTable();
        refreshUsers();
    }

    private void fillUsersTable(){
        usersTable.setItems(usersData);
    }

    @FXML
    private void refreshUsers() {
        try {
            List<User> users = userDAO.getAllUsers();
            usersData.clear();
            usersData.addAll(users);
            System.out.println("Данные  загружены");
        } catch (SQLException e) {
            System.out.println("Ошибка загрузки данных " + e.getMessage());
        }
    }

    @FXML
    private void handleRefresh() {
        refreshUsers();
    }

    @FXML
    private void handleAddUser() {
        User newUser = new User();
        newUser.setActive(true);
        showUserForm(newUser);

    }

    @FXML
    private void handleEditUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            showUserForm(selectedUser);
        }
    }

    @FXML
    private void handleBlockUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                selectedUser.setActive(!selectedUser.isActive());
                userDAO.updateUser(selectedUser);
                usersTable.refresh();
            } catch (SQLException e) {
                System.out.println("Не удалось обновить статус пользователя");
                e.printStackTrace();
            }
        }
    }

    private void showUserForm(User user) {
        System.out.println("Кнопка нажата");
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/main/managementsystem/admin/users_dialog_window.fxml"));
        AnchorPane page = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle(user.getUserId() == 0 ? "Добавить пользователя" : "Редактировать пользователя");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(usersTable.getScene().getWindow());

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        UsersDialogWindowController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUser(user);

        dialogStage.showAndWait();

        if (controller.isSaveClicked()) {
            System.out.println("showUserForm Подготовка добавлению в базу");
            try{
            if (user.getUserId() == 0) {
                System.out.println("showUserForm Добавление нового");
                userDAO.addUser(user);
                usersData.add(user);
            } else {
                System.out.println("showUserForm Апдейт существующего");
                userDAO.updateUser(user);
                usersTable.refresh();
            }}catch (SQLException e){
                System.out.println("showUserForm Не удалось сохранить пользователя: " + e.getMessage());
                e.printStackTrace();
            }
        }
        } catch (IOException e) {
            System.out.println("showUserForm Не удалось загрузить форму");
            e.printStackTrace();
        }
    }
}
