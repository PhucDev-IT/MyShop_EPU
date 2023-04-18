package com.epu.oop.myshop.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epu.oop.myshop.Database.JDBCUtil;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Test extends Application {







    private ObservableList<Product> products = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException {
        TableView<Product> tableView = new TableView<>();
        TableColumn<Product, String> nameCol = new TableColumn<>("Tên sản phẩm");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Product, Integer> quantityCol = new TableColumn<>("Số lượng");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TableColumn<Product, Double> priceCol = new TableColumn<>("Giá bán");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(nameCol, quantityCol, priceCol);
        tableView.setItems(products);

        Parent root = new VBox(tableView);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Lấy dữ liệu từ cơ sở dữ liệu và thêm vào danh sách sản phẩm
        try {
            Connection conn = JDBCUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery ("SELECT * FROM Product " +
                    " WHERE ID= 3 " +
                    "AND p.Statuss = 'ON' ");
            while (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                products.add(new Product(name, quantity, price));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Product {

        private final SimpleStringProperty name;
        private final SimpleIntegerProperty quantity;
        private final SimpleDoubleProperty price;

        public Product(String name, int quantity, double price) {
            this.name = new SimpleStringProperty(name);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.price = new SimpleDoubleProperty(price);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public int getQuantity() {
            return quantity.get();
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }

        public double getPrice() {
            return price.get();
        }

        public void setPrice(double price) {
            this.price.set(price);
        }
    }
}
