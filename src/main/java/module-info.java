module com.epu.oop.myshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires mssql.jdbc;
    requires com.jfoenix;
    requires java.sql.rowset;

    exports com.epu.oop.myshop.Main;
    exports com.epu.oop.myshop.controller to javafx.fxml;
    opens com.epu.oop.myshop.model to javafx.base;
    opens com.epu.oop.myshop.controller to javafx.fxml;
    opens com.epu.oop.myshop.Main to javafx.fxml;
}