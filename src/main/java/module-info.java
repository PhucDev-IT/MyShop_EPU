module com.epu.oop.myshop {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires com.jfoenix;
    requires com.zaxxer.hikari;
    requires org.slf4j;

    opens com.epu.oop.myshop.Main ;
    exports com.epu.oop.myshop.Main to javafx.fxml;

    opens com.epu.oop.myshop.model to javafx.base;
    exports com.epu.oop.myshop.model;

    opens com.epu.oop.myshop.controller to javafx.fxml;
    exports com.epu.oop.myshop.controller to javafx.fxml;



}