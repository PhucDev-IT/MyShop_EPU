package com.epu.oop.myshop.controller;


import com.epu.oop.myshop.Dao.Account_Dao;
import com.epu.oop.myshop.Dao.UserDao;
import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.Account;
import com.epu.oop.myshop.model.ConverForm;
import com.epu.oop.myshop.model.Temp;
import com.epu.oop.myshop.model.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Login_Register_Controller implements Initializable{

    @FXML
    private AnchorPane loginForm_Pane;
    @FXML
    private Label khach_label;
    @FXML
    private Label minisize_label;
    @FXML
    private Hyperlink forgotPass_link;

    @FXML
    private JFXTextField userName_txt;

    @FXML
    private JFXPasswordField password_txt;

    @FXML
    private Label register_label;



    //------------------- Phần Đăng ký ---------------------------------
    @FXML
    private Label Convertlogin_label;

    @FXML
    private TextField EmailTxt_Register;

    @FXML
    private TextField NameTxt_Register;

    @FXML
    private TextField PasswordTxt_register;

    @FXML
    private Label minisizeRegister_Label;

    ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Account_Dao accountDao = Account_Dao.getInstance(connectionPool);

    public void Login(ActionEvent e)
    {
        String userName = userName_txt.getText();
        String Pass = password_txt.getText();

        if(userName.isEmpty()|| Pass.isEmpty())
        {
            AlertNotification.showAlertWarning("","Vui lòng nhập thông tin đầy đủ");
            return;
        }
        //Do user name k phải khóa chính nên phải tạo obj lấy đối tượng trong db để truy vấn ng dùng
        Account resul = accountDao.checkLogin(new Account(0,userName,Pass));
        if(resul!=null) {
            Temp.account = resul;
            try{
                ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/PageHome.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else {
            AlertNotification.showAlertError("ERROR!","Tài khoản hoặc mật khẩu không chính xác");
        }
    }


    public void Register(ActionEvent e) throws SQLException {

        String name = NameTxt_Register.getText();
        String pass = PasswordTxt_register.getText();
        String email = EmailTxt_Register.getText();

        if(name.isEmpty() || pass.isEmpty() || email.isEmpty())
        {
            AlertNotification.showAlertWarning("Warning","Vui lòng nhập đầy đủ thông tin");

        }else {

            Account account = new Account(0,email,pass);
            User u = new User();
            u.setFullName(name);
            u.setEmail(email);
            u.setAccount(account);
            u.setSrcAvatar("/com/epu/oop/myshop/image/profile/avatarNam.png");

            if(accountDao.checkRegister(account)==false)
            {
                boolean result = accountDao.signUpUser(account,u);
                if(result)
                {
                    AlertNotification.showAlertSucces("", "Đăng ký tài khoản thành công!");
                }
            }else {
                AlertNotification.showAlertWarning("WARNING","Tài khoản đã tồn tại");
            }


        }
    }

    public void click(MouseEvent e)
    {
        if(e.getSource() == register_label)
        {
            try{
                ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/RegisterForm.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }else if(e.getSource() == forgotPass_link)
        {

        }else if(e.getSource() == minisizeRegister_Label)
        {
            Stage stage = (Stage)minisizeRegister_Label.getScene().getWindow();
            stage.setIconified(true);
        }else if(e.getSource() == Convertlogin_label ) {

            try{
                ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/LoginForm.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }else if(e.getSource() == khach_label)
        {
            try{
                ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/HomeForm.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void close(MouseEvent e)
    {
        System.exit(0);
    }

    public void minisize(MouseEvent e)
    {
        Stage stage = (Stage)minisize_label.getScene().getWindow();
        stage.setIconified(true);
    }


    //Giair phóng data

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

