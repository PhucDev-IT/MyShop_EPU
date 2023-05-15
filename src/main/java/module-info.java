module com.epu.oop.myshop {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires com.jfoenix;
    requires com.zaxxer.hikari;
<<<<<<< Updated upstream
=======
    requires org.slf4j;

>>>>>>> Stashed changes

    exports com.epu.oop.myshop.Main;
    exports com.epu.oop.myshop.controller to javafx.fxml;
    opens com.epu.oop.myshop.model to javafx.base;
<<<<<<< Updated upstream
    opens com.epu.oop.myshop.controller to javafx.fxml;
    opens com.epu.oop.myshop.Main to javafx.fxml;
=======
    exports com.epu.oop.myshop.model;
>>>>>>> Stashed changes
}