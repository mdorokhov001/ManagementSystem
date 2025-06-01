package ru.main.managementsystem.admin.submenues;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import ru.main.managementsystem.admin.dao.RequestDAO;
import ru.main.managementsystem.admin.dao.UserDAO;
import ru.main.managementsystem.admin.entity.Request;
import ru.main.managementsystem.admin.entity.RequestFilter;
import ru.main.managementsystem.admin.entity.RequestType;
import ru.main.managementsystem.admin.entity.User;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class RequestsController implements Initializable {
    // Кнопки управления
    @FXML public ImageView refreshButton;
    @FXML public Button viewRequestButton;
    @FXML public Button addButton;
    @FXML public Button editButton;
    @FXML public Button deleteButton;
    // Статус
    @FXML public CheckBox openCheckbox;
    @FXML public CheckBox inProgressCheckbox;
    @FXML public CheckBox closedCheckbox;
    // Дата
    @FXML public DatePicker dateFrom;
    @FXML public DatePicker dateTo;
    // Списки
    @FXML public ComboBox<User> assigneeCombobox;
    @FXML public ComboBox<String> requestTypeCombobox;
    @FXML public ComboBox<String> priorityCombobox;
    // Поиск
    @FXML public TextField searchField;
    @FXML public ImageView cleanButton;
    // Таблица
    @FXML public TableView<Request> requestsTable;

    private final RequestDAO requestDao = new RequestDAO();
    private final UserDAO userDao = new UserDAO();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTableColumns();
        setupFilterControls();
        loadInitialData();
    }

    private void configureTableColumns() {
        // Настройка форматирования дат
        TableColumn<Request, LocalDateTime> createdAtCol = (TableColumn<Request, LocalDateTime>) requestsTable.getColumns()
                .stream().filter(c -> "createdAtColumn".equals(c.getId())).findFirst().orElseThrow();

        createdAtCol.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS");

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : formatter.format(item));
            }
        });
    }

    private void setupFilterControls() {
        // Реакция на изменение фильтров
        List<Node> controls = List.of(
                searchField, requestTypeCombobox, priorityCombobox,
                openCheckbox, inProgressCheckbox, closedCheckbox,
                dateFrom, dateTo, assigneeCombobox
        );

        for (Node control : controls) {
            if (control instanceof TextInputControl) { // Для TextField и подобных
                ((TextInputControl) control).textProperty().addListener((obs, oldVal, newVal) -> refreshRequests());
            } else if (control instanceof ComboBoxBase<?>) { // Для ComboBox, DatePicker
                ((ComboBoxBase<?>) control).valueProperty().addListener((obs, oldVal, newVal) -> refreshRequests());
            } else if (control instanceof CheckBox) {
                ((CheckBox) control).selectedProperty().addListener((obs, oldVal, newVal) -> refreshRequests());
            }
        }
    }

    private void loadInitialData() {
        try {
            // Загрузка типов заявок
            requestTypeCombobox.getItems().setAll("Обслуживание", "Дизайн", "Доставка"); // ToDO Взять данные из таблицы

            // Загрузка приоритетов
            priorityCombobox.getItems().setAll("Высокий", "Средний", "Низкий");

            // Загрузка ответственных
            assigneeCombobox.setConverter(new StringConverter<>() {
                @Override
                public String toString(User user) {
                    return user == null ? "Не назначено" : user.getFullName();
                }

                @Override
                public User fromString(String string) {
                    return null;
                }
            });
            assigneeCombobox.getItems().setAll(userDao.getAllActiveUsers());

            refreshRequests();
        } catch (SQLException e) {
            System.err.println("Ошибка загрузки данных " + e.getMessage());
        }
    }

    @FXML
    private void refreshRequests() {
        try {
            RequestFilter filter = new RequestFilter(
                    searchField.getText(),
                    requestTypeCombobox.getValue(),
                    getSelectedStatuses(),
                    dateFrom.getValue(),
                    dateTo.getValue(),
                    assigneeCombobox.getValue() != null ? assigneeCombobox.getValue().getUserId() : null,
                    priorityCombobox.getValue()
            );

            requestsTable.setItems(requestDao.getFilteredRequests(filter));
        } catch (SQLException e) {
            System.err.println("Ошибка загрузки заявок " + e.getMessage());
        }
    }

    private Set<String> getSelectedStatuses() {
        Set<String> statuses = new HashSet<>();
        if (openCheckbox.isSelected()) statuses.add("Открыта");
        if (inProgressCheckbox.isSelected()) statuses.add("В работе");
        if (closedCheckbox.isSelected()) statuses.add("Закрыта");
        return statuses;
    }

    public void handleRefresh(ActionEvent actionEvent) {
    }

    public void hadleViewRequest(ActionEvent actionEvent) {
    }

    public void handleAddUser(ActionEvent actionEvent) {
    }

    public void handleEditUser(ActionEvent actionEvent) {
    }

    public void handleDeleteUser(ActionEvent actionEvent) {
    }


    // ToDo на таблице только основные данные, все данные в модальном окне
}
