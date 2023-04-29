package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.MyListener;
import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.itemCart;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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

    private itemCart item;

    private MyListener<itemCart> myListener;

    private int number;

    public void setData(MyListener<itemCart> myListener,itemCart it) {
        this.myListener = myListener;
        item = it;
        try {
            if(!it.getProduct().getSrcImg().contains(":")){
                imgProduct.setImage(new Image(getClass().getResourceAsStream(it.getProduct().getSrcImg())));
            }else
                imgProduct.setImage(new Image(it.getProduct().getSrcImg()));
        }catch (Exception e){
            imgProduct.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png")));
            System.out.println("Lỗi: "+e.getMessage());
        }

        nameProduct.setText(it.getProduct().getTenSP());
        price.setText(App.numf.format(it.getProduct().getPrice())+"đ");
        txtNumbers.setText(it.getQuantity()+"");
        number+=it.getQuantity();
    }
    private PageHomeController page = new PageHomeController();
    public void chooseProduct(MouseEvent e)
    {

        if(checkbox.isSelected()){
            item.setChoose(true);
        }else{
            item.setChoose(false);
        }
        myListener.onClickListener(item);
    }
    @FXML
    public void upDownNumber(ActionEvent event) {
        if (event.getSource() == btnUp) {
            number++;
        } else if (event.getSource() == btnDown) {
            if (number > 1) {
                number--;
            }
        }
        txtNumbers.setText(number + "");
        item.setQuantity(number);


        //myListener.onClickListener(item);
    }

    //Kiểm tra người dùng nhập số lượng có chữ cái và xoóa
    public void removeCharInput(KeyEvent e) {
        txtNumbers.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*[^0-9].*")) {
                txtNumbers.setText(oldValue);
            }
        });
        if (txtNumbers.getText().length()==0) {
            if (txtNumbers.getText().charAt(0) == '0') {
                txtNumbers.setText(txtNumbers.getText().replace("0", ""));
            }
            number = Integer.parseInt(txtNumbers.getText());
        }
    }

}
