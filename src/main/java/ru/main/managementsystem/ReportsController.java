package ru.main.managementsystem;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    public CheckBox statisticCheckbox;
    public CheckBox loadCheckbox;
    public CheckBox analyticsCheckbox;
    public DatePicker dateFrom;
    public DatePicker dateTo;
    public ComboBox groupCombobox;
    public Button refreshButton;
    public CheckBox pdfCheckbox;
    public CheckBox excelCheckbox;
    public CheckBox htmlCheckbox;
    public Button generateButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
