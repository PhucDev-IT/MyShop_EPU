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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class UserProfileController implements Initializable {

    //Phí rút tiền
    private static final BigDecimal phiRutTien = new BigDecimal("5000");
    static final String Nam = "Nam";
    static final String Nu = "Nữ";
    static final String Khac = "Khác";

    static final int maxProduct = 10;

    @FXML
    private Text MyShop_txt;

    @FXML
    private ImageView imgLoadingData;

    // ------------------------------------- DASBROAD ---------------------------------------------------
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

   // private String urlSP;

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

    //------------------------------ LIÊN KẾT NGÂN HÀNG -----------------------------------------------------
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

    private String UrlAvatar;   //Lưu trữ tạm thời link img vừa được chonj

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

    private List<Product> listProducts = new ArrayList<>();
    private Category category = new Category();


    static boolean isStringEmpty(String string) {
        return string == null || string.length() == 0;
    }


    //---------------------------------------------- FORM BÁN HÀNG ----------------------------------------------------

    String lastEvent = "select";    //Gỉa thiết để kiếm tra dun cho thread cuộn

    public void clearTextData(){
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

        if(e.getSource() == importImageHang_btn){
             stage = (Stage) banHang_Form.getScene().getWindow();

        }else if(e.getSource() == avata_img) {
             stage = (Stage) asideLeft_Frm.getScene().getWindow();
        }
        File file = open.showOpenDialog(stage);

        return file;
    }

    public void addImgProduct(ActionEvent e){
        File file = AddImage(e);
        if(file!=null){

            imgSP.setImage(new Image(file.toURI().toString()));
        }
    }

    public void addImgAvatar(MouseEvent e){
        File file = AddImage(e);
        if(file!=null){
            UrlAvatar = file.toURI().toString();
            avata_img.setImage(new Image(file.toURI().toString()));
        }
        user.setSrcAvatar(UrlAvatar);
        userDao.Update(user);
    }

    public void refreshDataInSell(Event e){
        SuaSP_btn.setDisable(true);
        xoaSP_btn.setDisable(true);
        clearTextData();
        lastIndex.set(0);
        listProducts.clear();
        grid_BanHangForm.getChildren().clear();
        loadingData(e);

    }

    //Loading data
    public synchronized void loadingData(Event e){
        imgLoadingData.setImage(new Image("C:\\Users\\84374\\Downloads\\Ellipsis-1.4s-583px.gif"));
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() ->imgLoadingData.setVisible(true) );

                if(isCancelled()){
                    return null;
                }
                Thread.sleep(200);
                Platform.runLater(() -> {

                    if(e.getSource() == btnSearchProduct){  //Nếu nhấn tìm kiếm thì
                        ProductBySearch();
                    }else if(e instanceof ScrollEvent){
                        if(lastEvent.equals("select")){
                            System.out.println("Cuộn cho select");
                            SelectDataProduct();
                        }else {
                            System.out.println("Cuộn cho tìm kiếm");
                            ProductBySearch();
                        }
                    }
                    else {
                        System.out.println("scrool");
                        SelectDataProduct();
                    }
                });

                Thread.sleep(400);
                Platform.runLater(() -> imgLoadingData.setVisible(false));

                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        task.setOnSucceeded(event -> {
            System.out.println("close thread");
            thread.interrupt();
        });
    }

    private AtomicInteger lastIndex = new AtomicInteger(0);

    public static AtomicBoolean isReusltProduct = new AtomicBoolean(false); //Ban đầu rỗng

    public void ProductBySearch(){

        try{
            listProducts.addAll(product_dao.SearchProductOfSeller(user,nameProductSearch,lastIndex,maxProduct));
        }catch (OutOfMemoryError e)
        {
            System.out.println("Tràn List: "+e.getMessage());
        }
        calculateNumbers();
    }

    public void SelectDataProduct(){
        try{
            listProducts.addAll(product_dao.selectProductOfUser(user,lastIndex,maxProduct));
        }catch (OutOfMemoryError | SQLException e)
        {
            System.out.println("Tràn List: "+e.getMessage());
        }
        calculateNumbers();
    }
    public void calculateNumbers()  {

        for (int i = 0; i < listProducts.size() && i >= lastIndex.get(); i++) {
            setDataInBanHangForm(listProducts.get(i));
        }

        if(listProducts.size() == lastIndex.get()){ //Nếu không có thêm sp mới thì đã hết sản phâ trong csdl gán false đ khỏi cuộn
            isReusltProduct.set(false);
        }else{
            isReusltProduct.set(true);
        }
        lastIndex.set(lastIndex.get() + (listProducts.size()-lastIndex.get()));
    }

    //Khi cuôộn sản phẩm trong bán sản phẩm
    public void eventScroll(ScrollEvent e){
        if(isReusltProduct.get()){
            loadingData(e);
        }
    }

    public void AddProducts(ActionEvent e) throws SQLException {

        String nameSP = tenHang_txt.getText();
        int SoLuong = Integer.parseInt(soLuong_txt.getText());

        // Mục đích khi người bán nhập giá bán có các kí tự thì ....
        String giaBan = deleteChar(DonGia_txt.getText());
        BigDecimal DonGia = new BigDecimal(giaBan);

        String moTa = moTa_txta.getText();
        String srcImg = imgSP.getImage().getUrl();

        int IdCategory = -1;
        IdCategory=category.listCategory.get(danhMuc_choisebox.getValue());

        if (IdCategory == -1 || isStringEmpty(srcImg) || isStringEmpty(soLuong_txt.getText()) || isStringEmpty(giaBan)) {
            AlertNotification.showAlertWarning("", "Thiếu thông tin!");
        } else {

            Product pro = new Product(0,nameSP, SoLuong, DonGia, moTa, srcImg, IdCategory, user);

            if (!product_dao.Insert(pro)) {
                AlertNotification.showAlertError("Có lỗi xảy ra!", "Thêm sản phẩm thất bại.");
            } else {
                //Mỗi lần thêm thành công sẽ k truy vẫn lại dữ liệu từ CSDL nữa, thêm trực tiếp vào arraylist
                listProducts.add(pro);
                clearTextData();
                grid_BanHangForm.getChildren().clear();     //Nếu để trong setDataInBanHangForm khi cuộn sẽ mất d liệu
                for(Product product:listProducts)
                setDataInBanHangForm(product);
            }
        }
    }

    // Button: Sửa thông tin sản phẩm
    public void UpdateProduct(ActionEvent e) throws FileNotFoundException, SQLException {
        String img = imgSP.getImage().getUrl();
        String tenhang = tenHang_txt.getText();
        int SoLuong = Integer.parseInt(soLuong_txt.getText());
        String giaBan = deleteChar(DonGia_txt.getText());
        BigDecimal DonGia = new BigDecimal(giaBan);
        String mota = moTa_txta.getText();

        int IdCategory = -1;
        IdCategory= category.listCategory.get(danhMuc_choisebox.getValue());

        if (IdCategory == -1 ||isStringEmpty(tenhang) || isStringEmpty(soLuong_txt.getText()) || isStringEmpty(giaBan)) {
            AlertNotification.showAlertWarning("", "Thiếu thông tin!");
        }else {
            Product pro = new Product(pro_onclick.getID(), tenhang, SoLuong, DonGia, mota, img, IdCategory, user);
            if (product_dao.Update(pro)>0) {
                AlertNotification.showAlertSucces("", "Cập nhật thành công");
                refreshDataInSell(e);
            } else {
                AlertNotification.showAlertWarning("", "Có lỗi xảy ra!");
            }
        }

    }

    // button: Xóa sản phẩm
    public void RemoveProducts(ActionEvent e) throws SQLException {

        if (AlertNotification.showAlertConfirmation("", "Bạn chắc chắn muốn xóa sản phẩm này?")) {

            pro_onclick.setActivity("LOCK");    //Sản phẩm đang được chọn

            if (product_dao.Update(pro_onclick) > 0) {
                AlertNotification.showAlertSucces("", "Xóa thành công!");
                for(Product product : listProducts){
                    if(product.getID() == pro_onclick.getID()){
                        listProducts.remove(product);
                        continue;
                    }
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
        if(!isStringEmpty(nameProductSearch)){
            refreshDataInSell(e);
        }

    }

    public void clickProducts() {
        myListenerProduct = new MyListener<Product>() {
            @Override
            public void onClickListener(Product t) {
                pro_onclick = t;

                try {
                    imgSP.setImage(new Image(t.getSrcImg()));
                }catch (Exception e){
                    imgSP.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png")));
                }
                tenHang_txt.setText(t.getTenSP());
                soLuong_txt.setText(t.getQuantity() + "");
                DonGia_txt.setText(App.numf.format(t.getPrice()) + "");

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


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setValueCategory(){
        ObservableList<String> languages = FXCollections.observableArrayList();
        for (Map.Entry entry : category.listCategory.entrySet()){
            languages.add(entry.getKey()+"");
        }
        danhMuc_choisebox.setItems(languages);
    }

    //------------------------------ LIÊN KẾT NGÂN HÀNG-----------------------------------------------
    public String hiddenNumbersBank(String str)
    {
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
    public void clearDataBank(){
        txtFullNameInBank.setText("");
        txtCccdInBank.setText("");
        cbNameBank.setValue("");
        txtTenChiNhanh.setText("");
        txtSoTaiKhoan.setText("");
        txtChuTaiKhoanBank.setText("");
    }
    public void setDataListBank(){
        ObservableList<String> listBank = FXCollections.observableArrayList();
        for (Map.Entry entry : Bank.listBanks.entrySet()){
            listBank.add(entry.getKey()+"-"+entry.getValue());
        }
        cbNameBank.setItems(listBank);
    }
    public void displayInformationBank() throws SQLException {

        if(Temp.bank!=null){
            bank = Temp.bank;
        }else{
            bank = bank_Dao.SelectByID(new Bank(user));
            Temp.bank = bank;
        }
        setDataListBank();
        if(bank==null){
            btnDeleteBank.setDisable(true);
            btnUpdateBank.setDisable(true);
            btnAddBank.setDisable(false);
        }else{
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

    public boolean checkInputBank(){
        String hovaTen = txtFullNameInBank.getText();
        String CCCD = txtCccdInBank.getText();
        String tenNganHang = cbNameBank.getValue();
        String chiNhanh = txtTenChiNhanh.getText();
        String stk = txtSoTaiKhoan.getText();
        String chuTaiKhoan = txtChuTaiKhoanBank.getText();

        if(!hovaTen.equals(chuTaiKhoan) && (!isStringEmpty(hovaTen) || !isStringEmpty(chuTaiKhoan))){
            AlertNotification.showAlertWarning("","Họ và tên và chủ sở hữu phải giống nhau");
        }else if(CCCD.length()<=8 || stk.length()<=9){
            AlertNotification.showAlertWarning("Vui lòng nhập chính xác căn cước công dân và số tài khoản!","");
        }else if(isStringEmpty(tenNganHang) || isStringEmpty(chiNhanh)){
            AlertNotification.showAlertWarning("Vui lòng nhập đầy đủ thông tin","");
        }else{
            bank = new Bank(stk,chiNhanh,CCCD,tenNganHang,chuTaiKhoan,user);
            return true;
        }
        return false;
    }
    public void addBank(ActionEvent e) throws SQLException {
        if(!checkInputBank()){
            return;
        }
        if(bank_Dao.Insert(bank)){
            AlertNotification.showAlertSucces("Thêm thành công","");
        }else{
            AlertNotification.showAlertError("Có lỗi xảy ra, thử lại sau!","");
        }
    }

    public void UpdateBank(ActionEvent e) throws SQLException {
        if(!checkInputBank()){
            return;
        }
        if(bank_Dao.Update(bank)>0){
            AlertNotification.showAlertSucces("Sửa thành công","");
        }else{
            AlertNotification.showAlertError("Có lỗi xảy ra, thử lại sau!","");
        }
    }

    public void deleteBank(ActionEvent e){
        if(AlertNotification.showAlertConfirmation("Bạn chắc chắn xóa liên kết ngân hàng!","" +
                "Đồng ý, việc mua bán có thể bị gián đoạn")){
            if(bank_Dao.Delete(bank)>0){
                AlertNotification.showAlertSucces("Xóa thành công","");
                clearDataBank();
            }else{
                AlertNotification.showAlertError("Có lỗi xảy ra, thử lại sau!","");
            }
        }
    }

    //---------------------------------- FORM SỐ DƯ TÀI KHOẢN ----------------------------------------------
    public void showDataSoDuTaiKhoan() throws SQLException {
        labsoDu_RutTienForm.setText(App.numf.format(Temp.account.getMoney()));

        if(Temp.bank == null){
            bank = bank_Dao.SelectByID(new Bank(user));
            Temp.bank = bank;
        }else{
            LbSTK_rutTienForm.setText(hiddenNumbersBank(bank.getSoTaiKhoan()));
            LbTenNH_RTForm.setText(bank.getTenNH());
            LbShuSoHuu_RTForm.setText(bank.getChuSoHuu());
        }
    }

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

    public void removeInputChar(KeyEvent e){
        if(e.getSource() == soTienChuyen_txt){
            removeChar(soTienChuyen_txt);
        }else if(e.getSource() == txtSoTienRut_RTForm){
            removeChar(txtSoTienRut_RTForm);
        }
    }
    public void withdrawMoney(ActionEvent e){

        if(isStringEmpty(txtSoTienRut_RTForm.getText())){
            AlertNotification.showAlertWarning("","Nhập số tiền muốn rút!");
        }else {

            BigDecimal soTienRut = new BigDecimal(deleteChar(txtSoTienRut_RTForm.getText()));
            soTienRut.add(phiRutTien);

            if (Temp.account.getMoney().compareTo(BigDecimal.ZERO) <= 0) {
                AlertNotification.showAlertWarning("", "Số dư không đủ!");
            } else if (soTienRut.compareTo(Temp.account.getMoney()) > 0) {
                AlertNotification.showAlertWarning("", "Số dư không đủ!");
            }else{
                String notification = "Số tiền bạn nhận được là: "+App.numf.format(soTienRut.subtract(phiRutTien));
                if(AlertNotification.showAlertConfirmation(notification,"Bạn chắc chắn muốn rút?")){
                    PaymentHistory paymentBank = new PaymentHistory("Rút tiền","Rút về ngân hàng", soTienRut,
                            new Date(System.currentTimeMillis()), "/com/epu/oop/myshop/image/iconRutTien.png", Temp.user,null);
                    Temp.account.setMoney(Temp.account.getMoney().subtract(soTienRut));
                    if(account_dao.Update(Temp.account)>0){
                        AlertNotification.showAlertSucces("Rút tiền thành công","");
                    }else{
                        AlertNotification.showAlertError("Có lỗi xảy ra, thử lại sau!","");
                    }
                }
            }
        }
    }

    //Button Chuyển tiền
    public void transferMoney(){
        //Account a = account_dao.SelectByID();

        if(isStringEmpty(taiKhoanNhan_txt.getText()) || isStringEmpty(soTienChuyen_txt.getText())){
            AlertNotification.showAlertWarning("","Vui lòng nhập đầy đủ thông tin");
        }else{

        }
    }












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
                if (isCancelled()) {
                    return null;
                }
                Platform.runLater(() -> getObjectUser());
                if (isCancelled()) {
                    return null;
                }
                Thread.sleep(2000);
                Platform.runLater(() -> {
                    try {
                        calculateMoneyMain();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                Thread.sleep(1000);
                if (isCancelled()) {
                    return null;
                }
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
        Thread thread = new Thread(task);
        thread.start();
        task.setOnSucceeded(event -> {
            thread.interrupt();
        });
    }
    public void getObjectUser(){
        if(Temp.user!=null){
            user = Temp.user;

        }else{
            user = userDao.SelectByID(new User(Temp.account.getID()));

        }

        NameDefault_Label.setText(user.getFullName());
        helloName_label.setText(user.getFullName());
        try{
            avata_img.setImage(new Image(user.getSrcAvatar()));
        }catch (Exception e){
            avata_img.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/avatarNam.png")));
        }
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
            if(Temp.account.getPhanQuyen().equals("Seller")){
                setValueCategory();
                banHang_Form.setVisible(true);
                loadingData(event);
            }else{
                AlertNotification.showAlertWarning("","Đăng ký tở thành người bán cùng chúng tôi");
            }

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


    public void click(MouseEvent e){
        if(e.getSource() == imgRefreshSell){
            refreshDataInSell(e);
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

        img_tuVanKH.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon-tu-van-khach-hang.png")));
        img_ThamgiaBanHang.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon_tham-gia-ban-hang.png")));
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

        imgRefreshSell.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\icon_refresh.jpg"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DateTime_label.setText(App.timeDay);
        loadImage();

        if(Temp.account.getPhanQuyen().equals("Seller")) {
            paneHeader_Main.setVisible(true);
            try {
                loadTotalOrder();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
