package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Dao.*;
import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {

    //Phí rút tiền
    private static final BigDecimal phiRutTien = new BigDecimal("5000");
    static final String Nam = "Nam";
    static final String Nu = "Nữ";
    static final String Khac = "Khác";


    @FXML
    private Text MyShop_txt;

    // ------------------------------------- DASBROAD ---------------------------------------------------
    @FXML
    private ImageView RankSeller;
    @FXML
    private ImageView imgIconDaBan;
    @FXML
    private ImageView imgIconTongDoanhThu;
    @FXML
    private ImageView iconDoanhThuToDay;
    @FXML
    private ImageView imgIconDaBanToday;

    @FXML
    private Label soDonHangtoday_lb;

    @FXML
    private Label doanhThuToday_lb;

    @FXML
    private Label SumDaBan_lb;

    @FXML
    private Label SumDoanhThu_lb;
    @FXML
    private ImageView imgloadingOne;

    @FXML
    private ImageView imgLoadingTwo;

    @FXML
    private ImageView imgLoadingThree;

    @FXML
    private ImageView imgLoadingFour;


    @FXML
    private Label NameDefault_Label;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label DateTime_label;

    @FXML
    private ImageView Calendar_img;

    @FXML
    private ImageView purchaseHistory;

    @FXML
    private ImageView changePassword;

    @FXML
    private ImageView bank_img;

    @FXML
    private ImageView voucher_img;


    // ------------------ IMAGE - Profile Form ----------------------------------

    @FXML
    private ImageView avata_img;

    @FXML
    private ImageView menu_img;

    // --------------------- Bán Hàng FORM ---------------------------------------
    @FXML
    private TextField DonGia_txt;

    @FXML
    private Button SuaSP_btn;

    @FXML
    private Button ThemSP_btn;

    @FXML
    private AnchorPane banHang_Form;

    @FXML
    private ChoiceBox<String> danhMuc_choisebox;

    @FXML
    private GridPane grid_BanHangForm;

    @FXML
    private ImageView imgSP;

    private String urlSP;

    @FXML
    private JFXButton importImageHang_btn;

    @FXML
    private TextArea moTa_txta;

    @FXML
    private TextField soLuong_txt;

    @FXML
    private TextField tenHang_txt;

    @FXML
    private Button xoaSP_btn;

    private List<Category> listCategory;

    private List<Product> listProducts = new ArrayList<>();

    private MyListener<Product> myListenerProduct;


    private Product pro_onclick;


    // -------------------------- Tắt - Thu nhỏ App -------------------------------

    @FXML
    private Label close;

    @FXML
    private Label mini;

    // -------------------------- Form Aside---------------------------------------------
    @FXML
    private Pane asideLeft_Frm;

    @FXML
    private Label helloName_label;

    @FXML
    private JFXButton dashboard_btn;

    @FXML
    private JFXButton editProfile_btn;

    @FXML
    private JFXButton sell_btn;

    @FXML
    private JFXButton soDuTK_btn;

    @FXML
    private JFXButton logout_btn;

    @FXML
    private ImageView img_Dashboard;

    @FXML
    private ImageView img_SuaHoSo;

    @FXML
    private ImageView img_Logout;

    @FXML
    private ImageView img_BanHang;

    @FXML
    private ImageView img_SoDuTK;

    // ----------------------Hiện thông tin - Sửa thông tin cá nhân---------------------------------------

    @FXML
    private JFXButton btn_updateProfile;
    @FXML
    private AnchorPane editProfile_Form;

    @FXML
    private JFXTextField Jtxt_HoTen;

    @FXML
    private JFXRadioButton JRadion_Nam;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton JRadion_Nu;

    @FXML
    private JFXRadioButton JRadion_Khac;

    @FXML
    private JFXComboBox<String> Jcombox_diaChi;

    @FXML
    private Label email_label;

    @FXML
    private Label userName_label;

    @FXML
    private JFXTextField Jtxt_Pass;

    @FXML
    private JFXTextField Jtxt_CCCD;

    @FXML
    private JFXTextField Jtxt_SDT;

    @FXML
    private JFXTextField Jtxt_SoTaiKhoan;

    @FXML
    private JFXTextField Jtxt_TenNganHang;

    @FXML
    private JFXTextField Jtxt_ChuSoHuu;

    @FXML
    private DatePicker ngaySinh_datepicker;

    private String UrlAvatar;

    // ------------------

    @FXML
    private AnchorPane soDuTK_Form;

    // --------------------------------- FORM RÚT TIỀN--------------------------------------------------------------------

    @FXML
    private Pane RutTienPane;   //Thuộc Pane chuyển tiền

    @FXML
    private ImageView imgRutTien;   //Thuộc pane chuyển tiền
    @FXML
    private Pane ChuyenTienPane;

    @FXML
    private Pane napTienPane;

    @FXML
    private ImageView img_clickNapTien;

    @FXML
    private ImageView img_clickChuyenTien;
    @FXML
    private JFXButton btnChuyenTien;
    @FXML
    private TextField taiKhoanNhan_txt;

    @FXML
    private TextField soTienChuyen_txt;

    @FXML
    private AnchorPane Anchor_chuyentienForm;

    @FXML
    private GridPane grid_Payment;
    @FXML
    private JFXButton btnRutTien;
    @FXML
    private ImageView icon_soDu;
    @FXML
    private Label labsoDu_RutTienForm;

    @FXML
    private Label LbSTK_rutTienForm;

    @FXML
    private Label LbTenNH_RTForm;

    @FXML
    private Label LbShuSoHuu_RTForm;

    @FXML
    private TextField txtSoTienRut_RTForm;
    //-------------------------------- LỊCH SỬ MUA HÀNG -------------------------------------------
    List<Object[]> listPurchaseProducts = new ArrayList<>();

    @FXML
    private AnchorPane PurchaseProduct_Form;

    @FXML
    private GridPane grid_PurchaseProduct;

    @FXML
    private ImageView imageThanhVien;
    @FXML
    private ImageView imgAvatarInPurchase;

    @FXML
    private Label tenInCard_lb;

    @FXML
    private Label addessCard_lb;
    @FXML
    private Label RankCard_Lb;

    @FXML
    private Label PhoneInCard_lb;
    @FXML
    private ImageView imgPhone;
    @FXML
    private ImageView imgAdress;


    //-------------------------------- FORM VOUCHER ------------------------------------
    @FXML
    private AnchorPane voucherForm;
    @FXML
    private GridPane gridVoucher;
    private List<VoucherModel> listVouchers = new ArrayList<>();

    private VoucherModel voucher;
    private MyListener<VoucherModel> myListener_Voucher;
    // -----------------------------------------------------
    @FXML
    private Label myShop_label;

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    private UserDao userDao = UserDao.getInstance(connectionPool);
    private Bank_Dao bank_Dao = Bank_Dao.getInstance(connectionPool);
    private Account_Dao account_dao = Account_Dao.getInstance(connectionPool);

    private Order_Dao hoaDon_dao = Order_Dao.getInstance(connectionPool);

    private Product_Dao product_dao = Product_Dao.getInstance(connectionPool);
    private PaymentHistory_Dao paymentHistory_dao = PaymentHistory_Dao.getInstance(connectionPool);

    private VoucherDao voucherDao = VoucherDao.getInstance(connectionPool);
    private Bank bank;
    private User user;

    private Category category = new Category();




    public void calculateMoneyMain() throws SQLException {
        Object[] obj = product_dao.sumTotalOrder(new User(Temp.account));

        if(obj!=null){
            soDonHangtoday_lb.setText(obj[2]+"");
            if(obj[3]!=null) {
                doanhThuToday_lb.setText(App.numf.format(obj[3]) + " đ");
            }else {
                doanhThuToday_lb.setText("0");
            }
            SumDaBan_lb.setText(obj[0]+"");
            if(obj[1]!=null){
                SumDoanhThu_lb.setText(App.numf.format(obj[1]) +" đ");
                BigDecimal sumdoanhthu = new BigDecimal(obj[1]+"");
                if(sumdoanhthu.compareTo(BigDecimal.valueOf(100000000))>=0 && Integer.parseInt(obj[0]+"")>10000){
                    RankSeller.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\bestseller.jpg"));
                }
            }else {
                SumDoanhThu_lb.setText("0");
            }

        }else {
            soDonHangtoday_lb.setText("0");
            doanhThuToday_lb.setText("0");
            SumDaBan_lb.setText("0");
            SumDoanhThu_lb.setText("0");
        }


    }
    public void loadTotalOrder() throws InterruptedException {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
               Platform.runLater(() -> {
                   try {
                       calculateMoneyMain();
                   } catch (SQLException e) {
                       throw new RuntimeException(e);
                   }
               });

                Thread.sleep(1000);
                Platform.runLater(() -> {
                    imgloadingOne.setVisible(false);
                    imgLoadingTwo.setVisible(false);
                    imgLoadingThree.setVisible(false);
                    imgLoadingFour.setVisible(false);

                    SumDoanhThu_lb.setVisible(true);
                    SumDaBan_lb.setVisible(true);
                    doanhThuToday_lb.setVisible(true);
                    soDonHangtoday_lb.setVisible(true);
                });
                return null;
            }
        };
        new Thread(task).start();

    }


    public void hiddenAllForm(){
        dashboard_form.setVisible(false);
        editProfile_Form.setVisible(false);
        banHang_Form.setVisible(false);
        soDuTK_Form.setVisible(false);
    }
    //Click chuyển form
    public void clickConverForm(MouseEvent event) throws IOException {
        hiddenAllForm();
        if(event.getSource() == dashboard_btn){
            dashboard_form.setVisible(true);

        }else if(event.getSource() == editProfile_btn){
            editProfile_Form.setVisible(true);

        }else if(event.getSource() == sell_btn){
            banHang_Form.setVisible(true);

        }else if(event.getSource() == soDuTK_btn){
            soDuTK_Form.setVisible(true);

        }else if(event.getSource() == logout_btn){
            if (AlertNotification.showAlertConfirmation("", "Bạn muốn đăng xuất?")) {
                Temp.account = null;
                ConverForm.showForm((Stage) ((Node) event.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/PageHome.fxml");
            }
        }else if (event.getSource() == MyShop_txt) {

            ConverForm.showForm((Stage) ((Node) event.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/PageHome.fxml");
        }
    }

    //Xử lý CSS hover và click menu, nếu dùng chung hàm trên khi hidden form sẽ mất all
    public void eventMenu(MouseEvent e){
        if (e.getSource() == menu_img) {
            asideLeft_Frm.setVisible(true);
            menu_img.setVisible(false);
        } else if (e.getSource() == asideLeft_Frm) {    //Khi di chuột ra ngoài
            asideLeft_Frm.setVisible(false);
            menu_img.setVisible(true);
        }
    }



    public void loadImage(){

        imgloadingOne.setImage(new Image("C:\\Users\\84374\\Downloads\\Spinner-0.5s-277px.gif"));
        imgLoadingTwo.setImage(new Image("C:\\Users\\84374\\Downloads\\Spinner-0.5s-277px.gif"));
        imgLoadingThree.setImage(new Image("C:\\Users\\84374\\Downloads\\Spinner-0.5s-277px.gif"));
        imgLoadingFour.setImage(new Image("C:\\Users\\84374\\Downloads\\Spinner-0.5s-277px.gif"));

        imgIconDaBan.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconDaBanHang.jpg"));
        imgIconTongDoanhThu.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconTongDoanhThu.jpg"));
        imgIconDaBanToday.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconDaBan1.jpg"));
        iconDoanhThuToDay.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconDoanhThuToday.png"));

        menu_img.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/menu.png")));
        icon_soDu.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/money.png")));
        img_Dashboard.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/dashboard.png")));
        img_SoDuTK.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/wallet.png")));
        img_SuaHoSo.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon_suahoso.png")));
        img_Logout.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon_logout.png")));
        img_BanHang.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon_cuahang.png")));
        purchaseHistory.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/purchaseHistory.png")));
        Calendar_img.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/Calendar.png")));
        changePassword.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/changePassword.png")));
        voucher_img.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/voucher.png")));
        bank_img.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/bank.png")));
        img_clickChuyenTien.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/iconClickChuyenTien.png")));
        img_clickNapTien.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/iconNapTien.png")));
        imgRutTien.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconRutTien.png")));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DateTime_label.setText(App.timeDay);

        if(Temp.user!=null){
            user = Temp.user;
        }else{
            user = userDao.SelectByID(new User(Temp.account));
        }


        loadImage();

        try {
            loadTotalOrder();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
