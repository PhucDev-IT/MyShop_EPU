package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.model.Messenger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MessengerController {

    @FXML
    private ImageView imgMess;

    @FXML
    private Label Sender;

    @FXML
    private Label content;

    @FXML
    private Label dateSend;

    @FXML
    private Label status;


    public void setData(Messenger messenger)
    {
        if(messenger.getImgSrc()==null)
        {
            if(messenger.isStatus())
            {
                imgMess.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/icon-open-messenge.png")));
            }else{
                imgMess.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/icon-close-mess.png")));
            }
        }else{
            imgMess.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/icon-admin.png")));
        }

        Sender.setText(messenger.getNameSender());
        dateSend.setText(String.valueOf(messenger.getSentDate().toLocalDate()));
        content.setText(messenger.getContent());

        if(messenger.isStatus()){
            status.setVisible(false);
        }else {
            status.setVisible(true);
        }
    }

}
