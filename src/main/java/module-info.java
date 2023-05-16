module com.epu.oop.myshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires com.jfoenix;
    requires java.sql.rowset;
    requires com.zaxxer.hikari;
    requires javafx.graphics;

    opens com.epu.oop.myshop.Main to javafx.fxml;
    exports com.epu.oop.myshop.Main;

    opens com.epu.oop.myshop.controller to javafx.fxml;
    exports com.epu.oop.myshop.controller to javafx.fxml;

    opens com.epu.oop.myshop.model to javafx.base;
}