package com.epu.oop.myshop.controller;


import com.epu.oop.myshop.Dao.Account_Dao;
import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.*;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private Text myshop;

    @FXML
    private Hyperlink forgotPass_link;

    @FXML
    private JFXTextField userName_txt;

    @FXML
    private JFXPasswordField password_txt;

    @FXML
    private Label register_label;

    @FXML
    private ImageView imgChungNhan1;
    @FXML
    private ImageView imgChungNhan2;

    @FXML
    private ImageView vanchuyen1;
    @FXML
    private ImageView vanchuyen2;
    @FXML
    private ImageView vanchuyen3;
    @FXML
    private ImageView vanchuyen4;

    //----------------------------- QUÊN MẬT KHẨU -----------------------------------------------
    @FXML
    private Label lb_runTime;
    @FXML
    private AnchorPane paneForgot_pass;

    @FXML
    private TextField txt_Email_forpass;

    @FXML
    private Pane pane_getPass;

    @FXML
    private Label txtRandom_password;

    @FXML
    private Button btn_gui_lai_ma;

    @FXML
    private ImageView imgGoBackLogin;

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final Account_Dao accountDao = Account_Dao.getInstance(connectionPool);

    private String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private String oldUserName = "";    //Kiểm tra có phải tài khoản vừa nhập không để khóa
    private int numbersLogin = 0;

    public boolean checkEmail(String email) {
        return email.matches(regex);
    }

    public void Login(ActionEvent e) {
        String userName = userName_txt.getText();
        String Pass = password_txt.getText();

        if (userName.isEmpty() || Pass.isEmpty()) {
            AlertNotification.showAlertWarning("", "Vui lòng nhập thông tin đầy đủ");
            return;
        } else if (!checkEmail(userName)) {
            if (!userName.equals("admin")) {
                AlertNotification.showAlertWarning("", "Email chưa chính xác");
                return;
            }
        } else if (!oldUserName.equals(userName)) {
            numbersLogin = 0;
        }


        //Do user name k phải khóa chính nên phải tạo obj lấy đối tượng trong db để truy vấn ng dùng
        Account resul = accountDao.checkLogin(new Account(0, userName, Pass));
        if (resul != null) {
            if (resul.getStatus().equals("ON")) {
                numbersLogin = 0;
                Temp.account = resul;
                try {
                    ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/PageHome.fxml", "Trang chủ");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                AlertNotification.showAlertWarning("Tài khoản của bạn đang bị khóa", "Mọi thắc mắc vui lòng liên hệ với chúng tôi");
            }
        } else {
            AlertNotification.showAlertError("", "Tài khoản hoặc mật khẩu không chính xác");
            oldUserName = userName;
            numbersLogin++;
        }
        if (numbersLogin == 3 && oldUserName.equals(userName)) {
            AlertNotification.showAlertWarning("", "Tài khoản của bạn sẽ bị khóa sau 2 lần nữa");

        } else if (numbersLogin == 5 && oldUserName.equals(userName)) {
            AlertNotification.showAlertWarning("", "Tài khoản của bạn đã bị khóa");
            accountDao.lockAccount(oldUserName);
            numbersLogin = 0;
        }
    }


    public void click(MouseEvent e) throws SQLException {
        if (e.getSource() == register_label) {
            try {
                ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/RegisterForm.fxml", "Đăng ký");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == forgotPass_link) {
            startTask();
            refreshDataForgotPass();
            paneForgot_pass.setVisible(true);
        } else if (e.getSource() == myshop) {
            try {
                ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/PageHome.fxml", "Trang chủ");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == imgGoBackLogin) {
            stopTask();
            paneForgot_pass.setVisible(false);
        } else if (e.getSource() == btn_gui_lai_ma) {
            randomPassword();
            loadingTime();
        }
    }

    //------------------------- FORGOT PASSWORD -------------------------------------

    public void refreshDataForgotPass() {
        txt_Email_forpass.setEditable(true);
        txt_Email_forpass.setText("");
        pane_getPass.setVisible(false);
        txtRandom_password.setText("");
    }

    private static final SecureRandom random = new SecureRandom();
    private String newPassword = "";
    //volatile: đánh dấu field của 1 class, tất cả các thread đều thấy đc giá trị mới nhất của trường này, mà k lưu trên cached của mỗi thread riêng
    private volatile boolean isStopped = false; // Cờ hiệu để kiểm tra trạng thái của Task/Thread

    public void stopTask() {
        isStopped = true;
    }

    public void startTask() {
        isStopped = false;
    }

    public void loadingTime() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    btn_gui_lai_ma.setVisible(false);
                    lb_runTime.setVisible(true);
                });

                for (int i = 10; i >= 0; i--) {
                    if (isCancelled() || isStopped) { // Kiểm tra trạng thái của Task/Thread
                        break; // Nếu đã bị hủy hoặc dừng, thoát khỏi vòng lặp
                    }

                    Thread.sleep(1000);
                    updateMessage(Integer.toString(i)); // Cập nhật message
                }

                Platform.runLater(() -> {
                    btn_gui_lai_ma.setVisible(true);
                    lb_runTime.setVisible(false);

                });
                return null;
            }
        };

        lb_runTime.textProperty().bind(task.messageProperty()); // Liên kết nội dung của button với message của Task
        new Thread(task).start(); // Khởi chạy Task trên một luồng khác

        task.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                if (numbers > 3) {
                    btn_gui_lai_ma.setDisable(true);
                }
            });
            lb_runTime.textProperty().unbind();
            Thread.interrupted();
        });
    }

    public void randomPassword() throws SQLException {
        numbers++;
        byte[] bytes = new byte[6];
        random.nextBytes(bytes);
        newPassword = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, 6).toUpperCase();
        newPassword = newPassword.replace("_", ""); // loại bỏ ký tự '_'

        if (accountDao.changeFogotPass(txt_Email_forpass.getText(), newPassword)) {
            txtRandom_password.setText(newPassword);
            txt_Email_forpass.setEditable(false);
            pane_getPass.setVisible(true);
        } else {
            pane_getPass.setVisible(false);
            AlertNotification.showAlertError("Có lỗi xảy ra", "Email không tồn tại");

        }
    }

    //Button: Nhấn Gửi
    int numbers = 0;    //Gioi han số lần reset password

    public void requestEmail(ActionEvent e) throws SQLException {

        if (checkEmail(txt_Email_forpass.getText())) {
            randomPassword();
        }
    }

    public void defaultImage() {
        vanchuyen4.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/vận chuyển 4.png"))));
        vanchuyen3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/vận chuyển 3.png"))));
        vanchuyen2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/vận chuyển 2.png"))));
        vanchuyen1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/vận chuyển 1.png"))));
        imgChungNhan1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/chứng nhận 2.png"))));
        imgChungNhan2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/chứng nhận 1.png"))));
        imgGoBackLogin.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconGoBack.png"))));
    }

    //Giair phóng data

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultImage();
    }
}

