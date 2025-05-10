package ru.main.managementsystem;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {
    public TextField searchField;
    public ComboBox ordersTypeCombobox;
    public CheckBox openCheckbox;
    public CheckBox inProgressCheckbox;
    public CheckBox closedCheckbox;
    public DatePicker dateFrom;
    public DatePicker dateTo;
    public ComboBox assigneeCombobox;
    public TableView ordersTable;
    public CheckBox selectAllRowCheckbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
