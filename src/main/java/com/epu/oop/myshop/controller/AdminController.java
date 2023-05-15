package com.epu.oop.myshop.controller;


import com.epu.oop.myshop.Dao.*;
import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class AdminController implements Initializable {
    private   ConnectionPool connectionPool = new ConnectionPool();

    private Account_Dao account_Dao = Account_Dao.getInstance(connectionPool);

    private UserDao user_dao = UserDao.getInstance(connectionPool);

    private Product_Dao prod_dao = Product_Dao.getInstance(connectionPool);

<<<<<<< Updated upstream
=======
    @FXML
    private AnchorPane paneLoading;

    @FXML
    private ImageView imgLoading;

    @FXML
    private ImageView imgHome;
>>>>>>> Stashed changes

    // --------------------------- Quản Lý Danh Mục FORM  -------------------------------------------------------



    @FXML
    private JFXButton QLProduct_Btn;

    @FXML
    private JFXButton Notification_btn;

    @FXML
    private JFXButton logout_btn;

    @FXML
    private AnchorPane QLDanhMuc_Form;




    // ------------------------------- FORM Quản Lý Tài Khoản --------------------------------------------
    @FXML
    private JFXButton QLAccount_Btn;

    @FXML
    private AnchorPane QLAccount_Form;

    @FXML
    private JFXTextField searchUser_AccountFrm;

    @FXML
    private Label LbName_AccountFrm;

    @FXML
    private Label LbBirth_AccountFrm;

    @FXML
    private Label LbAddress_Account_Frm;

    @FXML
    private Label LbLienHe_AccountFrm;

    @FXML
    private ComboBox<String> PhanQuyen_AccountFrm;

    @FXML
    private TableView<Account> tbleViewAccount;

    @FXML
    private TableColumn<Account, String> userName_Col;

    @FXML
    private TableColumn<Account, String> Password_Col;

    @FXML
    private TableColumn<Account, String> Status_Col;

    @FXML
    private TableColumn<Account, String> PhanQuyen_Col;


    @FXML
    private AnchorPane informationUser_AccountFrm;

    private List<Account> ListAccount = new ArrayList<>();

    ObservableList<Account> listAccounts = FXCollections.observableArrayList();


    private User user;


    public void initTableInAccountForm()
    {
        ListAccount = account_Dao.SelectAll();
        for (Account account : ListAccount) {
            listAccounts.add(account);
        }
        userName_Col.setCellValueFactory(new PropertyValueFactory<Account,String>("UserName"));
        Password_Col.setCellValueFactory(new PropertyValueFactory<Account,String>("Password"));
        Status_Col.setCellValueFactory(new PropertyValueFactory<Account,String>("Status"));
        PhanQuyen_Col.setCellValueFactory(new PropertyValueFactory<Account,String>("PhanQuyen"));

        tbleViewAccount.setItems(listAccounts);

    }

    @FXML
    public void removeAccount(ActionEvent e) throws FileNotFoundException {
        int selected = tbleViewAccount.getSelectionModel().getSelectedIndex();
        Account accout = tbleViewAccount.getSelectionModel().getSelectedItem();

        boolean check = AlertNotification.showAlertConfirmation("",
                "Bạn chắc chắn muốn xóa tài khoản : "+accout.getUserName() +" này?");

        if(check) {
            accout.setStatus("Remove");
            int delete = account_Dao.Update(accout);
            if(delete>0) {
                AlertNotification.showAlertSucces("", "Xóa tài khoản: "+ accout.getUserName()+ " thành công");
                tbleViewAccount.getItems().remove(selected);
            }
            else
                AlertNotification.showAlertWarning("", "Có lỗi xảy ra!");
        }
    }


    @FXML
    public void OnLockAccount(ActionEvent e) throws FileNotFoundException {
        int selected = tbleViewAccount.getSelectionModel().getSelectedIndex();
        Account accout = tbleViewAccount.getSelectionModel().getSelectedItem();

        if(accout.getStatus().equals("ON"))
        {
            accout.setStatus("LOCK");
        }else if(accout.getStatus().equals("LOCK")) {
            accout.setStatus("ON");
        }

        int check = account_Dao.Update(accout);
        if(check>0)
        {
            listAccounts.get(selected).setStatus(accout.getStatus());
            AlertNotification.showAlertSucces("", "Tài khoản: "+accout.getUserName()+" vừa "+accout.getStatus());
            tbleViewAccount.refresh();
        }else {
            AlertNotification.showAlertWarning("", "Có lỗi xảy ra");
        }
    }

    @FXML
    public void PhanQuyen(MouseEvent e)
    {

        Account accout = tbleViewAccount.getSelectionModel().getSelectedItem();

        if(PhanQuyen_AccountFrm.getValue()!=null)
        {
            String phanquyen = PhanQuyen_AccountFrm.getValue();
            if(accout.getPhanQuyen()!=phanquyen)
            {
                if(AlertNotification.showAlertConfirmation("WARNING","Bạn chắc chắn " +
                        "muốn thay đổi?")) {

                    accout.setPhanQuyen(phanquyen);
                    account_Dao.Update(accout);
                }
            }
        }
    }

    public void showInformationUser()
    {
        Account accout = tbleViewAccount.getSelectionModel().getSelectedItem();
        user = new User(accout.getID());
        user = user_dao.SelectByID(user);

        informationUser_AccountFrm.setVisible(true);
        LbName_AccountFrm.setText(user.getFullName());
        LbLienHe_AccountFrm.setText(user.getNumberPhone());
        if(user.getDateOfBirth()!=null)
            LbBirth_AccountFrm.setText(user.getDateOfBirth().toLocalDate()+"");
        LbAddress_Account_Frm.setText(user.getAddress());
    }

    //------------------------------- QUẢN LÝ VOUCHER -----------------------------------------------------------
    @FXML
    private JFXButton QLVoucher_btn;
    @FXML
    private AnchorPane QLVoucher_form;

    @FXML
    private Tab voucherTab;
    @FXML
    private TextField maVoucher_txt;

    @FXML
    private JFXButton ramdomVoucher_btn;

    @FXML
    private TextField tileGiamGia_txt;

    @FXML
    private TextField soLuong_txt;

    @FXML
    private DatePicker ngayBatDau_date;

    @FXML
    private DatePicker ngayKTDate;

    @FXML
    private TextArea noiDung_txa;

    @FXML
    private JFXButton them_btn;

    @FXML
    private ImageView imgVoucher;

    @FXML
    private JFXButton addImgVoucher_btn;

    @FXML
    private TextField sotienGiam_txt;

    @FXML
    private GridPane gridVoucher;

    @FXML
    private TextField EmailUserVoucher_txt;

                    //-------------------------TAB QUẢN LÝ VOUCHER------------------------

    @FXML
    private TableView<VoucherModel> TableView_Voucher;

    @FXML
    private TableColumn<VoucherModel, String> colMaVoucher;

    @FXML
    private TableColumn<VoucherModel, Float> colTiLeGiam;

    @FXML
    private TableColumn<VoucherModel, BigDecimal> ColSoTienGiam;

    @FXML
    private TableColumn<VoucherModel, Integer> ColSoLuongVoucher;

    @FXML
    private TableColumn<VoucherModel, Date> colNgayBatDau;

    @FXML
    private TableColumn<VoucherModel, Date> colNgayKetThuc;

    @FXML
    private Tab QLVoucher_tab;

    @FXML
    private TabPane tabPane_Voucher;

    @FXML
    private TableColumn<VoucherModel, String> ColNoiDung;

    private List<VoucherModel> listAllVouchers;
    //--------------------
    private String imgVoucherTemp;
    private List<VoucherModel> listVouchers = new ArrayList<>();

    private VoucherDao voucherDao = VoucherDao.getInstance(connectionPool);

    private VoucherModel voucher;
    private MyListener<VoucherModel> myListener_Voucher;

    public void clickChooseVoucher(){
        myListener_Voucher = new MyListener<VoucherModel>() {
            @Override
            public void onClickListener(VoucherModel t) {
                voucher = t;
                showInformationVoucher();
            }
        };
    }

    //Khi nhấn vào 1 voucher bâất kì thì hiển thị thông tin
    public void showInformationVoucher(){
        imgVoucher.setImage(new Image(voucher.getImgVoucher()));
        maVoucher_txt.setText(voucher.getMaVoucher());
        tileGiamGia_txt.setText(voucher.getTiLeGiamGia()+"");
        if(voucher.getSoTienGiam()!=null)
            sotienGiam_txt.setText(App.numf.format(voucher.getSoTienGiam()));
        else
            sotienGiam_txt.setText("");
        App.formatter.format(voucher.getNgayKetThuc());
        ngayKTDate.setValue(voucher.getNgayKetThuc().toLocalDate());
        ngayBatDau_date.setValue(voucher.getNgayBatDau().toLocalDate());
        soLuong_txt.setText(voucher.getSoLuong()+"");
        noiDung_txa.setText(voucher.getNoiDung());
    }
    public void addImageVoucher(ActionEvent e){
        imgVoucherTemp = importImageOnForm(QLVoucher_form,imgVoucher);
    }

    public void ConverTab(MouseEvent e){
        //Tab đầu tiên = 0;
        int selectedIndex = tabPane_Voucher.getSelectionModel().getSelectedIndex();
        if (selectedIndex == 0) {
            listVouchers = voucherDao.SelectAll();
            displayVoucherForm();
        } else if (selectedIndex == 1) {
            setEmptyElement();
            listAllVouchers = voucherDao.SelectAll();
            setDataQLVoucher();
        }
    }

    public void removeKiTuDacBiet(String str){
        if (str.contains(".")) {
            str = str.replace('.', ' ');
        }
        if (str.contains(",")) {
            str = str.replace(',', ' ');
        }
        if (str.contains(" ")) {
            str = str.replaceAll(" ", "");
        }
    }
    public void checkInputGiamGia(KeyEvent e){
        if(e.getSource() == tileGiamGia_txt){
            sotienGiam_txt.setDisable(true);
        }else if(e.getSource() == sotienGiam_txt){
            tileGiamGia_txt.setDisable(true);
        }else if(e.getSource() == EmailUserVoucher_txt){
            soLuong_txt.setDisable(true);
            soLuong_txt.setText("1");
        }
        if(tileGiamGia_txt.getText().equals("")){
            sotienGiam_txt.setDisable(false);
        }
        if(isStringEmpty(sotienGiam_txt.getText())){
            tileGiamGia_txt.setDisable(false);
        }
        if(isStringEmpty(EmailUserVoucher_txt.getText())){
            soLuong_txt.setDisable(false);
        }

    }
    public void setEmptyElement(){
        maVoucher_txt.setText("");
        tileGiamGia_txt.setText("");
        sotienGiam_txt.setText("");
        soLuong_txt.setText("");
        noiDung_txa.setText("");
        ngayBatDau_date.setValue(null);
        ngayKTDate.setValue(null);
        imgVoucher.setImage(null);
    }
    public void AddVoucher(ActionEvent e) throws SQLException {
        if (ngayKTDate.getValue().compareTo(ngayBatDau_date.getValue()) >= 0) {

            float tilegiamgia = 0;
            if (!tileGiamGia_txt.getText().equals("")) {
                String sale = tileGiamGia_txt.getText();
                if (sale.contains("%")) {
                    sale = sale.replace("%", "");
                }
                tilegiamgia = (Float.parseFloat(sale));
            }
            BigDecimal SoTienGiamGia = null;
            if (!sotienGiam_txt.getText().equals("")) {
                String sotien = sotienGiam_txt.getText();
                removeKiTuDacBiet(sotien);
                SoTienGiamGia = new BigDecimal(sotien);
            }

            VoucherModel voucher = new VoucherModel(maVoucher_txt.getText(), tilegiamgia, SoTienGiamGia,
                    (Integer.parseInt(soLuong_txt.getText())), noiDung_txa.getText(), imgVoucherTemp,
                    Date.valueOf(ngayBatDau_date.getValue()), Date.valueOf(ngayKTDate.getValue()));
            if (!isStringEmpty(EmailUserVoucher_txt.getText())) {
                if (voucherDao.GiftVoucher(voucher, EmailUserVoucher_txt.getText()) <= 0) {
                    AlertNotification.showAlertError("", "Người dùng không tồn tại");
                }else{
                    AlertNotification.showAlertSucces("", "Thêm thành công");
                    setEmptyElement();
                }
            }else{
                if (voucherDao.Insert(voucher)) {
                    AlertNotification.showAlertSucces("", "Thêm thành công");
                    setEmptyElement();
                }else {
                    AlertNotification.showAlertError("", "Có lỗi xảy ra");
                }
            }

        }else{
            AlertNotification.showAlertWarning("", "Ngày kết thúc voucher phải lớn hơn ngày bắt đầu.");
        }
    }

    @FXML
    public void FormatDate(MouseEvent e){
        if(e.getSource() == ngayBatDau_date){
            ConverterFormDatepicker(ngayBatDau_date);
        }else if(e.getSource() == ngayKTDate){
            ConverterFormDatepicker(ngayKTDate);
        }
    }

    public void RamDomMaVoucher(ActionEvent e){
        RanDom rd = new RanDom();
        Thread thread = new Thread(rd);
        thread.start();
        rd.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                maVoucher_txt.setText(String.valueOf(newValue));
                thread.interrupt();

            }
        });
    }

    public void displayVoucherForm(){
        int col = 0;
        int row = 1;
        clickChooseVoucher();
        try {
            for (int i = 0; i < listVouchers.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/Voucher.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                VoucherController item = fxmlLoader.getController();
                item.setData(myListener_Voucher,listVouchers.get(i));

                row++;
                setDataGridPane(gridVoucher,anchorPane,col,row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private ObservableList<VoucherModel> obsListVoucher = FXCollections.observableArrayList();
    //Quản lý voucher
    public void setDataQLVoucher(){
        obsListVoucher.clear();
        for(VoucherModel vc : listAllVouchers){
            obsListVoucher.add(vc);
        }

        colMaVoucher.setCellValueFactory(new PropertyValueFactory<VoucherModel,String>("MaVoucher"));
        colTiLeGiam.setCellValueFactory(new PropertyValueFactory<VoucherModel,Float>("TiLeGiamGia"));
        ColSoTienGiam.setCellValueFactory(new PropertyValueFactory<VoucherModel,BigDecimal>("soTienGiam"));
        ColSoLuongVoucher.setCellValueFactory(new PropertyValueFactory<VoucherModel,Integer>("soLuong"));
        colNgayBatDau.setCellValueFactory(new PropertyValueFactory<VoucherModel,Date>("ngayBatDau"));
        colNgayKetThuc.setCellValueFactory(new PropertyValueFactory<VoucherModel,Date>("ngayKetThuc"));
        ColNoiDung.setCellValueFactory(new PropertyValueFactory<VoucherModel,String>("noiDung"));

        TableView_Voucher.setItems(obsListVoucher);
    }


    //------------------------------------------ Quản Lý Sản Phẩm Form --------------------------------------------------
    @FXML
    private AnchorPane QLSanPham_Form;

    @FXML
    private GridPane grid_QLProduct;

    @FXML
    private JFXTextField nameProduct_Jtxt;

    @FXML
    private JFXButton deleteProduct;

    private MyListener<Product> myListenerProducts;
    private Product prod_temp;


    public void deleteProduct(ActionEvent e)
    {

        if(AlertNotification.showAlertConfirmation("WARNING", "Bạn chắc chắn muốn xóa sản phẩm này?")) {
            prod_dao.Delete(prod_temp);
        }

    }


    public void onclick() {
        myListenerProducts = new MyListener<Product>() {
            @Override
            public void onClickListener(Product t) {
                nameProduct_Jtxt.setText(t.getTenSP());
                prod_temp = t;
            }
        };


    }


//    public void setDataProducts()
//    {
//        int col = 0;
//        int row = 1;
//        onclick();
//        try {
//            for(Product p : Temp.Listproducts){
//                if(p.getStatus().equals("LOCK"))
//                    continue;
//
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("/com/oop/myshop/GUI/ItemOfMarket.fxml"));
//                AnchorPane anchorPane = fxmlLoader.load();
//                ItemMarketController item = fxmlLoader.getController();
//                item.setData(myListenerProducts, p);
//
//                if(col == 4)
//                {
//                    col = 0;
//                    row++;
//                }
//
//                grid_QLProduct.add(anchorPane, col++, row); //(child,column,row)
//                //set grid width
//                grid_QLProduct.setMinWidth(Region.USE_COMPUTED_SIZE);
//                grid_QLProduct.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                grid_QLProduct.setMaxWidth(Region.USE_PREF_SIZE);
//
//                //set grid height
//                grid_QLProduct.setMinHeight(Region.USE_COMPUTED_SIZE);
//                grid_QLProduct.setPrefHeight(Region.USE_COMPUTED_SIZE);
//                grid_QLProduct.setMaxHeight(Region.USE_PREF_SIZE);
//
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }







    public void ConverScene(ActionEvent e) {
        if (e.getSource() == QLAccount_Btn) {
            QLSanPham_Form.setVisible(false);
            QLVoucher_form.setVisible(false);
            QLAccount_Form.setVisible(true);
            initTableInAccountForm();

        } else if (e.getSource() == QLProduct_Btn) {

            QLAccount_Form.setVisible(false);
            QLVoucher_form.setVisible(false);
            QLSanPham_Form.setVisible(true);
            //setDataProducts();
        }else if(e.getSource() == QLVoucher_btn){

            QLAccount_Form.setVisible(false);
            QLSanPham_Form.setVisible(false);
            QLVoucher_form.setVisible(true);
        }

    }
    @FXML
<<<<<<< Updated upstream
    private Label close;

    @FXML
    private Label minisize;

    public void click(MouseEvent e) throws IOException {
        if (e.getSource() == close) {
            System.exit(0);
        } else if (e.getSource() == minisize) {
            Stage stage = (Stage) minisize.getScene().getWindow();
            stage.setIconified(true);
        }else if(e.getSource() == logout_btn)
        {
            Temp.account=null;
            ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/oop/myshop/GUI/LoginForm.fxml","Đăng nhập");
        }
    }

=======
    public void goHome(MouseEvent e)
    {
         if(e.getSource() == imgHome){
        try{
            ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/PageHome.fxml","Trang chủ");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    }
>>>>>>> Stashed changes

    public void setDefaulValue()
    {
        ObservableList<String> PhanQuyen = FXCollections.observableArrayList("Admin", "Seller","Member");
        PhanQuyen_AccountFrm.setItems(PhanQuyen);
    }

    //Method chung cho import ảnh từ hệ thống máy tính
    public String importImageOnForm(AnchorPane anchorPane,ImageView img) {
        String url = null;
        FileChooser open = new FileChooser();
        open.setTitle("Vui lòng chọn ảnh");
        // Chỉ hiển thị những image có đuôi....
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*png", "*jpg"));
        // Khung chứa nơi import
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        File file = open.showOpenDialog(stage);

        if (file != null) {
            url = file.toURI().toString();
            img.setImage(new Image(file.toURI().toString()));
        }
        return url;
    }
    //Kiểm tra chuỗi nhập vào có rỗng hay không
    public boolean isStringEmpty(String str){
        return str==null || str.equals("") || str.length()==0;
    }

    //Định dạng lại ngày tháng theo dd/mm/yyy trong datepicker
    public void ConverterFormDatepicker(DatePicker datePicker){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("vi"));
        // Set the formatter on the date picker
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                }else{
                    return "";
                }

            }
            @Override
            public LocalDate fromString(String date) {
                if (date != null && !date.isEmpty()) {
                    return LocalDate.parse(date, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    public void setDataGridPane(GridPane gridPane,AnchorPane anchorPane,int col,int row){
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefaulValue();
<<<<<<< Updated upstream

=======
        imgHome.setImage(new Image("C:\\Users\\84374\\Downloads\\icon-home.png"));
        imgLoading.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/loading.gif")));
        imgRefreshVoucher.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/icon-refresh.png")));
>>>>>>> Stashed changes
    }
}

class RanDom extends Task<String> {

    private static final SecureRandom random = new SecureRandom();
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private VoucherDao voucherDao = VoucherDao.getInstance(connectionPool);

    @Override
    protected String call() throws Exception {
        String result;
        do {
            byte[] bytes = new byte[6];
            random.nextBytes(bytes);
            result = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, 6).toUpperCase();
            result = result.replace("_", ""); // loại bỏ ký tự '_'
        } while (voucherDao.checkMaVoucher(result) > 1);
        return result;
    }

//        String resul = null;
//        long ressult = 0;
//        for(int i = 1;i<=10000;i++){
//            ressult+=i;
//            resul = uuid.toString().replaceAll("-", "").substring(0, 6); // Lấy 12 ký tự đầu tiên
//            updateValue(String.valueOf(ressult));
//
//        }
//        return resul;
    }

