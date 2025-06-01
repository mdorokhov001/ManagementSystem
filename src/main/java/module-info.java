module ru.main.managementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens ru.main.managementsystem to javafx.fxml;
    exports ru.main.managementsystem;
    exports ru.main.managementsystem.admin;
    opens ru.main.managementsystem.admin to javafx.fxml;
    exports ru.main.managementsystem.admin.submenues;
    exports ru.main.managementsystem.admin.dao;
    exports ru.main.managementsystem.admin.entity;
    opens ru.main.managementsystem.admin.submenues to javafx.fxml;
    opens ru.main.managementsystem.admin.dao to javafx.fxml;
    opens ru.main.managementsystem.admin.entity to javafx.fxml;
}