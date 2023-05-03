package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Dao.MessengeDao;
import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.Messenger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MessengerController implements Initializable {

    @FXML
    private ImageView imgMess;

    @FXML
    private Label Sender;

    @FXML
    private Text content;

    @FXML
    private Label dateSend;

    @FXML
    private ImageView status;
    private Messenger messenge;

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private MessengeDao messengeDao = MessengeDao.getInstance(connectionPool);


    public void setData(Messenger messenger)
    {
        this.messenge = messenger;

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

    public void click(MouseEvent e) throws SQLException {
        if(!messenge.isStatus())
        {
            messenge.setStatus(true);
            messengeDao.Update(messenge);
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        status.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/icon-chua-xem-mess.png")));
    }

}
