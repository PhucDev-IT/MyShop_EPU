package com.epu.oop.myshop.controller;


import com.epu.oop.myshop.Dao.Bank_Dao;
import com.epu.oop.myshop.Dao.Product_Dao;
import com.epu.oop.myshop.Dao.UserDao;
import com.epu.oop.myshop.Dao.VoucherDao;
import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class PageHomeController implements Initializable {
    @FXML
    private ImageView imgLoading;

    //------------------------------- HEADER----------------------------------------------------------------------
    @FXML
    private Text myShop_logo;
    @FXML
    private TextField SearchProduct_txt;

    @FXML
    private Pane authForm_pane;

    @FXML
    private Label SignUp_lb;

    @FXML
    private Label SignIn_lb;

    @FXML
    private ImageView img_iconSearch;

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Pane paneUser;  //Chứa img user dẫn đến form thông tin

    //-------------------------- Navbar - MENU -------------------------------------------
    @FXML
    private JFXButton menu1_btn;

    @FXML
    private JFXButton menu2_btn;

    @FXML
    private JFXButton menu3_btn;

    @FXML
    private JFXButton menu4_btn;

    @FXML
    private JFXButton menu5_btn;

    @FXML
    private JFXButton menu6_btn;

    @FXML
    private JFXButton menu7_btn;

    @FXML
    private Pane main;

    @FXML
    private Pane item1_pane;

    @FXML
    private Label itemPhone_lb;

    @FXML
    private Label itemLaptop_lb;

    @FXML
    private Label itemMayChoiGame_lb;

    @FXML
    private Pane item2_pane;

    @FXML
    private Label itemChamSocDa_lb;

    @FXML
    private Label itemTrangDiem_lb;

    @FXML
    private Label itemNuocHoa_lb;

    @FXML
    private Label itemThucPhamChucNang_lb;

    @FXML
    private Label itemThietBiYTe_lb;

    @FXML
    private Pane item3_pane;

    @FXML
    private Label itemTrangPhucNam;

    @FXML
    private Label ItemGiayDepNam;

    @FXML
    private Label itemDongHoNam;

    @FXML
    private Label itemDoLotNam;

    @FXML
    private Pane item4_pane;

    @FXML
    private Label itemTrangPhucNu;

    @FXML
    private Label itemTuiXachNu;

    @FXML
    private Label itemNoiYNu;

    @FXML
    private Label itemTrangSucNu;

    @FXML
    private Pane item5_pane;

    @FXML
    private Label itemXeMayMoto;

    @FXML
    private Label itemXeOto;

    @FXML
    private Label itemXeDiaHinh;

    @FXML
    private Pane item6_pane;

    @FXML
    private Label itemPhuKienTheThao;

    @FXML
    private Label itemDoTheThao;

    @FXML
    private Label itemValiTuiXach;

    @FXML
    private Label itemKinhMat;

    @FXML
    private Label itemHoatDongDaNgoai;


    //-------------------------------------- MAIN ------------------------------------------------------------------
    @FXML
    private ImageView img_slider;

    @FXML
    private GridPane grid_Products;

    @FXML
    private AnchorPane paneProductMarket;
    //---------- Phân trang--------------

    @FXML
    private Label lbNoData;
    @FXML
    private Pagination pagination;

    //------------------------------------------ THÔNG TIN SẢN PHẨM --------------------------------------------------
    @FXML
    private ImageView imgProdInfor;

    @FXML
    private Text nameProdInfor_txt;

    @FXML
    private Label priceProdInfor;

    @FXML
    private Label sold;

    @FXML
    private Label remainInfor_lb;

    @FXML
    private Label nameSeller_Infor_lb;

    @FXML
    private JFXTextArea contentProInfor_txa;

    @FXML
    private JFXButton BtndownNumber;

    @FXML
    private JFXButton BtnUpNumber;

    @FXML
    private JFXTextField txtNumber;

    @FXML
    private Pane paneInformationProduct;

    @FXML
    private Label closeInfromation;

    @FXML
    private JFXButton btnBuyProduct;
    private MyListener<Product> productMyListener;

    int numbersBuyProduct = 1;

    //------------------------------ CHI TIẾT MUA HÀNG ---------------------------------------------------
    @FXML
    private AnchorPane paneOrderDetail;
    @FXML
    private ImageView imgProdOderDetail;

    @FXML
    private Text nameProdOrder_txt;

    @FXML
    private Label priceProOder_lb;

    @FXML
    private ImageView imgVoucherOrder;

    @FXML
    private Label VcSaleOrder_lb;

    @FXML
    private ImageView imgUserOrder;

    @FXML
    private Label nameUserOder_lb;

    @FXML
    private ImageView imgPhoneOrder;

    @FXML
    private Label phoneUserOder_lb;

    @FXML
    private ImageView imgAddressOrder;

    @FXML
    private JFXTextField AdressOrder_txt;

    @FXML
    private Label numbersProduct;

    @FXML
    private Label totalMoneyProd_lb;

    @FXML
    private Label VcShelfOrder_lb;

    @FXML
    private Label totalOrder_lb;

    @FXML
    private ImageView imgPayAtHome;

    @FXML
    private ImageView imgPayBank;

    @FXML
    private Pane paneVoucher;

    @FXML
    private GridPane gridVoucher;

    @FXML
    private JFXButton btnAddVoucher;

    @FXML
    private Label closeOrderDetail;

    @FXML
    private Pane payAtHome;

    @FXML
    private Pane payByBank;
    //----------------------------------------



    private final int maxProductsOfPage = 10; //1 trang tối đa 10 sản phẩm

    List<Product> listProductByPage = new ArrayList<>();
    int pageSelected ;

    private Product SelectedProduct;    //Lưu giá trị product vừa được click

    private VoucherModel voucherSelected = null;

    private List<VoucherModel> listvoucher;
    private Product_Dao product_Dao = Product_Dao.getInstance();
    private VoucherDao voucherDao = VoucherDao.getInstance();

    private UserDao userDao = UserDao.getInstance();

    private Bank_Dao bank_dao = Bank_Dao.getInstance();

    //-------------------------------------------- HIỂN THỊ CÁC SẢN PHẨM THUỘC DANH MUC ?------------


    //Tính số trang và lấy dữ liệu từ cachedRowSet để hiển thị lên GUI
    public void calculatePage() throws SQLException {
        int totalPages = 0;
        int totalProducts = 0;
        totalProducts = product_Dao.getNumbersPages(keyCategory);
        totalPages =  (int) Math.ceil((double) totalProducts / maxProductsOfPage);
        if(totalProducts<=0){
            lbNoData.setVisible(true);
        }else{
            lbNoData.setVisible(false);
        }
        pagination.setPageCount(totalPages>0?totalPages:1);

        pageSelected = 1;   //Ban đầu sẽ hiện danh sách ở page 1
        listProductByPage = product_Dao.getProductsByPage(keyCategory,pageSelected,maxProductsOfPage);
        selectDataProducts();

    }


    public void clickPage() {
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            pageSelected = newValue.intValue()+1;
            try {
                selectDataProducts();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        pagination.setPageFactory(pageIndex -> {
            try {
                selectDataProducts();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return grid_Products;
        });
    }

    public void selectDataProducts() throws SQLException {

        row = 1;
        col = 0;
        grid_Products.getChildren().clear();
        for (Product product : listProductByPage){
            setDataProduct(product);
        }

    }
    int row = 1;
    int col = 0;
    public void setDataProduct(Product prod)
    {
        paneProductMarket.setVisible(true);
        clickProduct();
        try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/ItemProductOfMarket.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ProductsOfMarketController item = fxmlLoader.getController();
                item.setData(productMyListener,prod);
                if(col == 5)
                {
                    col = 0;
                    row++;
                }
                grid_Products.add(anchorPane, col++, row); //(child,column,row)
                //set grid width
                grid_Products.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid_Products.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid_Products.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid_Products.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid_Products.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid_Products.setMaxHeight(Region.USE_PREF_SIZE);

            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void loadData()
    {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> imgLoading.setVisible(true));
                Thread.sleep(100); // giảm độ trễ cho phép cho JavaFX làm việc
                Platform.runLater(() -> {
                    if (!isCancelled()) { // Kiểm tra task có bị hủy hay không
                        try {
                            calculatePage();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                Thread.sleep(1000);
                Platform.runLater(() -> {
                    imgLoading.setVisible(false);
                });
                return null;
            }
        };


//        task.setOnRunning(e -> {
//            Platform.runLater(() -> imgLoading.setVisible(true));
//        });
//
//        task.setOnSucceeded(e -> {
//            Platform.runLater(() -> {
//                imgLoading.setVisible(false);
//                paneProductMarket.setVisible(true);
//            });
//        });

        new Thread(task).start();
    }

    //------------------------------------- HIỂN THỊ THÔNG TIN SẢN Phẩm VỪA CHỌN--------------------------------
    public void clickProduct()
    {
        productMyListener = new MyListener<Product>() {
            @Override
            public void onClickListener(Product product) {
                SelectedProduct = product;
                paneInformationProduct.setVisible(true);
                displayInformationProduct();
            }
        };
    }
    public void displayInformationProduct()
    {
        imgProdInfor.setImage(new Image(SelectedProduct.getSrcImg()));
        nameProdInfor_txt.setText(SelectedProduct.getTenSP());
        priceProdInfor.setText(App.numf.format(SelectedProduct.getPrice())+"đ");
        remainInfor_lb.setText(SelectedProduct.getQuantity()+"");
      //  nameSeller_Infor_lb.setText(SelectedProduct.getUser().getFullName());
        contentProInfor_txa.setText(SelectedProduct.getMoTa());
        //sold.setText();
    }

    public void upDownNumber(ActionEvent event)
    {
        if(event.getSource() == BtnUpNumber){
            numbersBuyProduct++;
        }else if(event.getSource() == BtndownNumber) {
            if(numbersBuyProduct>1){
                numbersBuyProduct--;
            }
        }
        txtNumber.setText(numbersBuyProduct+"");
    }

    //Kiểm tra người dùng nhập số lượng có chữ cái và xoóa
    public void removeCharInput(KeyEvent e)
    {
        txtNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*[^0-9].*")) {
                txtNumber.setText(oldValue);
            }
        });
        if(txtNumber.getText().length() >0 && txtNumber.getText().charAt(0)=='0')
        {
            txtNumber.setText(txtNumber.getText().replace("0",""));
            numbersBuyProduct = Integer.parseInt(txtNumber.getText());
        }
        System.out.println(txtNumber.getText());


    }

    //Nhấn cloes information
    @FXML
    public void closeForm(MouseEvent event)
    {
        if(event.getSource() == closeInfromation){
            paneInformationProduct.setVisible(false);
        }else if(event.getSource() == closeOrderDetail){
            paneOrderDetail.setVisible(false);
        }
    }

    public void BuyProduct(ActionEvent e) throws IOException {
        if(e.getSource() == btnBuyProduct){
            if(Temp.account!=null){

                if(SelectedProduct.getQuantity()<=0){
                    AlertNotification.showAlertWarning("","Sản phẩm này tạm hết!");
                }else if(Integer.parseInt(txtNumber.getText()) > SelectedProduct.getQuantity()){
                    AlertNotification.showAlertWarning("","Số lượng hàng không đủ");
                }else {
                    paneInformationProduct.setVisible(false);
                    paneOrderDetail.setVisible(true);
                    showOderDetail();
                }
            }else{
                if(AlertNotification.showAlertConfirmation("Bạn chưa đăng nhập","Đăng nhập để mua hàng?")){
                    ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/LoginForm.fxml");
                }
            }
        }
    }

    //---------------------------------- THÔNG TIN CHI TIẾT HÓA ĐƠN - MUA HÀNG -----------------------------------

    private BigDecimal tongTien ;   //Sau khi nhấn mua hàng thì sẽ tính tiền vì khi đó mới có dữ liệu của product
    private BigDecimal thanhTien;

    //Thanh toán - Mua Hàng
    @FXML
    public void PayProduct(MouseEvent e) throws SQLException {
        Order hoaDon = new Order(new Date(System.currentTimeMillis()),tongTien,voucherSelected,thanhTien,Temp.user);
        OrderDetails ctHoaDon = new OrderDetails(SelectedProduct,numbersBuyProduct,SelectedProduct.getPrice());


        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() ->imgLoading.setVisible(true));

                Thread.sleep(200);


                    if(e.getSource()==payAtHome){
                        PayProductAtHome(hoaDon,ctHoaDon);
                    }else if(e.getSource() == payByBank) {
                        payProductByBank(hoaDon, ctHoaDon);
                    }

                Thread.sleep(100);
                for (Product pro :listProductByPage){
                    if(pro.getID() == SelectedProduct.getID()){
                        pro.setQuantity(pro.getQuantity() - numbersBuyProduct);
                        pro.setSold(pro.getSold() + numbersBuyProduct);
                        break;
                    }
                }

                Platform.runLater(() -> {
                    try {
                        selectDataProducts();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                Platform.runLater(() -> imgLoading.setVisible(false));
                return null;
            }
        };

        new Thread(task).start();

    }
    public void PayProductAtHome(Order hoaDon, OrderDetails ctHoaDon){
            if(Temp.hoaDon_dao.OrderDetailsPayAtHome(hoaDon,ctHoaDon)){
                AlertNotification.showAlertSucces("Mua hàng thành công!","Cảm ơn bạn đã mua hàng");
                paneOrderDetail.setVisible(false);

            }else{
                AlertNotification.showAlertError("","Có lỗi xảy ra");
            }
    }

    public void payProductByBank(Order hoaDon, OrderDetails ctHoaDon)
    {
        Temp.bank = bank_dao.SelectByID(new Bank(Temp.user));
        if(Temp.bank!=null){
            if(Temp.account.getMoney().compareTo(thanhTien)<=0){
                AlertNotification.showAlertWarning("Số dư không đủ!","Vui lòng nạp thêm tiền...");
            }else{
                PaymentHistory paymentBank = new PaymentHistory("Mua Hàng","Thanh toán cho MyShop",thanhTien,
                        new Date(System.currentTimeMillis()),"/com/epu/oop/myshop/image/iconThanhToan.jpg",Temp.user,null);
                Temp.account.setMoney(Temp.account.getMoney().subtract(thanhTien));
                if(Temp.hoaDon_dao.OrderDetailsPayByBank(hoaDon,ctHoaDon,paymentBank,Temp.account)){
                    AlertNotification.showAlertSucces("Mua hàng thành công!","Cảm ơn bạn đã mua hàng");
                    paneOrderDetail.setVisible(false);
                }else{
                    AlertNotification.showAlertError("","Có lỗi xảy ra");
                }
            }
        }else {
            AlertNotification.showAlertWarning("","Liên kết ngân hàng trước khi thanh toán!");
        }
    }
    public void caculateOrderDetail()
    {
        tongTien = SelectedProduct.getPrice().multiply(BigDecimal.valueOf(Long.parseLong(txtNumber.getText())));

        if(voucherSelected!=null)
        {
            if(voucherSelected.getTiLeGiamGia()>0.0){
                VcSaleOrder_lb.setText(voucherSelected.getTiLeGiamGia()+"%");
                thanhTien = tongTien.subtract(tongTien.multiply(BigDecimal.valueOf(voucherSelected.getTiLeGiamGia()).divide(BigDecimal.valueOf(100))));
            }else if(voucherSelected.getSoTienGiam()!=null || voucherSelected.getSoTienGiam().compareTo(BigDecimal.ZERO)>0){
                VcSaleOrder_lb.setText(App.numf.format(voucherSelected.getSoTienGiam())+"đ");
                thanhTien = tongTien.subtract(voucherSelected.getSoTienGiam());
                if(thanhTien.compareTo(BigDecimal.ZERO)<0)  //Tránh trường hợp giảm giá âm tiền
                    thanhTien = new BigDecimal("0");
            }
        }else{
            thanhTien = tongTien;
            VcSaleOrder_lb.setText("");
        }

        VcShelfOrder_lb.setText(VcSaleOrder_lb.getText());
        totalOrder_lb.setText(App.numf.format(thanhTien)+"đ");
        totalMoneyProd_lb.setText(App.numf.format(tongTien)+"đ");
    }
    public void showOderDetail()
    {
        System.out.println(numbersBuyProduct);
        //Lấy thông tin người dungf
        Temp.user = userDao.SelectByID(new User(Temp.account.getID()));
        nameUserOder_lb.setText(Temp.user.getFullName());
        phoneUserOder_lb.setText(Temp.user.getNumberPhone());
        AdressOrder_txt.setText(Temp.user.getAddress());

        imgProdOderDetail.setImage(new Image(SelectedProduct.getSrcImg()));
        nameProdOrder_txt.setText(SelectedProduct.getTenSP());
        priceProOder_lb.setText(App.numf.format(SelectedProduct.getPrice())+"đ");
        numbersProduct.setText(txtNumber.getText());

        //Tổng tiền hàng
        caculateOrderDetail();

    }

    private MyListener<VoucherModel> myListener_Voucher;
    @FXML
    public void addVoucher(ActionEvent e){
        if(btnAddVoucher.getText().equals("+")){
            paneVoucher.setVisible(true);
            setDataVoucher();
        }else{
            voucherSelected = null;
            btnAddVoucher.setText("+");
            caculateOrderDetail();
        }
    }

    public void clickVoucher()
    {
        myListener_Voucher = new MyListener<VoucherModel>() {
            @Override
            public void onClickListener(VoucherModel voucherModel) {
                voucherSelected = voucherModel;
                paneVoucher.setVisible(false);
                caculateOrderDetail();
                btnAddVoucher.setText("-");
            }
        };
    }
    public void setDataVoucher()
    {
        if(listvoucher==null)
        {
            listvoucher = voucherDao.getVoucherConTime(Temp.user.getID());
        }
        gridVoucher.getChildren().clear();
        clickVoucher();
        for(VoucherModel vc : listvoucher)
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/Voucher.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                VoucherController itemCategory = fxmlLoader.getController();
                itemCategory.setData(myListener_Voucher,vc);

                gridVoucher.add(anchorPane, col, ++row); // (child,column,row)
                // set grid width
                gridVoucher.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridVoucher.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridVoucher.setMaxWidth(Region.USE_PREF_SIZE);

                // set grid height
                gridVoucher.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridVoucher.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridVoucher.setMaxHeight(Region.USE_PREF_SIZE);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    //-------------------------------------------------------------------------------

    //Khi hover vào các nav-item
    @FXML
    public void HoverMenuNavbar(MouseEvent e){
        item1_pane.setVisible(false);
        item2_pane.setVisible(false);
        item3_pane.setVisible(false);
        item4_pane.setVisible(false);
        item5_pane.setVisible(false);
        item6_pane.setVisible(false);
        
        if(e.getSource() == menu1_btn){
            item1_pane.setVisible(true);
        }else if(e.getSource() == menu2_btn){
            item2_pane.setVisible(true);
        }else if(e.getSource() == menu3_btn){
            item3_pane.setVisible(true);
        }else if(e.getSource() == menu4_btn){
            item4_pane.setVisible(true);
        }else if(e.getSource() == menu5_btn){
            item5_pane.setVisible(true);
        }else if(e.getSource() == menu6_btn){
            item6_pane.setVisible(true);
        }
    }
    //Khi di chuột ra màn hình chính hoặc các chỗ khác sẽ bị mất list item (làm này cho dễ)
    public void hiddenListItemOfMenu(MouseEvent e){
        item1_pane.setVisible(false);
        item2_pane.setVisible(false);
        item3_pane.setVisible(false);
        item4_pane.setVisible(false);
        item5_pane.setVisible(false);
        item6_pane.setVisible(false);
    }

    public void displayBackgroundItemNav(MouseEvent event){
        menu1_btn.setStyle("-fx-background-color: #fff");
        menu2_btn.setStyle("-fx-background-color: #fff");
        menu3_btn.setStyle("-fx-background-color: #fff");
        menu4_btn.setStyle("-fx-background-color: #fff");
        menu5_btn.setStyle("-fx-background-color: #fff");
        menu6_btn.setStyle("-fx-background-color: #fff");
        menu7_btn.setStyle("-fx-background-color: #fff");
        if(event.getSource() == item1_pane){
            menu1_btn.setStyle("-fx-background-color: #e3fafc");
        }else if(event.getSource() == item2_pane){
            menu2_btn.setStyle("-fx-background-color: #e3fafc");
        }else if(event.getSource() == item3_pane){
            menu3_btn.setStyle("-fx-background-color: #e3fafc");
        }else if(event.getSource() == item4_pane){
            menu4_btn.setStyle("-fx-background-color: #e3fafc");
        }else if(event.getSource() == item5_pane){
            menu5_btn.setStyle("-fx-background-color: #e3fafc");
        }else if(event.getSource() == item6_pane){
            menu6_btn.setStyle("-fx-background-color: #e3fafc");
        }else if(event.getSource() == menu7_btn){
            menu7_btn.setStyle("-fx-background-color: #e3fafc");
        }
    }

    @FXML
    public void EventAuthForm(MouseEvent e) throws IOException {
        if(e.getSource() == SignIn_lb){
            ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/LoginForm.fxml");
        }else if(e.getSource() == SignUp_lb)
        {
            ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/RegisterForm.fxml");
        }
    }
    private Category category = new Category();
    private int keyCategory=-1;
    /* OldKey..
   -Mục đích để kiểm tra nếu truy vấn danh mục khác nếu gặp giá trị rỗng thì k bị loi pointer
   -Do set lên GUI nên  cachedRow đang ở vị trí dòng bất kỳ, nếu: cachedProducts.beforeFirst(); dẫn đến các trang đều bị set giống nhau
   -Không có thì khi chọn danh mục khác bị null thì lỗi con trỏ
    */
    private int oldKeyCategory = -1;


    public void clickCategory(MouseEvent e) throws SQLException {
        if(e.getSource() == itemPhone_lb)
        {
            keyCategory = category.listCategory.get(itemPhone_lb.getText());
        }else if(e.getSource() == itemLaptop_lb)
        {
            keyCategory = category.listCategory.get(itemLaptop_lb.getText());
        }else if(e.getSource() == itemMayChoiGame_lb)
        {
            keyCategory = category.listCategory.get(itemMayChoiGame_lb.getText());
        }else if(e.getSource() == itemChamSocDa_lb)
        {
            keyCategory = category.listCategory.get(itemChamSocDa_lb.getText());
        }else if(e.getSource() == itemTrangDiem_lb)
        {
            keyCategory = category.listCategory.get(itemTrangDiem_lb.getText());
        }else if(e.getSource() == itemNuocHoa_lb)
        {
            keyCategory = category.listCategory.get(itemNuocHoa_lb.getText());
        }else if(e.getSource() == itemThucPhamChucNang_lb)
        {
            keyCategory = category.listCategory.get(itemThucPhamChucNang_lb.getText());
        }else if(e.getSource() == itemThietBiYTe_lb)
        {
            keyCategory = category.listCategory.get(itemThietBiYTe_lb.getText());
        }else if(e.getSource() == itemTrangPhucNam)
        {
            keyCategory = category.listCategory.get(itemTrangPhucNam.getText());
        }else if(e.getSource() == ItemGiayDepNam)
        {
            keyCategory = category.listCategory.get(ItemGiayDepNam.getText());
        }else if(e.getSource() == itemDongHoNam)
        {
            keyCategory = category.listCategory.get(itemDongHoNam.getText());
        }else if(e.getSource() == itemDoLotNam)
        {
            keyCategory = category.listCategory.get(itemDoLotNam.getText());
        }else if(e.getSource() == itemTrangPhucNu)
        {
            keyCategory = category.listCategory.get(itemTrangPhucNu.getText());
        }else if(e.getSource() == itemTuiXachNu)
        {
            keyCategory = category.listCategory.get(itemTuiXachNu.getText());
        }else if(e.getSource() == itemNoiYNu)
        {
            keyCategory = category.listCategory.get(itemNoiYNu.getText());
        }else if(e.getSource() == itemTrangSucNu)
        {
            keyCategory = category.listCategory.get(itemTrangSucNu.getText());
        }else if(e.getSource() == itemXeMayMoto)
        {
            keyCategory = category.listCategory.get(itemXeMayMoto.getText());
        }else if(e.getSource() == itemXeOto)
        {
            keyCategory = category.listCategory.get(itemXeOto.getText());
        }else if(e.getSource() == itemXeDiaHinh)
        {
            keyCategory = category.listCategory.get(itemXeDiaHinh.getText());
        }else if(e.getSource() == itemPhuKienTheThao)
        {
            keyCategory = category.listCategory.get(itemPhuKienTheThao.getText());
        }else if(e.getSource() == itemDoTheThao)
        {
            keyCategory = category.listCategory.get(itemDoTheThao.getText());
        }else if(e.getSource() == itemValiTuiXach)
        {
            keyCategory = category.listCategory.get(itemValiTuiXach.getText());
        }else if(e.getSource() == itemKinhMat)
        {
            keyCategory = category.listCategory.get(itemKinhMat.getText());
        }else if(e.getSource() == itemHoatDongDaNgoai)
        {
            keyCategory = category.listCategory.get(itemHoatDongDaNgoai.getText());
        }else if(e.getSource() == menu7_btn)
        {
            keyCategory = category.listCategory.get(menu7_btn.getText());
        }
        if(keyCategory!=-1)
        {
           // paneProductMarket.setVisible(true);
            //calculatePage();
            loadData();

        }

    }
    private void showImage(){
        img_iconSearch.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconSearch.png"));
        imgAvatar.setImage((new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/img1.jpg"))));

        imgLoading.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/loading.gif")));
        imgVoucherOrder.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/voucher.png")));
        imgUserOrder.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/icon_user.png"))));
        imgPhoneOrder.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconPhone.png"));
        imgAddressOrder.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconAddress.png"));
        imgPayAtHome.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconFreeShip.jpg"));
        imgPayBank.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconThanhToan.jpg"))));
    }


    public void defaultForm()
    {
        if(Temp.account == null){
            authForm_pane.setVisible(true);
            paneUser.setVisible(false);
        }else{
            authForm_pane.setVisible(false);
            paneUser.setVisible(true);
        }
    }

    public void click(MouseEvent e) throws IOException {
        if(e.getSource() == imgAvatar)
        {
            ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/ProfileUser.fxml");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultForm();
        clickPage();
        showImage();

    ThreadImageV threadImageV;

        Rectangle clip = new Rectangle(
                img_slider.getFitWidth(), img_slider.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        img_slider.setClip(clip);

        // snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = img_slider.snapshot(parameters, null);

        // remove the rounding clip so that our effect can show through.
        img_slider.setClip(null);

        // apply a shadow effect.
        img_slider.setEffect(new DropShadow(20, Color.BLACK));

        // store the rounded image in the imageView.
        img_slider.setImage(image);
        threadImageV =  new ThreadImageV(img_slider);
        threadImageV.start();
    }
}
