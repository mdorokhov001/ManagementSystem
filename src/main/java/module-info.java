module ru.main.managementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.main.managementsystem to javafx.fxml;
    exports ru.main.managementsystem;
}