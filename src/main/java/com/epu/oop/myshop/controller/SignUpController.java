package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Dao.Account_Dao;
import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.Account;
import com.epu.oop.myshop.model.ConverForm;
import com.epu.oop.myshop.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpController {
    //------------------- Phần Đăng ký ---------------------------------
    @FXML
    private Label Convertlogin_label;

    @FXML
    private TextField EmailTxt_Register;

    @FXML
    private TextField NameTxt_Register;

    @FXML
    private TextField PasswordTxt_register;
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Account_Dao accountDao = Account_Dao.getInstance(connectionPool);

    private String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public boolean checkEmail(String email) {
        return email.matches(regex);
    }

    public void Register(ActionEvent e) throws SQLException, IOException {

        String name = NameTxt_Register.getText();
        String pass = PasswordTxt_register.getText();
        String email = EmailTxt_Register.getText();

        if (name.isEmpty() || pass.isEmpty() || email.isEmpty()) {
            AlertNotification.showAlertWarning("", "Vui lòng nhập đầy đủ thông tin");

        } else if (!checkEmail(email)) {
            AlertNotification.showAlertWarning("", "Email chưa chính xác");
        } else {

            Account account = new Account(0, email, pass);
            User u = new User();
            u.setFullName(name);
            u.setEmail(email);
            u.setAccount(account);
            u.setSrcAvatar("/com/epu/oop/myshop/image/profile/avatarNam.png");

            if (accountDao.checkRegister(account) == false) {
                boolean result = accountDao.signUpUser(account, u);
                if (result) {
                    AlertNotification.showAlertSucces("", "Đăng ký tài khoản thành công!");
                    ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/LoginForm.fxml", "Đăng nhập");
                }
            } else {
                AlertNotification.showAlertWarning("WARNING", "Tài khoản đã tồn tại");
            }
        }
    }

    @FXML
    public void click(MouseEvent e) {
        try {
            ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/LoginForm.fxml", "Đăng nhập");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
