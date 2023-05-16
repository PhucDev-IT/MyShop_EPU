package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Dao.itemCartDao;
import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.ListenerItemCart;
import com.epu.oop.myshop.model.MyListener;
import com.epu.oop.myshop.model.itemCartModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Objects;

public class ItemCartController {

    @FXML
    private ImageView imgProduct;

    @FXML
    private CheckBox checkbox;

    @FXML
    private Text nameProduct;

    @FXML
    private Label price;

    @FXML
    private JFXButton btnDown;

    @FXML
    private JFXTextField txtNumbers;

    @FXML
    private JFXButton btnUp;


    private itemCartModel item;

    private MyListener<itemCartModel> myListener;

    private ListenerItemCart<Event, itemCartModel> listenerEvent;

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final itemCartDao itemDao = itemCartDao.getInstance(connectionPool);

    private int number;

    public void setData(MyListener<itemCartModel> myListener, itemCartModel it, ListenerItemCart<Event, itemCartModel> event) {
        this.myListener = myListener;
        this.listenerEvent  = event;

        item = it;
        try {
            if(!it.getProduct().getSrcImg().contains(":")){
                imgProduct.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(it.getProduct().getSrcImg()))));
            }else
                imgProduct.setImage(new Image(it.getProduct().getSrcImg()));
        }catch (Exception e){
            imgProduct.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png"))));
            System.out.println("Lỗi: "+e.getMessage());
        }

        nameProduct.setText(it.getProduct().getTenSP());
        price.setText(App.numf.format(it.getProduct().getPrice())+"đ");
        txtNumbers.setText(String.valueOf(it.getQuantity()));
        number+=it.getQuantity();
    }

    public void chooseProduct()  {

        if(checkbox.isSelected()){
            item.setChoose(true);
        }else{
            item.setChoose(false);
        }
        item.setSumMoney(item.getProduct().getPrice().multiply(BigDecimal.valueOf(number)));
        myListener.onClickListener(item);
    }

    @FXML
    public void handle(ActionEvent event){
        if (event.getSource() == btnUp) {
            number++;
        } else if (event.getSource() == btnDown) {
            if (number > 1) {
                number--;
            }
        }
        item.setSumMoney(item.getProduct().getPrice().multiply(BigDecimal.valueOf(number)));
        txtNumbers.setText(String.valueOf(number));
        item.setQuantity(number);
        listenerEvent.onClickListener(event,item);
    }

    public void removeItem(ActionEvent e) throws SQLException {
        itemCartModel it = item;
            if(itemDao.Delete(item)<0){
                AlertNotification.showAlertError("","Có lỗi xảy ra");
            }else {
                listenerEvent.onClickListener(e,it);
            }
    }


}
