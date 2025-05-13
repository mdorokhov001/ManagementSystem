package ru.main.managementsystem;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;


public class SettingsController implements Initializable {
    public ListView rolesList;
    public CheckBox createOrderBox;
    public CheckBox editOrderBox;
    public CheckBox setExecutorBox;
    public CheckBox closeOrderBox;
    public CheckBox generateReportBox;
    public CheckBox showStatsBox;
    public Button resetButton;
    public Button saveButton;
    public Button addRoleButton;
    public Button deleteRoleButton;
    public TableView ordersTable;
    public Button addTypeButton;
    public Button removeTypeButton;
    public FlowPane statusFlowPane;
    public TextField statusTextField;
    public ColorPicker colorPicker;
    public CheckBox isFinalBox;
    public Button addStatusButton;
    public RadioButton sqlButton;
    public RadioButton jsonButton;
    public RadioButton cvsButton;
    public TextField savePathField;
    public Button viewPathButton;
    public Button exportButton;
    public Button chooseFileButton;
    public Label fileNameLabel;
    public CheckBox cleanDataBox;
    public Button importButton;
    public TextArea logsTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
