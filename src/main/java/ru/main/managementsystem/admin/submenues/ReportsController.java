package ru.main.managementsystem.admin.submenues;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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
    @FXML
    private AreaChart<String, Number> areaChart;

    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupAreaChart();    // График заявок по дням
        setupPieChart();    // Распределение заявок по типам мебели
    }

    private void setupAreaChart() {
        areaChart.setTitle("Динамика заявок за последнюю неделю");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Количество заявок");

        // Данные по заявкам мебельного магазина
        series.getData().add(new XYChart.Data<>("Пн", 8));
        series.getData().add(new XYChart.Data<>("Вт", 12));
        series.getData().add(new XYChart.Data<>("Ср", 15));
        series.getData().add(new XYChart.Data<>("Чт", 18));
        series.getData().add(new XYChart.Data<>("Пт", 22));
        series.getData().add(new XYChart.Data<>("Сб", 25));
        series.getData().add(new XYChart.Data<>("Вс", 10));

        areaChart.getData().add(series);
        areaChart.setLegendVisible(false);

        // Стиль для AreaChart (опционально)
        series.getNode().setStyle("-fx-stroke: #4CAF50; -fx-stroke-width: 2px;");
    }

    private void setupPieChart() {
        pieChart.setTitle("Распределение заявок по типам");

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Доставка", 35),
                new PieChart.Data("Обслуживание", 30),
                new PieChart.Data("Дизайн", 20),
                new PieChart.Data("Консультация", 10),
                new PieChart.Data("Заказ", 5)
        );

        pieChart.setData(pieChartData);

        // Цвета для категорий мебели
        pieChartData.forEach(data -> {
            String color = switch (data.getName()) {
                case "Заказ" -> "-fx-pie-color: #FF5722;";  // оранжевый
                case "Консультация" -> "-fx-pie-color: #673AB7;";   // фиолетовый
                case "Обслуживание" -> "-fx-pie-color: #2196F3;";     // голубой
                case "Дизайн" -> "-fx-pie-color: #4CAF50;";      // зеленый
                default -> "-fx-pie-color: #FFC107;";         // желтый
            };
            data.getNode().setStyle(color);
        });

        // Дополнительные настройки PieChart
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(10);
    }


}
