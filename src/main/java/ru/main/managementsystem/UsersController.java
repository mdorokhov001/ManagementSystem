package ru.main.managementsystem;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    public Button addButton;
    public Button editButton;
    public Button lockButton;
    public TextField searchField;
    public ImageView cleanButton;
    public TableView usersTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
