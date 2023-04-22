package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Dao.*;
import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

public class ProfileUserController implements Initializable {

    @FXML
    private AnchorPane profileUserPane;
    //Phí rút tiền
    private static final BigDecimal phiRutTien = new BigDecimal("5000");
    static final String Nam = "Nam";
    static final String Nu = "Nữ";
    static final String Khac = "Khác";


    @FXML
    private Text MyShop_txt;

    // ------------------------------------- DASBROAD ---------------------------------------------------
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

    private Product_Dao produc_dao;
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

    private PaymentHistory_Dao paymentHistory_dao = PaymentHistory_Dao.getInstance(connectionPool);

    private VoucherDao voucherDao = VoucherDao.getInstance(connectionPool);
    private Bank bank;
    private User user;

    private Category category = new Category();



    // Kiểm tra xem Chuỗi String là rỗng hay không
    // Dùng các method isEmpty và Blank sẽ có error: Nó empty và k chạy đc nữa
    static boolean isStringEmpty(String string) {
        return string == null || string.length() == 0;
    }

    // ------------------------------------------------ CODE SỬA THÔNG TIN CÁ NHÂN----------------------------------

    @FXML   //Đổi password
    public void setChangePassword(MouseEvent e) {
        btn_updateProfile.setText("Đổi mật khẩu");
        banHang_Form.setVisible(false);
        dashboard_form.setVisible(false);
        PurchaseProduct_Form.setVisible(false);
        editProfile_Form.setVisible(true);
        disableValueProfileForm("Password");

    }

    //Ẩn thông tin ngân hàng
    public String HideBank(String str) {
        int start = 0;
        int end = 3;
        int length = str.length();
        // Thay thế các kí tự từ vị trí 0 đến vị trí 3 bằng dấu *
        String newStr = "*".repeat(end - start);
        if (end < length)    //Nếu chuỗi > 3 kí tự thì mơới làm
        {
            newStr += str.substring(end); // Nối phần còn lại của chuỗi sau vị trí thay thế
        }
        return newStr;
    }

    //Do tất cả thông tin chung 1 form nên khi muốn đổi phần nào thì phần khác sẽ bị ẩn, làm dài dòng như này vì khi vào form
    //bank thì nó đc editable, sau vào đổi pass thì bank vẫn ddc hiện nên phải cho false truớc
    //Tránh nhập bừa k câập nhật và đữ liệu nhập đó vẫn còn lưu trên textFile
    public void disableValueProfileForm(String make) {

        Jtxt_Pass.setText("******");


        Jtxt_HoTen.setEditable(false);
        JRadion_Nu.setDisable(true);
        JRadion_Khac.setDisable(true);
        JRadion_Nam.setDisable(true);
        ngaySinh_datepicker.setDisable(true);
        Jcombox_diaChi.setDisable(true);
        Jtxt_CCCD.setEditable(false);
        Jtxt_SDT.setEditable(false);
        Jtxt_Pass.setEditable(false);
        Jtxt_SoTaiKhoan.setEditable(false);
        Jtxt_TenNganHang.setEditable(false);
        Jtxt_ChuSoHuu.setEditable(false);
        if (make.equals("Update")) {
            Jtxt_HoTen.setEditable(true);
            JRadion_Nu.setDisable(false);
            JRadion_Khac.setDisable(false);
            JRadion_Nam.setDisable(false);
            ngaySinh_datepicker.setDisable(false);
            Jcombox_diaChi.setDisable(false);
            Jtxt_CCCD.setEditable(true);
            Jtxt_SDT.setEditable(true);
        } else if (make.equals("Password")) {
            Jtxt_Pass.setEditable(true);
        } else if (make.equals("Bank")) {
            if (bank != null) {
                Jtxt_SoTaiKhoan.setPromptText(HideBank(bank.getSoTaiKhoan()));
                Jtxt_TenNganHang.setPromptText(HideBank(bank.getTenNH()));
                Jtxt_ChuSoHuu.setPromptText(HideBank(bank.getChuSoHuu()));
            }
            Jtxt_SoTaiKhoan.setText("");
            Jtxt_TenNganHang.setText("");
            Jtxt_ChuSoHuu.setText("");
            Jtxt_SoTaiKhoan.setEditable(true);
            Jtxt_TenNganHang.setEditable(true);
            Jtxt_ChuSoHuu.setEditable(true);
        }
    }

    @FXML
    public void LienKetNganHang() {
        dashboard_form.setVisible(false);
        editProfile_Form.setVisible(true);
        disableValueProfileForm("Bank");
        if (bank == null) {
            btn_updateProfile.setText("Thêm ngân hàng");
            if (isStringEmpty(Jtxt_CCCD.getText())) {
                Jtxt_CCCD.setEditable(true);
            }
        } else {
            btn_updateProfile.setText("Cập nhật ");     //Thêm dấu cách để phân biệt cập nhật bank và information
        }
    }

    //Xử lý thêm ngân hàng
    public void addBank() throws SQLException {
        if (isStringEmpty(Jtxt_CCCD.getText())) {
            AlertNotification.showAlertWarning("", "Vui lòng nhập căn cước công dân");
        } else {
            String chusoHuu = Jtxt_ChuSoHuu.getText();
            String tenNganHang = Jtxt_TenNganHang.getText();
            String stk = Jtxt_SoTaiKhoan.getText();

            if (isStringEmpty(chusoHuu) || isStringEmpty(tenNganHang) || isStringEmpty(stk)) {
                AlertNotification.showAlertWarning("WARNING", "Vui lòng nhập đầy đủ thông tin ngân hàng của bạn");
            } else {
                Bank bankInsert = new Bank(stk, tenNganHang, chusoHuu, user);
                if (bank_Dao.Insert(bankInsert)) {
                    if (Jtxt_CCCD.isEditable()) {
                        user.setCanCuocCongDan(Jtxt_CCCD.getText());
                        userDao.Update(user);
                    }
                    bank = bankInsert;
                    AlertNotification.showAlertSucces("", "Cảm ơn bạn đã liên kết ngân hàng với MyShop.");
                } else {
                    AlertNotification.showAlertError("", "Có lỗi xảy ra, Thử lại sau!");
                }
            }

        }
    }

    public void updateBank() throws SQLException {
        String chusoHuu = Jtxt_ChuSoHuu.getText();
        String tenNganHang = Jtxt_TenNganHang.getText();
        String stk = Jtxt_SoTaiKhoan.getText();

        if (isStringEmpty(chusoHuu) || isStringEmpty(tenNganHang) || isStringEmpty(stk)) {
            AlertNotification.showAlertWarning("", "Vui lòng nhập đầy đủ thông tin ngân hàng của bạn");
        } else {
            bank.setChuSoHuu(chusoHuu);
            bank.setTenNH(tenNganHang);
            bank.setSoTaiKhoan(stk);
            bank_Dao.Update(bank);
        }
    }

    @FXML
    public void ActionButton(ActionEvent e) throws SQLException {
        String nameButton = btn_updateProfile.getText();
        if (nameButton.equals("Đổi mật khẩu")) {
            String pass = Jtxt_Pass.getText();
            String s = AlertNotification.textInputDialog("Đổi mật khẩu", "Nhập pass", "");
            if (Temp.account.getPassword().equals(s)) {
                Temp.account.setPassword(pass);
                account_dao.Update(Temp.account);
                AlertNotification.showAlertSucces("", "Đổi mật khẩu thành công!");
            } else {
                AlertNotification.showAlertError("", "Sai mật khẩu");
            }
        } else if (nameButton.equals("Cập nhật")) {
            UpdateProfile();
        } else if (nameButton.equals("Thêm ngân hàng")) {
            addBank();
        } else if (nameButton.equals("Cập nhật ")) {
            updateBank();
        }
    }

    public void UpdateProfile() {

        String hoTen = Jtxt_HoTen.getText();
        String gioiTinh = "";
        Date ngaySinh = null;

        if (JRadion_Nu.isSelected()) {
            gioiTinh = Nu;
        } else if (JRadion_Nam.isSelected()) {
            gioiTinh = Nam;
        } else if (JRadion_Khac.isSelected()) {
            gioiTinh = JRadion_Khac.getText();
        }

        // Chuyển đổi datepicker thành Date của SQL
        if (ngaySinh_datepicker.getValue() != null) {
            LocalDate mydate = ngaySinh_datepicker.getValue();
            ngaySinh = Date.valueOf(mydate);
        }
        String diaChi = Jcombox_diaChi.getValue();
        String CCCD = Jtxt_CCCD.getText();
        String SDT = Jtxt_SDT.getText();

        User us = new User(Temp.account.getID(), hoTen, gioiTinh, ngaySinh, diaChi, CCCD, user.getEmail(), SDT,
                UrlAvatar);
        userDao.Update(us);

        AlertNotification.showAlertSucces("Success", "Cập nhật thành công");

    }

    // Thay avatar
    public void updateAvatar(MouseEvent e) {
        FileChooser open = new FileChooser();
        open.setTitle("Please choose the picture");
        // Chỉ hiển thị những image có đuôi....
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*png", "*jpg"));
        // Khung chứa nơi import
        Stage stage = (Stage) banHang_Form.getScene().getWindow();
        File file = open.showOpenDialog(stage);

        if (file != null) {
            UrlAvatar = file.toURI().toString();
            avata_img.setImage(new Image(file.toURI().toString()));
        }
        user.setSrcAvatar(UrlAvatar);
        userDao.Update(user);
    }

    // ------------------------------------------- FORM Bán Hàng ---------------------------------------------------

    public void clearTextData(){
        imgSP.setImage(null);
        tenHang_txt.setText("");
        soLuong_txt.setText("");
        DonGia_txt.setText("");
        danhMuc_choisebox.setValue(null);
        moTa_txta.setText("");
    }

    // Thêm ảnh cho hàng
    public void AddImageProduct() {
        FileChooser open = new FileChooser();
        open.setTitle("Please choose the picture");
        // Chỉ hiển thị những image có đuôi....
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*png", "*jpg"));
        // Khung chứa nơi import
        Stage stage = (Stage) asideLeft_Frm.getScene().getWindow();
        File file = open.showOpenDialog(stage);

        if (file != null) {
            urlSP = file.toURI().toString();
            imgSP.setImage(new Image(file.toURI().toString()));
        }
    }

    // Button: Thêm sản phẩm
    public void AddProducts(ActionEvent e) throws SQLException {

        String nameSP = tenHang_txt.getText();
        int SoLuong = Integer.parseInt(soLuong_txt.getText());

        // Mục đích khi người bán nhập giá bán có các kí tự thì ....
        String giaBan = DonGia_txt.getText();
        giaBan = deleteChar(giaBan);

        BigDecimal DonGia = new BigDecimal(giaBan);

        String moTa = moTa_txta.getText();
        String srcImg = urlSP;


        int IdCategory = -1;
        IdCategory=category.listCategory.get(danhMuc_choisebox.getValue());

        if (IdCategory == -1 || isStringEmpty(srcImg) || isStringEmpty(soLuong_txt.getText()) || isStringEmpty(giaBan)) {
            AlertNotification.showAlertWarning("", "Thiếu thông tin!");
        } else {

            Product pro = new Product(0,nameSP, SoLuong, DonGia, moTa, srcImg, IdCategory, user);

            if (!produc_dao.Insert(pro)) {
                AlertNotification.showAlertError("Có lỗi xảy ra!", "Thêm sản phẩm thất bại.");
            } else {
                //Mỗi lần thêm thành công sẽ k truy vẫn lại dữ liệu từ CSDL nữa, thêm trực tiếp vào arraylist
                listProducts.add(pro);
                setDataInBanHangForm(listProducts);
            }




        }
    }

    // kiểm tra và xóa kí tự khi người dùng nhập kí tự trong đơn giá thêm mặt hàng
    public String deleteChar(String giaBan) {
        if (giaBan.contains(".")) {
            giaBan = giaBan.replace('.', ' ');
        }
        if (giaBan.contains(",")) {
            giaBan = giaBan.replace(',', ' ');
        }
        if (giaBan.contains(" ")) {
            giaBan = giaBan.replaceAll(" ", "");
        }
        return giaBan;
    }

    // button: Xóa sản phẩm
    public void RemoveProducts(ActionEvent e) throws FileNotFoundException, SQLException {
        boolean check = AlertNotification.showAlertConfirmation("", "Bạn chắc chắn muốn xóa sản phẩm này?");

        if (check) {

            pro_onclick.setActivity("LOCK");    //Sản phẩm đang được chọn

            if (produc_dao.Update(pro_onclick) > 0) {
                AlertNotification.showAlertSucces("", "Xóa thành công!");
                listProducts.remove(pro_onclick);
                setDataInBanHangForm(listProducts);
            } else {
                AlertNotification.showAlertWarning("", "Có lỗi xảy ra.");
            }
        }

    }

    // Button: Sửa sant phẩm
    public void UpdateProduct(ActionEvent e) throws FileNotFoundException, SQLException {
        String img = urlSP;
        String tenhang = tenHang_txt.getText();
        int SoLuong = Integer.parseInt(soLuong_txt.getText());

        String giaBan = deleteChar(DonGia_txt.getText());
        BigDecimal DonGia = new BigDecimal(giaBan);
        String mota = moTa_txta.getText();

        int IdCategory = -1;
        IdCategory= category.listCategory.get(danhMuc_choisebox.getValue());

        Product pro = new Product(pro_onclick.getID(),tenhang, SoLuong, DonGia, mota, img, IdCategory, user);

        int check = produc_dao.Update(pro);
        if (check > 0) {
            AlertNotification.showAlertSucces("", "Cập nhật thành công");
            listProducts.remove(pro);
            listProducts.add(pro);
            setDataInBanHangForm(listProducts);
        } else {
            AlertNotification.showAlertWarning("", "Có lỗi xảy ra!");
        }


    }

    @FXML
    public void SeachProduct(ActionEvent e) {
        String tenHang = tenHang_txt.getText();

        List<Product> listprod = new ArrayList<>();
       // listprod = produc_dao.SearchProducts(tenHang);

        setDataInBanHangForm(listprod);
    }

    // ------------------------------------SetData các mặt hàng đã bán---------------------------------------

    public void clickProducts() {
        myListenerProduct = new MyListener<Product>() {
            @Override
            public void onClickListener(Product t) {
                pro_onclick = t;
                Locale lc = new Locale("vi", "VN");
                NumberFormat numf = NumberFormat.getInstance(lc);

                imgSP.setImage(new Image(t.getSrcImg()));
                tenHang_txt.setText(t.getTenSP());
                soLuong_txt.setText(t.getQuantity() + "");
                DonGia_txt.setText(numf.format(t.getPrice()) + "");

                for(Map.Entry entry : category.listCategory.entrySet()){
                    if(t.getCategory() == (int)entry.getValue()){
                        danhMuc_choisebox.setValue(entry.getKey()+"");
                    }
                }
                moTa_txta.setText(t.getMoTa());

                xoaSP_btn.setDisable(false);
                SuaSP_btn.setDisable(false);
            }
        };
    }

    public void setDataInBanHangForm(List<Product> listp) {
        clearTextData();

        // Xóa các node trong gridPane
        grid_BanHangForm.getChildren().clear();
        int col = 0;
        int row = 1;
        clickProducts();
        try {
            for (int i = 0; i < listp.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/ItemOfBanHangForm.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemProductsInBanHang item = fxmlLoader.getController();
                item.setData(myListenerProduct, listp.get(i));
                row++;
                grid_BanHangForm.add(anchorPane, col, row); // (child,column,row)
                // set grid width
                grid_BanHangForm.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid_BanHangForm.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid_BanHangForm.setMaxWidth(Region.USE_PREF_SIZE);

                // set grid height
                grid_BanHangForm.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid_BanHangForm.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid_BanHangForm.setMaxHeight(Region.USE_PREF_SIZE);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------- FORM RÚT TIỀN -------------------------------------------------------------------
    public void showInformationRutTienForm() {

        //Số dư có được: môĩ khi có người mua hàng thì trigger SQL sẽ tính tổng tiền trong class Order, tạo trigger update money cho account luôn
        Account acount = account_dao.SelectByID(Temp.account);
        Temp.account.setMoney(acount.getMoney());
        if (acount.getMoney() != null)
            labsoDu_RutTienForm.setText(App.numf.format(Temp.account.getMoney()) + "");
        else
            labsoDu_RutTienForm.setText(0 + "");

        if (bank != null) {
            txtSoTienRut_RTForm.setDisable(false);
            LbSTK_rutTienForm.setText(HideBank(bank.getSoTaiKhoan()));
            LbTenNH_RTForm.setText(HideBank(bank.getTenNH()));
            LbShuSoHuu_RTForm.setText(HideBank(bank.getChuSoHuu()));
        } else {
            txtSoTienRut_RTForm.setDisable(true);
            btnRutTien.setDisable(true);
            napTienPane.setDisable(true);
            ChuyenTienPane.setDisable(true);
        }

    }

    public void RutTien(ActionEvent e) {
        String soTienRut = deleteChar(txtSoTienRut_RTForm.getText());
        BigDecimal ruttien = new BigDecimal(soTienRut);

        BigDecimal soDu = new BigDecimal(Temp.account.getMoney() + "");

        if (ruttien.add(phiRutTien).compareTo(soDu) <= 0) {
            String s = "Số tiền bạn muốn rút là: " + App.numf.format(ruttien) + "\nPhí rút tiền là: " + App.numf.format(phiRutTien) + "\nSố dư sẽ còn: " +
                    App.numf.format((soDu.subtract(ruttien.add(phiRutTien)))) + "\nBạn có muốn rút?";
            if (AlertNotification.showAlertConfirmation("", s)) {
                String pass = AlertNotification.textInputDialog("MyShop", "Nhập mật khẩu!", "");
                if (Temp.account.getPassword().equals(pass)) {
                    Temp.account.setMoney(Temp.account.getMoney().subtract(ruttien.add(phiRutTien)));
                    account_dao.Update(Temp.account);
                    PaymentHistory paymentHistory = new PaymentHistory("Rút tiền","Rút tiền về tài khoản cá nhân", ruttien.add(phiRutTien), new Date(System.currentTimeMillis()),
                            "/com/epu/oop/myshop/image/iconRutTien.png", user,null);
                    paymentHistory_dao.PaymentMyShop(paymentHistory);
                    txtSoTienRut_RTForm.setText("");
                    labsoDu_RutTienForm.setText(App.numf.format(Temp.account.getMoney()) + "");
                }
            }

        }

    }

    //Showw form lịch sử rút tiền
    public void showPaymentHistory() {
        List<PaymentHistory> paymentHistoryList = paymentHistory_dao.listPaymentHistory(Temp.account);
        int col = 0;
        int row = 1;
        try {
            for (int i = 0; i < paymentHistoryList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/PaymentHistory.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                PaymentHistoryController item = fxmlLoader.getController();
                item.setData(paymentHistoryList.get(i));
                row++;
                grid_Payment.add(anchorPane, col, row); // (child,column,row)
                // set grid width
                grid_Payment.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid_Payment.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid_Payment.setMaxWidth(Region.USE_PREF_SIZE);

                // set grid height
                grid_Payment.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid_Payment.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid_Payment.setMaxHeight(Region.USE_PREF_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickPaneInRutTienForm(MouseEvent e) {
        if (e.getSource() == ChuyenTienPane) {
            Anchor_chuyentienForm.setVisible(true);
        } else if (e.getSource() == napTienPane) {

        } else if (e.getSource() == RutTienPane) {
            Anchor_chuyentienForm.setVisible(false);
        }
    }

    //Chuyển tiền
    @FXML
    public void ChuyenTien(ActionEvent e) {
        String taiKhoanNhan = taiKhoanNhan_txt.getText();

        Account a = account_dao.SelectByID(new Account(0, taiKhoanNhan, ""));
        if (a != null) {

            BigDecimal soTienChuyen = new BigDecimal(deleteChar(soTienChuyen_txt.getText()));
            if (soTienChuyen.compareTo(Temp.account.getMoney()) <= 0) {
                String pass = AlertNotification.textInputDialog("MyShop", "Nhập mật khẩu!", "");
                if (Temp.account.getPassword().equals(pass)) {
                    Temp.account.setMoney(Temp.account.getMoney().subtract(soTienChuyen));
                    account_dao.Update(Temp.account);

                    a.setMoney(a.getMoney().add(soTienChuyen));
                    account_dao.Update(a);

                    PaymentHistory payment = new PaymentHistory("Chuyển tiền",a.getUserName(), soTienChuyen, new Date(System.currentTimeMillis()),
                            "/com/epu/oop/myshop/image/iconchuyenTien.png", user, a);
                    paymentHistory_dao.Insert(payment);
                    AlertNotification.showAlertSucces("", "Chuyển tiền thành công!");
                }
            }
        } else {
            AlertNotification.showAlertWarning("", "Tài khoản nhận không tồn tại");
        }
    }

    //----------------------- VOUCHER-------------------------------------
    public void DisplayVoucherForm(MouseEvent e) {
        listVouchers = voucherDao.getVoucherConTime(user.getID());
        HideForm();
        voucherForm.setVisible(true);
        setDataVoucher();
    }
    public void clickChooseVoucher(){
        myListener_Voucher = new MyListener<VoucherModel>() {
            @Override
            public void onClickListener(VoucherModel t) {
                voucher = t;
            }
        };
    }

    public void setDataVoucher() {
        int col = 0;
        int row = 1;

        try {
            for (int i = 0; i < listVouchers.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/Voucher.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                VoucherController item = fxmlLoader.getController();
                item.setData(myListener_Voucher,listVouchers.get(i));

                row++;
                gridVoucher.add(anchorPane, col, row); // (child,column,row)
                // set grid width
                gridVoucher.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridVoucher.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridVoucher.setMaxWidth(Region.USE_PREF_SIZE);

                // set grid height
                gridVoucher.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridVoucher.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridVoucher.setMaxHeight(Region.USE_PREF_SIZE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //--------------------------------------- Lịch sử mua hàng ----------------------------------------------------------

    @FXML
    public void cickPurchaseProForm(MouseEvent e) throws SQLException {
        HideForm();
        PurchaseProduct_Form.setVisible(true);
        PurchaseProducts();
    }
    public void VinhDanhCard(){
        imgAvatarInPurchase.setImage(avata_img.getImage());
        Circle clip = new Circle(imgAvatarInPurchase.getFitWidth() / 2, imgAvatarInPurchase.getFitHeight() / 2, 50);
        imgAvatarInPurchase.setClip(clip);

        tenInCard_lb.setText(user.getFullName());
       // RankCard_Lb.setText();
        PhoneInCard_lb.setText(user.getNumberPhone());
        addessCard_lb.setText(user.getAddress());
    }
    BigDecimal tongTienDaMuaHang = new BigDecimal("0");
    public void PurchaseProducts() throws SQLException {
        listPurchaseProducts = hoaDon_dao.getPurchaseProducts(user);
        grid_PurchaseProduct.getChildren().clear();
        int col = 0;
        int row = 1;

        try {
            for (int i = 0; i < listPurchaseProducts.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/itemDaMuaHang.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemPurcharsedProduct item = fxmlLoader.getController();
                item.setData(listPurchaseProducts.get(i));
                row++;
                grid_PurchaseProduct.add(anchorPane, col, row); // (child,column,row)
                // set grid width
                grid_PurchaseProduct.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid_PurchaseProduct.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid_PurchaseProduct.setMaxWidth(Region.USE_PREF_SIZE);

                // set grid height
                grid_PurchaseProduct.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid_PurchaseProduct.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid_PurchaseProduct.setMaxHeight(Region.USE_PREF_SIZE);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void DefaultCategory() {
        ObservableList<String> languages = FXCollections.observableArrayList();
        for (Map.Entry entry : category.listCategory.entrySet()){
            languages.add(entry.getKey()+"");
        }
        danhMuc_choisebox.setItems(languages);
    }



    @FXML
    private ImageView imgIconTongDoanhThu;


    @FXML
    private Label SumDoanhThu_lb;

    @FXML
    private ImageView imgIconDaBan;

    @FXML
    private Label SumDaBan_lb;

    @FXML
    private ImageView imgIconDaBanToday;

    @FXML
    private Label soDonHangtoday_lb;

    @FXML
    private ImageView iconDoanhThuToDay;

    @FXML
    private Label doanhThuToday_lb;

    @FXML
    private ImageView RankSeller;

    public void setImage() {

        Image image1 = new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/menu.png"));
        menu_img.setImage(image1);
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
        imageThanhVien.setImage(new Image("C:\\Users\\84374\\Downloads\\Black Modern Id Card.png"));
        imgPhone.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconPhone.png"));
        imgAdress.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconAddress.png"));
        imgIconDaBan.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconDaBanHang.jpg"));
        imgIconTongDoanhThu.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconTongDoanhThu.jpg"));
        imgIconDaBanToday.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconDaBan1.jpg"));
        iconDoanhThuToDay.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconDoanhThuToday.png"));
        RankSeller.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\bestseller.jpg"));

    }



    // Hiển thị thông tin của người dùng
    public void defaultProfile() throws SQLException {
        //Nếu người dùng đã mua hàng ở bên ngoài thì k cần mất thời gian truy vấn tìm lại nữa
        if (Temp.user == null) {
            user = userDao.SelectByID(new User(Temp.account.getID()));
            bank = bank_Dao.SelectByID(new Bank(user));
        } else {
            user = Temp.user;
            bank = Temp.bank;
        }

        helloName_label.setText(user.getFullName());
        Jtxt_HoTen.setText(user.getFullName());
        String genDer = user.getGender();
        String avatar = user.getSrcAvatar();
        if (avatar == null) {
            Image image = new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/avatarNam.png"));
            avata_img.setImage(image);
        } else {
            avata_img.setImage(new Image(avatar));
        }

        if (genDer != null) {
            if (genDer.equalsIgnoreCase(Nam)) {
                JRadion_Nam.setSelected(true);
            } else if (genDer.equalsIgnoreCase(Nu)) {
                JRadion_Nu.setSelected(true);
            }
        } else {
            JRadion_Khac.setSelected(true);
        }

        if (user.getDateOfBirth() != null)
            ngaySinh_datepicker.setValue(user.getDateOfBirth().toLocalDate());

        Jcombox_diaChi.setValue(user.getAddress());
        email_label.setText(user.getEmail());
        Jtxt_CCCD.setText(user.getCanCuocCongDan());
        Jtxt_SDT.setText(user.getNumberPhone());
        userName_label.setText(Temp.account.getUserName());

    }

    public void HideForm(){
        dashboard_form.setVisible(false);
        editProfile_Form.setVisible(false);
        PurchaseProduct_Form.setVisible(false);
        soDuTK_Form.setVisible(false);
        banHang_Form.setVisible(false);
        voucherForm.setVisible(false);
    }

    //Nếu đăặt Hide lên đầu thì khi có sự kiện nhấn bất kì thì nó bị ẩn ngay
    public void click(MouseEvent e) throws IOException, SQLException {

        if (e.getSource() == menu_img) {
            asideLeft_Frm.setVisible(true);
            menu_img.setVisible(false);
        } else if (e.getSource() == asideLeft_Frm) {    //Khi di chuột ra ngoài
            asideLeft_Frm.setVisible(false);
            menu_img.setVisible(true);
        }  if (e.getSource() == dashboard_btn) {
            HideForm();
            dashboard_form.setVisible(true);
        } else if (e.getSource() == editProfile_btn) {
            HideForm();
            defaultProfile();
            disableValueProfileForm("Update");
            editProfile_Form.setVisible(true);
        } else if (e.getSource() == soDuTK_btn) {
            HideForm();
            soDuTK_Form.setVisible(true);
            showInformationRutTienForm();
            showPaymentHistory();
        } else if (e.getSource() == sell_btn) {
            if (Temp.account.getPhanQuyen().equals("Seller")) {
                HideForm();
                banHang_Form.setVisible(true);
                listProducts = userDao.listProducts(user);
                setDataInBanHangForm(listProducts);
            } else {
                AlertNotification.showAlertWarning("", "Vui lòng thêm ngân hàng để trở thành người bán.");
            }

        } else if (e.getSource() == logout_btn) {

            if (AlertNotification.showAlertConfirmation("", "Bạn muốn đăng xuất?")) {
                Temp.account = null;
                ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/PageHome.fxml");
            }
        } else if (e.getSource() == MyShop_txt) {

            ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/PageHome.fxml");

        }
    }

    public void defaultValue() {
        setImage();
        // set ngày tháng hiện tại

        DateTime_label.setText(App.timeDay);
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {


//        Object[] obj = new Object[0];
//        try {
//            obj = hoaDon_dao.OrderToDay(Date.valueOf(LocalDate.now()), Temp.account.getID());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        soDonHangtoday_lb.setText(obj[0] + " đơn");
//        if (obj[1] != null) {
//            doanhThuToday_lb.setText(App.numf.format(obj[1]) + "đ");
//        }


//        Object[] objSumOrder = hoaDon_dao.SumOrder(Temp.account.getID());
//        SumDaBan_lb.setText(objSumOrder[0] + " đơn");
//        if (objSumOrder[1] != null) {
//            SumDoanhThu_lb.setText(App.numf.format(objSumOrder[1]) + "đ");
//        }

        DefaultCategory(); // Trong form bán hàng
        defaultAddress();
        try {
            defaultProfile();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        defaultValue();
        NameDefault_Label.setText(user.getFullName());
        produc_dao = Product_Dao.getInstance(connectionPool);


    }

    public void defaultAddress() {
        ObservableList<String> address = FXCollections.observableArrayList("An Giang", "Bà Rịa – Vũng Tàu", "Bạc Liêu",
                "Bắc Giang", "Bắc Kạn", "Bắc Ninh", "Bến Tre", "Bình Dương", "Bình Định", "Bình Phước", "Bình Thuận",
                "Cà Mau", "Cao Bằng", "Cần Thơ", "Đà Nẵng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp",
                "Gia Lai", "Hà Giang", "Hà Nam", "Hà Nội", "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hậu Giang", "Hòa Bình",
                "Thành phố Hồ Chí Minh", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lạng Sơn",
                "Lào Cai", "Lâm Đồng", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ",
                "Phú Yên", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La",
                "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh",
                "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái");

        Jcombox_diaChi.setItems(address);
    }


}
