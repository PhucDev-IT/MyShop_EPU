package com.epu.oop.myshop.Main;

import com.epu.oop.myshop.Dao.Product_Dao;
import com.epu.oop.myshop.model.CreateSQL;
import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.User;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ConcurrentModificationException;
import java.util.Locale;
import java.util.Random;

public class App extends Application {
    public static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    public static String timeDay = formatter.format(Date.valueOf(LocalDate.now()));

    public static Locale lc = new Locale("vi","VN");
    public static NumberFormat numf = NumberFormat.getInstance(lc);


    private static String urlScene;


    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(urlScene));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Trang chủ");
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/com/epu/oop/myshop/image/logo-app.png")));
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        CreateSQL cr = new CreateSQL();
        if(cr.checkExistDatabase()){
            urlScene = "/com/epu/oop/myshop/GUI/PageHome.fxml";
            launch();
        }else{
            urlScene = "/com/epu/oop/myshop/GUI/setDataApp.fxml";
            launch();
        }


    }
}