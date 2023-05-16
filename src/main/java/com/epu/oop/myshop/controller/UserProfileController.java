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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class UserProfileController implements Initializable {

    //Phí rút tiền
    private static final BigDecimal phiRutTien = new BigDecimal("5000");
    static final String Nam = "Nam";
    static final String Nu = "Nữ";
    static final String Khac = "Khác";

    static final int maxResult = 10;

    @FXML
    private Text MyShop_txt;

    @FXML
    private ImageView imgLoadingData;

    // ------------------------------------- DASBROAD ---------------------------------------------------
    @FXML
    private Pane pane_chatMyShop_btn;
    @FXML
    private Pane pane_baotri;
    @FXML
    private ImageView img_Baotri_ChatMyShop;
    @FXML
    private Pane changePass_pane_btn;
    @FXML
    private Pane paneLienKetBank_btn;
    @FXML
    private Pane showVoucher_pane_btn;
    @FXML
    private Pane thamGiaBanHang_pane_btn;
    @FXML
    private Pane purchaseHistory_pane_btn;
    @FXML
    private ImageView img_tuVanKH;
    @FXML
    private ImageView img_ThamgiaBanHang;
    @FXML
    private Pane paneHeader_Main;
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

    @FXML
    private AnchorPane Anch_ThamGiaBanHang;
    @FXML
    private Text txtDieuKhoanBanHang;


    // ------------------ IMAGE - Profile Form ----------------------------------

    @FXML
    private ImageView avata_img;

    @FXML
    private ImageView menu_img;

    // --------------------- Bán Hàng FORM ---------------------------------------
    @FXML
    private JFXButton btnSearchProduct;
    @FXML
    private ImageView imgRefreshSell;
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

    private String urlSP;   //Do tự nhiên bi lỗi khi sử dụng getUrl của imageview nên phải dùng biến temp

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


    private MyListener<Product> myListenerProduct;


    private Product pro_onclick;


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

    //------------------------------ LIÊN KẾT NGÂN HÀNG -----------------------------------------------------
    @FXML
    private AnchorPane paneLienKetBank;
    @FXML
    private TextField txtFullNameInBank;

    @FXML
    private TextField txtCccdInBank;

    @FXML
    private ChoiceBox<String> cbNameBank;

    @FXML
    private TextField txtTenChiNhanh;

    @FXML
    private TextField txtSoTaiKhoan;

    @FXML
    private TextField txtChuTaiKhoanBank;

    @FXML
    private JFXButton btnUpdateBank;

    @FXML
    private JFXButton btnAddBank;

    @FXML
    private JFXButton btnDeleteBank;

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
    private JFXButton btnChane_password;

    @FXML
    private DatePicker ngaySinh_datepicker;

    private String UrlAvatar;   //Lưu trữ tạm thời link img vừa được chonj

    // ------------------

    @FXML
    private AnchorPane soDuTK_Form;

    // --------------------------------- FORM RÚT TIỀN--------------------------------------------------------------------\

    @FXML
    private ImageView imgLoadingPayment;

    @FXML
    private TextField txtNapTien;


    @FXML
    private JFXButton btnBackRutTien;

    @FXML
    private Pane paneNapTienForm;


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
    private ImageView imgLoadingPurchased;
    @FXML
    private AnchorPane PurchaseProduct_Form;

    @FXML
    private GridPane grid_PurchaseProduct;

    //-------------------------------- FORM VOUCHER ------------------------------------
    @FXML
    private ImageView imgLoadingVoucher;
    @FXML
    private AnchorPane voucherForm;
    @FXML
    private GridPane gridVoucher;
    private List<VoucherModel> listVouchers = new ArrayList<>();

    private MyListener<VoucherModel> myListener_Voucher;
    // -----------------------------------------------------
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final UserDao userDao = UserDao.getInstance(connectionPool);
    private final Bank_Dao bank_Dao = Bank_Dao.getInstance(connectionPool);
    private final Account_Dao account_dao = Account_Dao.getInstance(connectionPool);
    private final Order_Dao hoaDon_dao = Order_Dao.getInstance(connectionPool);
    private final Product_Dao product_dao = Product_Dao.getInstance(connectionPool);
    private final PaymentHistory_Dao paymentHistory_dao = PaymentHistory_Dao.getInstance(connectionPool);
    private final VoucherDao voucherDao = VoucherDao.getInstance(connectionPool);

    private final MessengeDao messengeDao = MessengeDao.getInstance(connectionPool);
    private Bank bank;
    private User user;

    private List<Product> listProducts = new ArrayList<>();
    private final Category category = new Category();


    static boolean isStringEmpty(String string) {
        return string == null || string.length() == 0;
    }


    //---------------------------------------------- FORM BÁN HÀNG ----------------------------------------------------

    String lastEvent = "select";    //Gỉa thiết để kiếm tra dun cho thread cuộn

    public void clearTextData() {
        urlSP = null;
        imgSP.setImage(null);
        tenHang_txt.setText("");
        soLuong_txt.setText("");
        DonGia_txt.setText("");
        danhMuc_choisebox.setValue(null);
        moTa_txta.setText("");
    }

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

    // Thêm ảnh cho hàng
    public File AddImage(Event e) {
        FileChooser open = new FileChooser();
        open.setTitle("Please choose the picture");
        // Chỉ hiển thị những image có đuôi....
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*png", "*jpg"));
        // Khung chứa nơi import
        Stage stage = null;

        if (e.getSource() == importImageHang_btn) {
            stage = (Stage) banHang_Form.getScene().getWindow();

        } else if (e.getSource() == avata_img) {
            stage = (Stage) asideLeft_Frm.getScene().getWindow();
        }

        return open.showOpenDialog(stage);
    }

    //Thêm ảnh sản phẩm trong bán hàng
    public void addImgProduct(ActionEvent e) {
        File file = AddImage(e);
        if (file != null) {
            urlSP = file.toURI().toString();
            imgSP.setImage(new Image(file.toURI().toString()));
        }
    }

    public void addImgAvatar(MouseEvent e) {
        File file = AddImage(e);
        if (file != null) {
            UrlAvatar = file.toURI().toString();
            avata_img.setImage(new Image(file.toURI().toString()));
        }
        user.setSrcAvatar(UrlAvatar);
        userDao.Update(user);
    }

    public void refreshDataInSell(Event e) {
        SuaSP_btn.setDisable(true);
        xoaSP_btn.setDisable(true);
        ThemSP_btn.setDisable(false);
        row = 1;
        col = 0;
        clearTextData();
        lastIndex.set(0);
        listProducts.clear();
        grid_BanHangForm.getChildren().clear();
        loadingDataProduct(e);


    }

    //Loading data
    public synchronized void loadingDataProduct(Event e) {
        imgLoadingData.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/loading2.gif"))));
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> imgLoadingData.setVisible(true));

                if (isCancelled()) {
                    return null;
                }
                Thread.sleep(200);

                if (e.getSource() == btnSearchProduct) {  //Nếu nhấn tìm kiếm thì
                    ProductBySearch();
                } else if (e instanceof ScrollEvent) {
                    if (lastEvent.equals("select")) {
                        SelectDataProduct();
                    } else {
                        ProductBySearch();
                    }
                } else {
                    SelectDataProduct();
                }

                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        task.setOnSucceeded(event -> {
            Platform.runLater(() -> calculateNumbers());
            Platform.runLater(() -> imgLoadingData.setVisible(false));
            System.out.println("close thread");
            thread.interrupt();
        });
    }

    private AtomicInteger lastIndex = new AtomicInteger(0);

    public static AtomicBoolean isReuslts = new AtomicBoolean(false); //Ban đầu rỗng

    public void ProductBySearch() {
        lastEvent = "search";
        try {
            listProducts.addAll(product_dao.SearchProductOfSeller(user, nameProductSearch, lastIndex, maxResult));
        } catch (Exception e) {
            listProducts.clear();
            System.out.println("Tràn List: " + e.getMessage());
        }
    }

    public void SelectDataProduct() {

        lastEvent = "select";
        try {
            listProducts.addAll(product_dao.selectProductOfUser(user, lastIndex, maxResult));
        } catch (OutOfMemoryError | SQLException e) {
            System.out.println("Tràn List: " + e.getMessage());
            listProducts.clear();
        }
    }

    public void calculateNumbers() {
        int index = lastIndex.get();
        while (index < listProducts.size()) {
            setDataInBanHangForm(listProducts.get(index));
            index++;
        }

        if (listProducts.size() == lastIndex.get()) { //Nếu không có thêm sp mới thì đã hết sản phâ trong csdl gán false đ khỏi cuộn
            isReuslts.set(false);
        } else {
            isReuslts.set(true);
        }
        lastIndex.set(lastIndex.get() + (listProducts.size() - lastIndex.get()));

        System.out.println("Number product: " + lastIndex.get() + " Grid: " + grid_BanHangForm.getChildren().size());
    }

    //Khi cuôộn sản phẩm trong bán sản phẩm
    @FXML
    public void eventScroll(ScrollEvent e) {
        if (isReuslts.get()) {
            loadingDataProduct(e);
        }
    }

    @FXML
    public void AddProducts() throws SQLException {

        String nameSP = tenHang_txt.getText();
        int SoLuong = Integer.parseInt(soLuong_txt.getText());
        // Mục đích khi người bán nhập giá bán có các kí tự thì ....
        String giaBan = deleteChar(DonGia_txt.getText());
        BigDecimal DonGia = new BigDecimal(giaBan);

        String moTa = moTa_txta.getText();
        String srcImg = imgSP.getImage().getUrl();

        int IdCategory;
        IdCategory = category.listCategory.get(danhMuc_choisebox.getValue());

        if (IdCategory == -1 || isStringEmpty(srcImg) || isStringEmpty(soLuong_txt.getText()) || isStringEmpty(giaBan)) {
            AlertNotification.showAlertWarning("", "Thiếu thông tin!");
        } else {

            Product pro = new Product(0, nameSP, SoLuong, DonGia, moTa, srcImg, IdCategory, user);
            pro.setTotalRevenue(new BigDecimal(0));
            if (!product_dao.Insert(pro)) {
                AlertNotification.showAlertError("Có lỗi xảy ra!", "Thêm sản phẩm thất bại.");
            } else {
                //Mỗi lần thêm thành công sẽ k truy vẫn lại dữ liệu từ CSDL nữa, thêm trực tiếp vào arraylist
                listProducts.add(pro);
                clearTextData();
                row = 1;
                grid_BanHangForm.getChildren().clear();     //Nếu để trong setDataInBanHangForm khi cuộn sẽ mất d liệu
                for (Product product : listProducts)
                    setDataInBanHangForm(product);
            }
        }
    }

    // Button: Sửa thông tin sản phẩm
    @FXML
    public void UpdateProduct(ActionEvent e) throws SQLException {

        String tenhang = tenHang_txt.getText();
        int SoLuong = Integer.parseInt(soLuong_txt.getText());
        String giaBan = deleteChar(DonGia_txt.getText());
        BigDecimal DonGia = new BigDecimal(giaBan);
        String mota = moTa_txta.getText();
        int IdCategory;
        IdCategory = category.listCategory.get(danhMuc_choisebox.getValue());

        if (IdCategory == -1 || isStringEmpty(tenhang) || isStringEmpty(soLuong_txt.getText()) || isStringEmpty(giaBan)) {
            AlertNotification.showAlertWarning("", "Thiếu thông tin!");
        } else {
            Product pro = new Product(pro_onclick.getID(), tenhang, SoLuong, DonGia, mota, urlSP, IdCategory, user);
            if (product_dao.Update(pro) > 0) {
                AlertNotification.showAlertSucces("", "Cập nhật thành công");
                refreshDataInSell(e);
            } else {
                AlertNotification.showAlertWarning("", "Có lỗi xảy ra!");
            }
        }

    }

    // button: Xóa sản phẩm
    @FXML
    public void RemoveProducts() throws SQLException {

        if (AlertNotification.showAlertConfirmation("", "Bạn chắc chắn muốn xóa sản phẩm này?")) {

            pro_onclick.setActivity("LOCK");    //Sản phẩm đang được chọn

            if (product_dao.Update(pro_onclick) > 0) {
                AlertNotification.showAlertSucces("", "Xóa thành công!");
                grid_BanHangForm.getChildren().clear();
                row = 1;
                listProducts.remove(pro_onclick);
                for (Product product : listProducts) {
                    setDataInBanHangForm(product);
                }

            } else {
                AlertNotification.showAlertWarning("", "Có lỗi xảy ra.");
            }
        }

    }

    private String nameProductSearch;

    @FXML
    public void SearchProduct(ActionEvent e) {
        nameProductSearch = tenHang_txt.getText();
        if (!isStringEmpty(nameProductSearch)) {
            refreshDataInSell(e);
        }

    }

    public void clickProducts() {
        myListenerProduct = new MyListener<>() {
            @Override
            public void onClickListener(Product t) {
                pro_onclick = t;
                try {
                    if (t.getSrcImg().contains(":")) {
                        imgSP.setImage(new Image(t.getSrcImg()));
                    } else {
                        imgSP.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(t.getSrcImg()))));
                    }
                } catch (Exception e) {
                    imgSP.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png"))));
                }
                urlSP = t.getSrcImg();
                tenHang_txt.setText(t.getTenSP());
                soLuong_txt.setText(String.valueOf(t.getQuantity()));
                DonGia_txt.setText(App.numf.format(t.getPrice()));

                for (Map.Entry entry : category.listCategory.entrySet()) {
                    if (t.getCategory() == (int) entry.getValue()) {
                        danhMuc_choisebox.setValue(String.valueOf(entry.getKey()));
                    }
                }
                moTa_txta.setText(t.getMoTa());
                xoaSP_btn.setDisable(false);
                SuaSP_btn.setDisable(false);
                ThemSP_btn.setDisable(true);
            }
        };
    }

    int row = 1;
    int col = 0;

    public void setDataInBanHangForm(Product product) {
        // Xóa các node trong gridPane

        clickProducts();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/ItemOfBanHangForm.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            ItemProductsInBanHang item = fxmlLoader.getController();
            item.setData(myListenerProduct, product);
            setDataGridPane(grid_BanHangForm, anchorPane, col, ++row);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setValueCategory() {
        ObservableList<String> languages = FXCollections.observableArrayList();
        for (Map.Entry entry : category.listCategory.entrySet()) {
            languages.add(String.valueOf(entry.getKey()));
        }
        danhMuc_choisebox.setItems(languages);
    }

    //------------------------------ LIÊN KẾT NGÂN HÀNG-----------------------------------------------
    public String hiddenNumbersBank(String str) {
        int start = 0;
        int end = 5;
        int length = str.length();
        // Thay thế các kí tự từ vị trí 0 đến vị trí 3 bằng dấu *
        String newStr = "*".repeat(end - start);
        if (end < length)    //Nếu chuỗi > 3 kí tự thì mơới làm
        {
            newStr += str.substring(end); // Nối phần còn lại của chuỗi sau vị trí thay thế
        }
        return newStr;
    }

    public void clearDataBank() {
        txtFullNameInBank.setText("");
        txtCccdInBank.setText("");
        cbNameBank.setValue("");
        txtTenChiNhanh.setText("");
        txtSoTaiKhoan.setText("");
        txtChuTaiKhoanBank.setText("");
    }

    public void setDataListBank() {
        ObservableList<String> listBank = FXCollections.observableArrayList();
        for (Map.Entry entry : Bank.listBanks.entrySet()) {
            listBank.add(entry.getKey() + "-" + entry.getValue());
        }
        cbNameBank.setItems(listBank);
    }

    public void displayInformationBank() {
        paneLienKetBank.setVisible(true);
        setDataListBank();
        if (bank == null) {
            btnDeleteBank.setDisable(true);
            btnUpdateBank.setDisable(true);
            btnAddBank.setDisable(false);
        } else {
            btnAddBank.setDisable(true);
            btnDeleteBank.setDisable(false);
            btnUpdateBank.setDisable(false);

            txtFullNameInBank.setText(bank.getChuSoHuu());
            txtCccdInBank.setText(bank.getSoCCCD());
            cbNameBank.setValue(bank.getTenNH());
            txtTenChiNhanh.setText(bank.getTenChiNhanh());
            txtSoTaiKhoan.setText(hiddenNumbersBank(bank.getSoTaiKhoan()));

            txtChuTaiKhoanBank.setText(bank.getChuSoHuu());
        }
    }

    public boolean checkInputBank() {
        String hovaTen = txtFullNameInBank.getText();
        String CCCD = txtCccdInBank.getText();
        String tenNganHang = cbNameBank.getValue();
        String chiNhanh = txtTenChiNhanh.getText();
        String stk = txtSoTaiKhoan.getText();
        String chuTaiKhoan = txtChuTaiKhoanBank.getText();

        if (!hovaTen.equals(chuTaiKhoan) && (!isStringEmpty(hovaTen) || !isStringEmpty(chuTaiKhoan))) {
            AlertNotification.showAlertWarning("", "Họ và tên và chủ sở hữu phải giống nhau");
        } else if (CCCD.length() <= 10 || stk.length() <= 10) {
            AlertNotification.showAlertWarning("Vui lòng nhập chính xác căn cước công dân và số tài khoản!", "");
        } else if (isStringEmpty(tenNganHang) || isStringEmpty(chiNhanh)) {
            AlertNotification.showAlertWarning("Vui lòng nhập đầy đủ thông tin", "");
        } else {
            bank = new Bank(stk, chiNhanh, CCCD, tenNganHang, chuTaiKhoan, user);
            return true;
        }
        return false;
    }

    @FXML
    public void addBank() throws SQLException {
        if (!checkInputBank()) {
            return;
        }
        if (bank_Dao.Insert(bank)) {
            AlertNotification.showAlertSucces("Thêm thành công", "");
            btnAddBank.setDisable(true);
            btnUpdateBank.setDisable(false);
            btnDeleteBank.setDisable(false);
        } else {
            AlertNotification.showAlertError("Có lỗi xảy ra, thử lại sau!", "");
        }
    }

    @FXML
    public void UpdateBank() throws SQLException {
        if (!checkInputBank()) {
            return;
        }
        if (bank_Dao.Update(bank) > 0) {
            AlertNotification.showAlertSucces("Sửa thành công", "");
        } else {
            AlertNotification.showAlertError("Có lỗi xảy ra, thử lại sau!", "");
        }
    }

    @FXML
    public void deleteBank() {
        if (AlertNotification.showAlertConfirmation("Bạn chắc chắn xóa liên kết ngân hàng!", "Đồng ý, việc mua bán có thể bị gián đoạn")) {
            if (bank_Dao.Delete(bank) > 0) {
                AlertNotification.showAlertSucces("Xóa thành công", "");
                bank = null;
                clearDataBank();
                btnAddBank.setDisable(false);
                btnUpdateBank.setDisable(true);
                btnDeleteBank.setDisable(true);
            } else {
                AlertNotification.showAlertError("Có lỗi xảy ra, thử lại sau!", "");
            }
        }
    }

    //---------------------------------- FORM SỐ DƯ TÀI KHOẢN ----------------------------------------------
    public void showDataSoDuTaiKhoan() {
        labsoDu_RutTienForm.setText(App.numf.format(Temp.account.getMoney()));

        if (bank == null) {
            btnRutTien.setDisable(true);
            napTienPane.setDisable(true);
        } else {
            LbSTK_rutTienForm.setText(hiddenNumbersBank(bank.getSoTaiKhoan()));
            LbTenNH_RTForm.setText(bank.getTenNH());
            LbShuSoHuu_RTForm.setText(bank.getChuSoHuu());

            btnRutTien.setDisable(false);
            napTienPane.setDisable(false);
        }
    }

    //Xóa các kí tự khi chỉ cho nhập số
    public void removeChar(TextField txt) {
        txt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*[^0-9].*")) {
                txt.setText(oldValue);
            }
        });
        if (!isStringEmpty(txt.getText())) {
            if (txt.getText().charAt(0) == '0') {
                txt.setText(txt.getText().replace("0", ""));
            }
        }
    }

    public void removeInputChar(KeyEvent e) {
        if (e.getSource() == soTienChuyen_txt) {
            removeChar(soTienChuyen_txt);
        } else if (e.getSource() == txtSoTienRut_RTForm) {
            removeChar(txtSoTienRut_RTForm);
        } else if (e.getSource() == txtNapTien) {
            removeChar(txtNapTien);
        }
    }

    @FXML
    public void withdrawMoney() throws SQLException {

        if (isStringEmpty(txtSoTienRut_RTForm.getText())) {
            AlertNotification.showAlertWarning("", "Nhập số tiền muốn rút!");
        } else {

            BigDecimal soTienRut = new BigDecimal(deleteChar(txtSoTienRut_RTForm.getText()));
            soTienRut.add(phiRutTien);

            if (Temp.account.getMoney().compareTo(soTienRut) < 0) {
                AlertNotification.showAlertWarning("", "Số dư không đủ!");
            } else {
                String notification = "Số tiền bạn nhận được là: " + App.numf.format(soTienRut.subtract(phiRutTien));
                if (AlertNotification.showAlertConfirmation(notification, "Bạn chắc chắn muốn rút?")) {
                    String pass = AlertNotification.textInputDialog("Rút tiền", "Nhập mật khẩu", "");
                    if (Temp.account.getPassword().equals(pass)) {
                        PaymentHistory paymentBank = new PaymentHistory("Rút tiền", "Rút về ngân hàng", soTienRut,
                                new Date(System.currentTimeMillis()), "/com/epu/oop/myshop/image/iconRutTien.png", user, null);
                        Temp.account.setMoney(Temp.account.getMoney().subtract(soTienRut));

                        if (account_dao.transferMoney(Temp.account, paymentBank)) {
                            labsoDu_RutTienForm.setText(App.numf.format(Temp.account.getMoney()));
                            AlertNotification.showAlertSucces("Rút tiền thành công", "");
                            refreshPayment();
                        } else {
                            AlertNotification.showAlertError("Có lỗi xảy ra, thử lại sau!", "");
                        }
                    }
                }
            }
        }
    }

    //Button Chuyển tiền
    @FXML
    public void transferMoney() throws SQLException {

        if (isStringEmpty(taiKhoanNhan_txt.getText()) || isStringEmpty(soTienChuyen_txt.getText())) {
            AlertNotification.showAlertWarning("", "Vui lòng nhập đầy đủ thông tin");
        } else {
            BigDecimal soTienChuyen = new BigDecimal(deleteChar(soTienChuyen_txt.getText()));
            if (Temp.account.getMoney().compareTo(soTienChuyen) >= 0) {

                String pass = AlertNotification.textInputDialog("Chuyển tiền", "Nhập mật khẩu", "");
                if (Temp.account.getPassword().equals(pass)) {
                    Account nguoiNhan = account_dao.SelectByID(new Account(0, taiKhoanNhan_txt.getText(), ""));
                    if (nguoiNhan == null) {
                        AlertNotification.showAlertWarning("Người dùng không tồn tại", "");
                    } else {
                        PaymentHistory pm = new PaymentHistory("Chuyển tiền", user.getFullName(), soTienChuyen, new Date(System.currentTimeMillis()),
                                "/com/epu/oop/myshop/image/profile/iconClickChuyenTien.png", user, nguoiNhan);
                        Temp.account.setMoney(Temp.account.getMoney().subtract(soTienChuyen));
                        nguoiNhan.setMoney(soTienChuyen);
                        if (account_dao.UpdatetransferMoney(Temp.account, nguoiNhan, pm)) {
                            AlertNotification.showAlertSucces("Chuyển tiền thành công", "");
                            labsoDu_RutTienForm.setText(App.numf.format(Temp.account.getMoney()));
                            refreshPayment();
                        } else {
                            AlertNotification.showAlertError("Có lỗi xảy ra", "");
                        }
                    }
                } else {
                    AlertNotification.showAlertError("", "Mật khẩu không chính xác");
                }
            } else {
                AlertNotification.showAlertWarning("", "Số dư không đủ");
            }

        }
    }

    //Button: Nạp tiền
    @FXML
    public void napTien() throws SQLException {
        if (isStringEmpty(txtNapTien.getText())) {
            AlertNotification.showAlertWarning("", "Nhập số tiền bạn muốn nạp");
        } else {

            String pass = AlertNotification.textInputDialog("Chuyển tiền", "Nhập mật khẩu", "");
            if (Temp.account.getPassword().equals(pass)) {
                BigDecimal soTienNap = new BigDecimal(deleteChar(txtNapTien.getText()));
                Temp.account.setMoney(Temp.account.getMoney().add(soTienNap));
                PaymentHistory pm = new PaymentHistory("Nạp tiền", "Từ STK: " + bank.getSoTaiKhoan(), soTienNap, new Date(System.currentTimeMillis()),
                        "/com/epu/oop/myshop/image/profile/iconNapTien.png", user, null);
                if (account_dao.transferMoney(Temp.account, pm)) {
                    AlertNotification.showAlertSucces("Nạp tiền thành công", "Cảm ơn bạn đã đồng hành với chúng tôi");
                    refreshPayment();
                } else {
                    Temp.account.setMoney(Temp.account.getMoney().subtract(soTienNap));
                    AlertNotification.showAlertError("Hệ thống lỗi", "");
                }
            } else {
                AlertNotification.showAlertError("", "Mật khẩu không chính xác");
            }
        }
        labsoDu_RutTienForm.setText(App.numf.format(Temp.account.getMoney()));
    }


    //Hiển thị lịch sử giao dịch

    private List<Object[]> listPaymentHistory = new ArrayList<>();

    public void refreshPayment() {
        rowPayment = 1;
        lastIndex.set(0);
        listPaymentHistory.clear();
        grid_Payment.getChildren().clear();
        loadingDataPaymentHistory();
    }

    //Cuộn xem lịch sử
    public void scrollPayment() {
        if (isReuslts.get()) {
            loadingDataPaymentHistory();
        }
    }


    public void CalcularPayment() {
        listPaymentHistory.addAll(paymentHistory_dao.listPaymentHistory(Temp.account, lastIndex, maxResult));
        for (int i = 0; i < listPaymentHistory.size() && i >= lastIndex.get(); i++) {
            displayPaymentHistory(listPaymentHistory.get(i));
        }
        if (listPaymentHistory.size() == lastIndex.get()) { //Nếu không có thêm kq mới thì đã hết trong csdl gán false để khỏi cuộn
            isReuslts.set(false);
        } else {
            isReuslts.set(true);
        }
        lastIndex.set(lastIndex.get() + (listPaymentHistory.size() - lastIndex.get()));
    }

    int rowPayment = 1;

    public void displayPaymentHistory(Object[] obj) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/PaymentHistory.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            PaymentHistoryController item = fxmlLoader.getController();
            item.setData(obj);
            setDataGridPane(grid_Payment, anchorPane, 0, rowPayment++);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadingDataPaymentHistory() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> imgLoadingPayment.setVisible(true));
                Thread.sleep(200);

                if (!isCancelled()) {
                    Platform.runLater(() -> CalcularPayment());
                }

                Platform.runLater(() -> imgLoadingPayment.setVisible(false));
                return null;

            }
        };
        Thread thread = new Thread(task);
        thread.start();
        task.setOnSucceeded(event -> {
            task.cancel();
            thread.interrupt();
        });


    }


    public void converFormSoDuTK(MouseEvent e) {
        paneNapTienForm.setVisible(false);
        Anchor_chuyentienForm.setVisible(false);

        if (e.getSource() == napTienPane) {
            paneNapTienForm.setVisible(true);
        } else if (e.getSource() == ChuyenTienPane) {
            Anchor_chuyentienForm.setVisible(true);
        }
    }

    //--------------------------------------- SỬA THÔNG TIN CÁ NHÂN --------------------------------------------
    public void hiddenInformation(boolean result) {
        Jtxt_Pass.setText("******");
        Jtxt_HoTen.setEditable(result);
        JRadion_Nu.setDisable(!result);
        JRadion_Khac.setDisable(!result);
        JRadion_Nam.setDisable(!result);
        ngaySinh_datepicker.setDisable(!result);
        Jcombox_diaChi.setDisable(!result);
        Jtxt_CCCD.setEditable(result);
        Jtxt_SDT.setEditable(result);
        Jtxt_Pass.setEditable(!result);
        btnChane_password.setVisible(!result);
        btn_updateProfile.setVisible(result);
    }

    public void showInformationUser() {
        Jtxt_HoTen.setText(user.getFullName());
        if (user.getGender().equals("Nam")) {
            JRadion_Nam.setSelected(true);
        } else if (user.getGender().equals("Nữ")) {
            JRadion_Nu.setSelected(true);
        } else {
            JRadion_Khac.setSelected(true);
        }

        if (user.getDateOfBirth() != null) {
            ngaySinh_datepicker.setValue(user.getDateOfBirth().toLocalDate());
        }
        Jcombox_diaChi.setValue(user.getAddress());
        Jtxt_CCCD.setText(user.getCanCuocCongDan());
        Jtxt_SDT.setText(user.getNumberPhone());
        email_label.setText(user.getEmail());
        userName_label.setText(user.getEmail());
    }

    @FXML
    public void updateProfile() {
        String hoTen = Jtxt_HoTen.getText();

        if (isStringEmpty(hoTen)) {
            AlertNotification.showAlertWarning("", "Họ tên không được phép trống");
            return;
        }

        String gioiTinh = Khac;
        Date ngaySinh = null;

        if (JRadion_Nu.isSelected()) {
            gioiTinh = Nu;
        } else if (JRadion_Nam.isSelected()) {
            gioiTinh = Nam;
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
        if (userDao.Update(us) > 0) {
            AlertNotification.showAlertSucces("", "Cập nhật thành công");
            user = us;
        } else {
            AlertNotification.showAlertError("", "Có lỗi xảy ra");
        }

    }

    //---------------------------------- ĐỔI MẬT KHẨU -----------------------------------------
    public void showChangePassForm() {
        hiddenInformation(false);
        showInformationUser();
        editProfile_Form.setVisible(true);
    }

    @FXML
    public void ChangePassword() {
        if (!isStringEmpty(Jtxt_Pass.getText())) {
            String pass = AlertNotification.textInputDialog("Đổi mật khẩu", "Nhập mật khẩu hiện tại", "");
            if (Temp.account.getPassword().equals(pass)) {
                String oldPass = Temp.account.getPassword();
                Temp.account.setPassword(Jtxt_Pass.getText());
                if (account_dao.Update(Temp.account) > 0) {
                    AlertNotification.showAlertSucces("", "Đổi mật khẩu thành công");
                } else {
                    AlertNotification.showAlertError("", "Có lỗi xảy ra!");
                    Temp.account.setPassword(oldPass);
                }
            } else {
                AlertNotification.showAlertError("", "Mật khẩu không chính xác");
            }

        }
    }
    //--------------------------------LỊCH SỬ MUA HÀNG---------------------------------------------

    /*Giai thích:
        -Type Atomic.. giúp an toàn trong ử dụng luồng, bởi các biến khi được sử dụng trong luồng gây ra result không chính xác
       -Dùng các biến AtomicInteger lastIndex(tổng số lượng sp lấy ra) làm vị trí bắt đầu select của lần sau
       -Dùng biến check để kiểm tra xem còn dữ liệu trong database hay không, mục đích để tránh việc truy vâấn nhiều lần
       gây tốn tài nguyên do người dùng cuộn scrollpane liên tục
       -Create method refresh make refrsh old data, và cập nhật lại số lượng truy vấn được sau cùng lastIndex
       -Gọi task để cập nhật lại giao diện UI. Mỗi lần lấy dữ liệu xong thì tinh toán có giá trị trả về hay không....
     */

    public void loadingDataPurcharsedHistory() {
        imgLoadingPurchased.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/loading.gif"))));
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> imgLoadingPurchased.setVisible(true));
                Thread.sleep(200);

                Platform.runLater(() -> {
                    try {
                        getDataPrurchaseHistory();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                Thread.sleep(300);
                Platform.runLater(() -> imgLoadingPurchased.setVisible(false));
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        task.setOnSucceeded(event -> thread.interrupt());
    }

    @FXML
    public void scrollPanePurchased() {
        if (isReuslts.get()) {
            loadingDataPurcharsedHistory();
        }
    }

    public void refreshDataPurchasedHistory() {
        listPurchaseProducts.clear();
        lastIndex.set(0);
        grid_PurchaseProduct.getChildren().clear();
        rowPruchased = 1;
        loadingDataPurcharsedHistory();

    }

    public void getDataPrurchaseHistory() throws SQLException {

        listPurchaseProducts.addAll(hoaDon_dao.getPurchaseProducts(user, lastIndex, maxResult));
        int index = lastIndex.get();
        while (index < listPurchaseProducts.size()) {
            showPurchasedHisoty(listPurchaseProducts.get(index));
            index++;
        }
        //Do tối đa 10 / 1 lần sellect nên nếu <10 thì khng cho cuộn nữa luôn
        if (listPurchaseProducts.size() < 10 || listPurchaseProducts.size() == lastIndex.get()) {
            isReuslts.set(false);
        } else {
            isReuslts.set(true);
        }
        lastIndex.set(lastIndex.get() + (listPurchaseProducts.size() - lastIndex.get()));
    }

    int rowPruchased = 1;

    public void showPurchasedHisoty(Object[] obj) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/itemDaMuaHang.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            ItemPurcharsedProduct item = fxmlLoader.getController();
            item.setData(obj);
            setDataGridPane(grid_PurchaseProduct, anchorPane, col, ++rowPruchased);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Vinh danh người dùng mua nhiều sản phẩm


    //-------------------------------------- DANH SÁCH VOUCHER --------------------------------------------------
    @FXML
    private Label lb_notVoucher;

    public void DisplayVoucherForm() {
        voucherForm.setVisible(true);
        listVouchers = voucherDao.getVoucherConTime(user.getID());
        if (listVouchers.size() > 0) {
            lb_notVoucher.setVisible(false);
            setDataVoucher();
        } else {
            lb_notVoucher.setVisible(true);
        }


    }

    public void setDataVoucher() {
        int colVoucher = 0;
        int rowVoucher = 1;

        try {
            for (VoucherModel listVoucher : listVouchers) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/Voucher.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                VoucherController item = fxmlLoader.getController();
                item.setData(myListener_Voucher, listVoucher);

                setDataGridPane(gridVoucher, anchorPane, colVoucher, rowVoucher++);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //--------------------------THAM GIA BÁN HÀNG -----------------------------------------------------
    @FXML
    private CheckBox checkBoxJoinSell;
    @FXML
    private JFXButton btnCancelRequest;

    @FXML
    private JFXButton btnRequest;

    public void loadingDataJoinSell() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                if (isCancelled()) {
                    return null;
                }
                Platform.runLater(() -> setDataJoinSell());

                return null;
            }
        };
        new Thread(task).start();
    }

    public void setDataJoinSell() {

        try {
            String content = String.join("\n", Files.readAllLines(Paths.get(Objects.requireNonNull(getClass().getResource("/com/epu/oop/myshop/Text/DieuKhoanBanHang.txt")).toURI())));
            txtDieuKhoanBanHang.setText(content);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkProfile() throws SQLException {
        if (bank == null || isStringEmpty(user.getFullName()) || isStringEmpty(user.getAddress()) || user.getDateOfBirth() == null
                || isStringEmpty(user.getCanCuocCongDan()) || isStringEmpty(user.getNumberPhone())) {
            AlertNotification.showAlertWarning("", "Vui lòng cập nhật đầy đủ thông tin\nVà liên kết ngân hàng để tham gia bán hàng");
        } else {
            LocalDate today = LocalDate.now();

            // Tính số năm giữa hai ngày
            int age = Period.between(user.getDateOfBirth().toLocalDate(), today).getYears();
            if (age >= 18) {
                Temp.account.setPhanQuyen("Seller");
                if (account_dao.Update(Temp.account) > 0) {
                    Messenger messenger = new Messenger(0, null, "Hệ thống", "Chúc mừng bạn đã trở thành người bán hàng của chúng tôi",
                            new Date(System.currentTimeMillis()), false, user.getID());
                    Anch_ThamGiaBanHang.setVisible(false);
                    AlertNotification.showAlertSucces("Chúc mừng bạn đã trở thành người bán hàng.", "Cảm ơn bạn đã đồng hành cùng chúng tôi");
                    dashboard_form.setVisible(true);
                    messengeDao.Insert(messenger);
                } else {
                    AlertNotification.showAlertError("", "Có lỗi xảy ra");
                    Temp.account.setPhanQuyen("Member");
                }
            } else {
                AlertNotification.showAlertWarning("", "Bạn chưa đủ 18+");
            }
        }
    }

    public void btnJoinSell(ActionEvent e) throws SQLException {
        if (e.getSource() == btnRequest) {
            if (checkBoxJoinSell.isSelected()) {
                checkProfile();
            } else {
                AlertNotification.showAlertWarning("", "Bạn chưa đồng ý điều khoản của chúng tôi");
            }
        } else if (e.getSource() == btnCancelRequest) {
            Anch_ThamGiaBanHang.setVisible(false);
            dashboard_form.setVisible(true);
        }
    }

    //---------------------- BẢO TRÌ HỆ THÔNG------------------
    public void baotriHeThong() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> pane_baotri.setVisible(true));
                Thread.sleep(5000);
                Platform.runLater(() -> pane_baotri.setVisible(false));
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();

        task.setOnSucceeded(event -> thread.interrupt());
    }


    private Object[] objects;

    public void calculateMoneyMain() throws SQLException {


        if (objects != null) {
            soDonHangtoday_lb.setText(String.valueOf(objects[2]));
            if (objects[3] != null) {
                doanhThuToday_lb.setText(App.numf.format(objects[3]) + " đ");
            } else {
                doanhThuToday_lb.setText("0");
            }
            SumDaBan_lb.setText(String.valueOf(objects[0]));
            if (objects[1] != null) {
                SumDoanhThu_lb.setText(App.numf.format(objects[1]) + " đ");
                BigDecimal sumdoanhthu = new BigDecimal(String.valueOf(objects[1]));
                if (sumdoanhthu.compareTo(BigDecimal.valueOf(100000000)) >= 0 && Integer.parseInt(String.valueOf(objects[0])) > 10000) {
                    RankSeller.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon-best-seller.png"))));
                }
            } else {
                SumDoanhThu_lb.setText("0");
            }

        } else {
            soDonHangtoday_lb.setText("0");
            doanhThuToday_lb.setText("0");
            SumDaBan_lb.setText("0");
            SumDoanhThu_lb.setText("0");
        }
    }

    private volatile boolean isStopped = false; // Cờ hiệu để kiểm tra trạng thái của Task/Thread

    public void stopTask() {
        isStopped = true;
    }

    public void loadTotalOrder() throws InterruptedException {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {

                if (!isCancelled() || !isStopped) {
                    objects = product_dao.sumTotalOrder(new User(Temp.account.getID(), ""));
                }
                //Thread.sleep(1000);
                Platform.runLater(() -> {
                    try {
                        calculateMoneyMain();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                return null;
            }

        };
        Thread thread = new Thread(task);
        thread.start();

        task.setOnSucceeded(event -> {


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
            System.out.println("Stop thread");
            thread.interrupt();
        });
    }

    public void getObjectUser() {
        user = userDao.SelectByID(new User(Temp.account.getID(), ""));
        try {
            bank = bank_Dao.SelectByID(new Bank(user));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        NameDefault_Label.setText(user.getFullName());
        helloName_label.setText(user.getFullName());
        try {
            avata_img.setImage(new Image(user.getSrcAvatar()));
        } catch (Exception e) {
            avata_img.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/avatarNam.png"))));
        }
    }

    public void hiddenAllForm() {
        dashboard_form.setVisible(false);
        editProfile_Form.setVisible(false);
        banHang_Form.setVisible(false);
        soDuTK_Form.setVisible(false);
        paneLienKetBank.setVisible(false);
        PurchaseProduct_Form.setVisible(false);
        voucherForm.setVisible(false);
        grid_BanHangForm.getChildren().clear();
        grid_Payment.getChildren().clear();
        gridVoucher.getChildren().clear();
        Anch_ThamGiaBanHang.setVisible(false);
    }

    //Click chuyển form
    @FXML
    public void clickConverForm(MouseEvent event) throws IOException {
        hiddenAllForm();
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);

        } else if (event.getSource() == editProfile_btn) {
            editProfile_Form.setVisible(true);
            hiddenInformation(true);
            showInformationUser();
        } else if (event.getSource() == sell_btn) {
            if (Temp.account.getPhanQuyen().equals("Seller")) {
                setValueCategory();
                banHang_Form.setVisible(true);
                refreshDataInSell(event);
            } else {
                dashboard_form.setVisible(true);
                AlertNotification.showAlertWarning("", "Đăng ký tở thành người bán cùng chúng tôi");
            }

        } else if (event.getSource() == soDuTK_btn) {
            soDuTK_Form.setVisible(true);
            showDataSoDuTaiKhoan();
            refreshPayment();
        } else if (event.getSource() == MyShop_txt) {
            stopTask();
            clearScene();
            ConverForm.showForm((Stage) ((Node) event.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/PageHome.fxml", "Trang chủ");
        } else if (event.getSource() == paneLienKetBank_btn) {
            clearDataBank();
            displayInformationBank();
        } else if (event.getSource() == purchaseHistory_pane_btn) {
            PurchaseProduct_Form.setVisible(true);
            refreshDataPurchasedHistory();
        } else if (event.getSource() == showVoucher_pane_btn) {
            DisplayVoucherForm();
        } else if (event.getSource() == thamGiaBanHang_pane_btn) {
            if (Temp.account.getPhanQuyen().equals("Seller")) {
                AlertNotification.showAlertWarning("", "Bạn đã trở thành người bán rồi!");
                dashboard_form.setVisible(true);
            } else {
                Anch_ThamGiaBanHang.setVisible(true);
                loadingDataJoinSell();
            }
        } else if (event.getSource() == changePass_pane_btn) {
            showChangePassForm();
        }
    }


    @FXML
    public void click(MouseEvent e) throws IOException {
        if (e.getSource() == imgRefreshSell) {
            refreshDataInSell(e);
        } else if (e.getSource() == logout_btn) {
            if (AlertNotification.showAlertConfirmation("", "Bạn muốn đăng xuất?")) {
                Temp.account = null;
                stopTask();
                clearScene();
                ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/PageHome.fxml", "Trang chủ");
            }
        } else if (e.getSource() == pane_chatMyShop_btn) {
            baotriHeThong();
        }
    }

    //Xử lý CSS hover và click menu, nếu dùng chung hàm trên khi hidden form sẽ mất all
    public void eventMenu(MouseEvent e) {
        if (e.getSource() == menu_img) {
            asideLeft_Frm.setVisible(true);
            menu_img.setVisible(false);
        } else if (e.getSource() == asideLeft_Frm) {    //Khi di chuột ra ngoài
            asideLeft_Frm.setVisible(false);
            menu_img.setVisible(true);
        }
    }


    public void loadImage() {
        imgloadingOne.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/loading.gif"))));
        imgLoadingTwo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/loading.gif"))));
        imgLoadingThree.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/loading.gif"))));
        imgLoadingFour.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/loading.gif"))));

        imgLoadingPayment.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/loading1.gif"))));

        imgIconDaBan.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon-tong-so-hang-da-ban.png"))));
        imgIconTongDoanhThu.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon-tang-truong-kinh-te.png"))));
        imgIconDaBanToday.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon-don-hang.jpg"))));
        iconDoanhThuToDay.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/iconDoanhThuToday.png"))));


        img_tuVanKH.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon-tu-van-khach-hang.png"))));
        img_ThamgiaBanHang.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon_tham-gia-ban-hang.png"))));
        menu_img.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/menu.png"))));
        icon_soDu.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/money.png"))));
        img_Dashboard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/dashboard.png"))));
        img_SoDuTK.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/wallet.png"))));
        img_SuaHoSo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon_suahoso.png"))));
        img_Logout.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon_logout.png"))));
        img_BanHang.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon_cuahang.png"))));
        purchaseHistory.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/purchaseHistory.png"))));
        Calendar_img.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/Calendar.png"))));
        changePassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/changePassword.png"))));
        voucher_img.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/voucher.png"))));
        bank_img.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/bank.png"))));
        img_clickChuyenTien.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/iconClickChuyenTien.png"))));
        img_clickNapTien.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/iconNapTien.png"))));
        imgRutTien.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconRutTien.png"))));
        imgRefreshSell.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon-refresh.png"))));
        img_Baotri_ChatMyShop.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/thong-bao-bao-tri-he-thong.jpg"))));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DateTime_label.setText(App.timeDay);
        loadImage();
        defaultAddress();

        Platform.runLater(this::getObjectUser);
        if (Temp.account.getPhanQuyen().equals("Seller")) {
            paneHeader_Main.setVisible(true);
            try {
                loadTotalOrder();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }

    //Set data lên gridview
    public void setDataGridPane(GridPane gridPane, AnchorPane anchorPane, int col, int row) {
        gridPane.add(anchorPane, col, row); // (child,column,row)
        // set grid width
        gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setMaxWidth(Region.USE_PREF_SIZE);

        // set grid height
        gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setMaxHeight(Region.USE_PREF_SIZE);
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

    public void clearScene() {
        listPaymentHistory.clear();
        listProducts.clear();
        listPurchaseProducts.clear();
        grid_Payment.getChildren().clear();
        grid_PurchaseProduct.getChildren().clear();
        grid_BanHangForm.getChildren().clear();
        gridVoucher.getChildren().clear();

        imgloadingOne.setImage(null);
        imgLoadingTwo.setImage(null);
        imgLoadingThree.setImage(null);
        imgLoadingFour.setImage(null);
        imgLoadingPayment.setImage(null);

        imgIconDaBan.setImage(null);
        imgIconTongDoanhThu.setImage(null);
        imgIconDaBanToday.setImage(null);
        iconDoanhThuToDay.setImage(null);


        img_tuVanKH.setImage(null);
        img_ThamgiaBanHang.setImage(null);
        menu_img.setImage(null);
        icon_soDu.setImage(null);
        img_Dashboard.setImage(null);
        img_SoDuTK.setImage(null);
        img_SuaHoSo.setImage(null);
        img_Logout.setImage(null);
        img_BanHang.setImage(null);
        purchaseHistory.setImage(null);
        Calendar_img.setImage(null);
        changePassword.setImage(null);
        voucher_img.setImage(null);
        bank_img.setImage(null);
        img_clickChuyenTien.setImage(null);
        img_clickNapTien.setImage(null);
        imgRutTien.setImage(null);

        imgRefreshSell.setImage(null);
        Thread.interrupted();
        System.gc();
    }
}
