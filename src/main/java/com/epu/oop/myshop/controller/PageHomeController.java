package com.epu.oop.myshop.controller;


import com.epu.oop.myshop.Dao.*;
import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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

    @FXML
    private ImageView img_shopping_cart;

    @FXML
    private ImageView img_Messenger;

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
    private ScrollPane scrollProductSearch;
    @FXML
    private AnchorPane paneLoading;
    @FXML
    private ImageView img_slider;

    @FXML
    private GridPane grid_Products;

    @FXML
    private AnchorPane paneProductMarket;

    @FXML
    private GridPane gridProductSearch;

    @FXML
    private AnchorPane paneListProductSearch;
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
    private Label soldInfror_lb;

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
    private JFXButton btnAddInCart;
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
    //----------------------------------------GIỎ HÀNG---------------------------------
    @FXML
    private AnchorPane paneShoppingCart;
    @FXML
    private GridPane gridItemCart;

    @FXML
    private Label numbersItemCart_lb;

    @FXML
    private Label sumMoney_itemCartLb;

    @FXML
    private JFXButton btnMuaItemCart;

    //-----------------------------------------


    private final int maxProductsOfPage = 10; //1 trang tối đa 10 sản phẩm

    List<Product> listProduct = new ArrayList<>();
    int pageSelected;

    private Product SelectedProduct;    //Lưu giá trị product vừa được click

    private VoucherModel voucherSelected = null;

    private List<VoucherModel> listvoucher = new ArrayList<>();

    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Product_Dao product_Dao = Product_Dao.getInstance(connectionPool);
    private VoucherDao voucherDao = VoucherDao.getInstance(connectionPool);

    private Order_Dao order_dao = Order_Dao.getInstance(connectionPool);
    private UserDao userDao = UserDao.getInstance(connectionPool);

    private Bank_Dao bank_dao = Bank_Dao.getInstance(connectionPool);

    private itemCartDao itemCart_dao = itemCartDao.getInstance(connectionPool);

    //---------------------- KIỂM TRA RỖNG ----------------------------------
    public boolean checkStringIsempty(String str) {
        return str == null || str.length() == 0 || str == "";
    }


    //-------------------------------------------- HIỂN THỊ CÁC SẢN PHẨM THUỘC DANH MUC ?------------

    /*
    - Mỗi lần chon 1 danh mục bất kỳ sẽ tính tổng số product thuộc danh mục  đó  để tiến hành phân trang và set số lượng cho pagi..
    -B2: Truy vấn dữ liệu thuộc category vừa nhấn băt đầu với trang thứ nhất
    -Mỗi khi nhấn sang 1 page mới sẽ clear data cũ và truy vấn sản phẩm thuộc trang tiếp theo, các  sản phẩm sẽ được set
    lên UI của PageFactory của pagination.
     */

    //Tính số trang và lấy dữ liệu từ cachedRowSet để hiển thị lên GUI
    int totalPages = 0;
    public void calculatePage() throws SQLException {

        int totalProducts = 0;

        totalProducts = product_Dao.getNumbersPages(keyCategory);

        totalPages = (int) Math.ceil((double) totalProducts / maxProductsOfPage);
        listProduct.clear();
        if (totalProducts <= 0) {
            lbNoData.setVisible(true);
        } else {
            lbNoData.setVisible(false);

            pageSelected = 1;   //Ban đầu sẽ hiện danh sách ở page 1

            listProduct.addAll(product_Dao.getProductsByPage(keyCategory, pageSelected, maxProductsOfPage));
        }
        paneProductMarket.setVisible(true);
        paneListProductSearch.setVisible(false);

    }


    public void clickPage() {
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            pageSelected = newValue.intValue() + 1;
            listProduct.clear();
            listProduct.addAll(product_Dao.getProductsByPage(keyCategory, pageSelected, maxProductsOfPage));
        });
        // nó sẽ được gọi mỗi khi người dùng chọn một trang mới trong Pagination
        //nếu bạn không thiết lập setPageFactory(), Pagination sẽ sử dụng mặc định một PageFactory để tạo các trang của nó.
        // Các trang được tạo ra bởi PageFactory mặc định sẽ chỉ là các Label hiển thị số trang tương ứng
        //=>Dùng tạo dộ mượt chuyển trang
        pagination.setPageFactory(pageIndex -> {
            try {
                loadDataProducts();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return grid_Products;
        });
    }

    /*
    -Để tiết kiệm bộ nhớ, giải phóng các node trên grid khi không còn dùng
    -Cần set lại matrix cho grid để không bị  trùng lên nhau v clear grid product để hiện phân trang
     */
    public void loadDataProducts() throws SQLException {

        numbersBuyProduct = 1;
        txtNumber.setText(numbersBuyProduct+"");
        paneOrderDetail.setVisible(false);
        row = 1;
        col = 0;

        grid_Products.getChildren().clear();
        gridProductSearch.getChildren().clear();

            for (Product product : listProduct) {
                setDataProduct(product, grid_Products);
            }
    }

    int row = 1;
    int col = 0;

    public void setDataProduct(Product prod, GridPane gridPane) {
        clickProduct();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/ItemProductOfMarket.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            ProductsOfMarketController item = fxmlLoader.getController();
            item.setData(productMyListener, prod);
            if (col == 5) {
                col = 0;
                row++;
            }
            gridPane.add(anchorPane, col++, row); //(child,column,row)
            //set grid width
            gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
            gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
            gridPane.setMaxWidth(Region.USE_PREF_SIZE);

            //set grid height
            gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
            gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
            gridPane.setMaxHeight(Region.USE_PREF_SIZE);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void loadData(Event e) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> paneLoading.setVisible(true));
                Thread.sleep(100); // giảm độ trễ cho phép cho JavaFX làm việc
                if (isCancelled()) { // Kiểm tra task có bị hủy hay không
                    return null;
                }
                    try {
                        if (e instanceof MouseEvent) {
                            calculatePage();
                        } else if (e.getSource() ==SearchProduct_txt || e.getSource() ==scrollProductSearch) {
                            listProduct.addAll(product_Dao.SearchProducts(SearchProduct_txt.getText(), lastIndex, maxProductSearch));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();

        task.setOnSucceeded(event -> {
            if (e.getSource() ==SearchProduct_txt || e.getSource() == scrollProductSearch) {
                loadDataProductSearch();
            }else if(e instanceof MouseEvent){

                try {
                    pagination.setPageCount(totalPages > 0 ? totalPages : 1);
                    loadDataProducts();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            Platform.runLater(() -> {
                paneLoading.setVisible(false);
            });
            thread.interrupt();
        });
    }

    //------------------------------------- HIỂN THỊ THÔNG TIN SẢN Phẩm VỪA CHỌN--------------------------------
    public void clickProduct() {
        productMyListener = new MyListener<Product>() {
            @Override
            public void onClickListener(Product product) {
                SelectedProduct = product;
                paneInformationProduct.setVisible(true);
                displayInformationProduct();
            }
        };
    }



    //Kiểm tra ảnh nằm trong project hay nằm ngoài project
    public void displayInformationProduct() {
        try {
            if(!SelectedProduct.getSrcImg().contains(":")){
                imgProdInfor.setImage(new Image(getClass().getResourceAsStream(SelectedProduct.getSrcImg())));
            }else
                imgProdInfor.setImage(new Image(SelectedProduct.getSrcImg()));
        }catch (Exception e){
            imgProdInfor.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png")));
        }
        nameProdInfor_txt.setText(SelectedProduct.getTenSP());
        priceProdInfor.setText(App.numf.format(SelectedProduct.getPrice()) + "đ");
        remainInfor_lb.setText(SelectedProduct.getQuantity() + "");
        nameSeller_Infor_lb.setText(SelectedProduct.getUser().getFullName());
        contentProInfor_txa.setText(SelectedProduct.getMoTa());
        soldInfror_lb.setText(SelectedProduct.getSold() + "");
    }

    public void upDownNumber(ActionEvent event) {
        if (event.getSource() == BtnUpNumber) {
            numbersBuyProduct++;
        } else if (event.getSource() == BtndownNumber) {
            if (numbersBuyProduct > 1) {
                numbersBuyProduct--;
            }
        }
        txtNumber.setText(numbersBuyProduct + "");
    }

    //Kiểm tra người dùng nhập số lượng có chữ cái và xoóa
    public void removeCharInput(KeyEvent e) {
        txtNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*[^0-9].*")) {
                txtNumber.setText(oldValue);
            }
        });
        if (!checkStringIsempty(txtNumber.getText())) {
            if (txtNumber.getText().charAt(0) == '0') {
                txtNumber.setText(txtNumber.getText().replace("0", ""));
            }
            numbersBuyProduct = Integer.parseInt(txtNumber.getText());
        }
    }

    //Nhấn cloes information
    @FXML
    public void closeForm(MouseEvent event) {
        if (event.getSource() == closeInfromation) {
            paneInformationProduct.setVisible(false);
        } else if (event.getSource() == closeOrderDetail) {
            paneOrderDetail.setVisible(false);
        }
    }

    public void BuyProduct(ActionEvent e) throws IOException {
        if (e.getSource() == btnBuyProduct) {
            if (Temp.account != null) {

                if (SelectedProduct.getQuantity() <= 0) {
                    AlertNotification.showAlertWarning("", "Sản phẩm này tạm hết!");
                } else if (Integer.parseInt(txtNumber.getText()) > SelectedProduct.getQuantity()) {
                    AlertNotification.showAlertWarning("", "Số lượng hàng không đủ");
                } else {
                    paneInformationProduct.setVisible(false);
                    paneOrderDetail.setVisible(true);
                    showOderDetail();
                }
            } else {
                if (AlertNotification.showAlertConfirmation("Bạn chưa đăng nhập", "Đăng nhập để mua hàng?")) {
                    ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/LoginForm.fxml","Đăng nhập");
                }
            }
        }
    }

    //Thêm vào gior hàng
    public void addInCart(ActionEvent e) throws SQLException {
        addItemCart();
    }

    //---------------------------------- THÔNG TIN CHI TIẾT HÓA ĐƠN - MUA HÀNG -----------------------------------

    private BigDecimal tongTien;   //Sau khi nhấn mua hàng thì sẽ tính tiền vì khi đó mới có dữ liệu của product
    private BigDecimal thanhTien;
    public boolean checkAddOrder;

    //Thanh toán - Mua Hàng
    @FXML
    public void PayProduct(MouseEvent e) {

        if (checkStringIsempty(phoneUserOder_lb.getText()) || checkStringIsempty(AdressOrder_txt.getText())) {
            AlertNotification.showAlertWarning("Thiếu thông tin!", "Nhập đầy đủ thông tin nhận hàng hoặc cập nhật lại hồ sơ");
        } else {
            Order hoaDon = new Order(new Date(System.currentTimeMillis()), tongTien, voucherSelected, thanhTien, Temp.user);
            OrderDetails ctHoaDon = new OrderDetails(SelectedProduct, numbersBuyProduct, SelectedProduct.getPrice());
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Platform.runLater(() -> paneLoading.setVisible(true));
                    Thread.sleep(200);
                        try{
                            if(e.getSource() == payAtHome){
                                checkAddOrder=PayProductAtHome(hoaDon, ctHoaDon);
                            }else if(e.getSource() == payByBank){
                                checkAddOrder=payProductByBank(hoaDon, ctHoaDon);
                            }
                        }catch (SQLException e){
                            throw new RuntimeException(e);
                        }
                    return null;
                }
            };

            Thread thread = new Thread(task);
            thread.start();

            task.setOnSucceeded(event -> {

                if (checkAddOrder) {
                    for (Product pro : listProduct) {
                        if (pro.getID() == SelectedProduct.getID()) {
                            pro.setQuantity(pro.getQuantity() - numbersBuyProduct);
                            pro.setSold(pro.getSold() + numbersBuyProduct);
                            break;
                        }
                    }
                    Platform.runLater(() -> {
                        AlertNotification.showAlertSucces("Mua hàng thành công!", "Cảm ơn bạn đã mua hàng");
                        try {
                            loadDataProducts();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        resetDisplayDataVoucher();
                    });
                }

                Platform.runLater(() -> paneLoading.setVisible(false));
                thread.interrupt();
            });
        }

    }

    public void resetDisplayDataVoucher()
    {
        btnAddVoucher.setText("+");
        VcSaleOrder_lb.setText("");
    }
    public boolean PayProductAtHome(Order hoaDon, OrderDetails ctHoaDon) throws SQLException {
        if (checkAddOrder = order_dao.OrderDetailsPayAtHome(hoaDon, ctHoaDon)) {
            return true;
        }else{
            Platform.runLater(() -> AlertNotification.showAlertError("", "Có lỗi xảy ra"));
        }
        return false;
    }

    public boolean payProductByBank(Order hoaDon, OrderDetails ctHoaDon) throws SQLException {
        Temp.bank = bank_dao.SelectByID(new Bank(Temp.user));
        if (Temp.bank != null) {
            if (Temp.account.getMoney().compareTo(thanhTien) <= 0) {
                Platform.runLater(() -> AlertNotification.showAlertWarning("Số dư không đủ!", "Vui lòng nạp thêm tiền..."));
            } else {
                PaymentHistory paymentBank = new PaymentHistory("Mua Hàng", "Thanh toán cho MyShop", thanhTien,
                        new Date(System.currentTimeMillis()), "/com/epu/oop/myshop/image/iconThanhToan.jpg", Temp.user, null);
                Temp.account.setMoney(Temp.account.getMoney().subtract(thanhTien));
                if (checkAddOrder = order_dao.OrderDetailsPayByBank(hoaDon, ctHoaDon, paymentBank)) {
                    listvoucher.remove(voucherSelected);
                    return true;
                }else{
                    Platform.runLater(() -> AlertNotification.showAlertError("", "Có lỗi xảy ra"));
                }
            }
        } else {
            Platform.runLater(() -> AlertNotification.showAlertWarning("", "Liên kết ngân hàng trước khi thanh toán!"));
        }
        return false;
    }

    public void caculateOrderDetail() {
        tongTien = SelectedProduct.getPrice().multiply(BigDecimal.valueOf(Long.parseLong(txtNumber.getText())));

        if (voucherSelected != null) {
            if (voucherSelected.getTiLeGiamGia() > 0.0) {
                VcSaleOrder_lb.setText(voucherSelected.getTiLeGiamGia() + "%");
                thanhTien = tongTien.subtract(tongTien.multiply(BigDecimal.valueOf(voucherSelected.getTiLeGiamGia()).divide(BigDecimal.valueOf(100))));
            } else if (voucherSelected.getSoTienGiam() != null || voucherSelected.getSoTienGiam().compareTo(BigDecimal.ZERO) > 0) {
                VcSaleOrder_lb.setText(App.numf.format(voucherSelected.getSoTienGiam()) + "đ");
                thanhTien = tongTien.subtract(voucherSelected.getSoTienGiam());
                if (thanhTien.compareTo(BigDecimal.ZERO) < 0)  //Tránh trường hợp giảm giá âm tiền
                    thanhTien = new BigDecimal("0");
            }
        } else {
            thanhTien = tongTien;
            VcSaleOrder_lb.setText("");
        }

        VcShelfOrder_lb.setText(VcSaleOrder_lb.getText());
        totalOrder_lb.setText(App.numf.format(thanhTien) + "đ");
        totalMoneyProd_lb.setText(App.numf.format(tongTien) + "đ");
    }

    public void showOderDetail() {
        numbersBuyProduct = Integer.parseInt(txtNumber.getText());
        //Lấy thông tin người dungf
        Temp.user = userDao.SelectByID(new User(Temp.account.getID()));
        nameUserOder_lb.setText(Temp.user.getFullName());
        phoneUserOder_lb.setText(Temp.user.getNumberPhone());
        AdressOrder_txt.setText(Temp.user.getAddress());

        try {
            if(!SelectedProduct.getSrcImg().contains(":")){
                imgProdOderDetail.setImage(new Image(getClass().getResourceAsStream(SelectedProduct.getSrcImg())));
            }else
                imgProdOderDetail.setImage(new Image(SelectedProduct.getSrcImg()));
        }catch (Exception e){
            imgProdOderDetail.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png")));
        }

        nameProdOrder_txt.setText(SelectedProduct.getTenSP());
        priceProOder_lb.setText(App.numf.format(SelectedProduct.getPrice()) + "đ");
        numbersProduct.setText(txtNumber.getText());

        //Tổng tiền hàng
        caculateOrderDetail();

    }

    private MyListener<VoucherModel> myListener_Voucher;

    @FXML
    public void addVoucher(ActionEvent e) {
        if (btnAddVoucher.getText().equals("+")) {
            paneVoucher.setVisible(true);
            setDataVoucher();
        } else {
            voucherSelected = null;
            btnAddVoucher.setText("+");
            caculateOrderDetail();
        }
    }

    public void clickVoucher() {
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

    public void setDataVoucher() {
        if (listvoucher.size()==0) {
            listvoucher = voucherDao.getVoucherConTime(Temp.user.getID());
            System.out.println(listvoucher);
        }
        gridVoucher.getChildren().clear();
        clickVoucher();
        for (VoucherModel vc : listvoucher)
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/Voucher.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                VoucherController itemCategory = fxmlLoader.getController();
                itemCategory.setData(myListener_Voucher, vc);

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

    public void hiddenFormVoucher(MouseEvent e)
    {
        paneVoucher.setVisible(false);
    }

    //--------------------------------------TÌM KIẾM SẢN PHẨM-----------------------------------------
    private AtomicInteger lastIndex = new AtomicInteger(0);
    private final int maxProductSearch = 15;

    public void loadDataProductSearch() {
        paneListProductSearch.setVisible(true);
        if(listProduct.size()<=0){

            lbNoData.setVisible(true);
        }else{
            lbNoData.setVisible(false);
        }
        int index = lastIndex.get();
        while(index<listProduct.size()){
            setDataProduct(listProduct.get(index), gridProductSearch);
            index++;
        }
        lastIndex.set(lastIndex.get() + (listProduct.size()-lastIndex.get()) ); // thay đổi giá trị của biến lastIndex

    }

    public synchronized void getNameProductSearch(ActionEvent e) {
        if (!checkStringIsempty(SearchProduct_txt.getText())) {
            resetBackgroundMenu();
            row = 1;
            col = 0;
            grid_Products.getChildren().clear();
            lastIndex.set(0);   //Vì sau khi nhấn tìm lại thì vị trí bắt đầu đang bị nhảy lên cao r
            listProduct.clear();    //Giai phong bộ nhớ  các node khng dùng đến
            gridProductSearch.getChildren().clear();
            hideForm();
            loadData(e);
        }
    }


    public void checkScroll(ScrollEvent e) {
        loadData(e);

    }
    //--------------------------------------- GIỎ HÀNG -----------------------------------------------
    int rowItemCart = 1;
    private List<itemCart> itemCartList = new ArrayList<>();

    private MyListener<itemCart> myListenerItemCart;

    public long sumNumbers = 0;

    public BigDecimal sumMoneyItem ;

    public  List<itemCart> listChooseItemCart = new ArrayList<>();
    /*
    -Khi người dùng tích chọn product, nếu lần đầu tích thì sẽ đc thêm vào list và tính tiền
    -Khi hủy thì sẽ bị xóa khoit list và cập nhật lại tiền
    -Nếu có hành động tăng giảm số lượng thì duyệt vòng lặp để kiểm tra và cập nhật lại sô lượng + tính toán tiền
    -Tông số lượng trước đó = tổng số lượng trước đó + (số lượng mới tăng - số luwognj trc đó trong list) nếu hành động tăng
    -Tổng tiền =
     */
    public void chooseItemCart()
    {
        myListenerItemCart = new MyListener<itemCart>() {
            @Override
            public void onClickListener(itemCart item) {

                if(item.isChoose()){
                    listChooseItemCart.add(item);
                    sumNumbers+= item.getQuantity();
                    sumMoneyItem = new BigDecimal(String.valueOf(item.getProduct().getPrice().multiply(BigDecimal.valueOf(sumNumbers))));
                    calculateSumItem();

                }else if(!item.isChoose()) {
                    listChooseItemCart.remove(item);
                    sumNumbers -= item.getQuantity();
                    sumMoneyItem = sumMoneyItem.subtract(item.getProduct().getPrice());
                    numbersItemCart_lb.setText(sumNumbers+"");
                    sumMoney_itemCartLb.setText(App.numf.format(sumMoneyItem)+"đ");
                }
//                numbersItemCart_lb.setText(sumNumbers+"");
//                sumMoney_itemCartLb.setText(App.numf.format(sumMoneyItem)+"đ");
            }
        };
    }

    public void calculateSumItem()
    {
//        sumNumbers+= item.getQuantity();
//        sumMoneyItem = new BigDecimal(String.valueOf(item.getProduct().getPrice().multiply(BigDecimal.valueOf(sumNumbers))));
        numbersItemCart_lb.setText(sumNumbers+"");
        sumMoney_itemCartLb.setText(App.numf.format(sumMoneyItem)+"đ");

    }
    public void loadDataItemCart() throws SQLException {
        itemCartList.clear();
        paneShoppingCart.setVisible(true);
        itemCartList.addAll(itemCart_dao.getDataByUser(Temp.account.getID()));
        for(itemCart it : itemCartList){
            setDataBasket(it);
        }

    }
    public void setDataBasket(itemCart it)
    {
        chooseItemCart();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/itemCartForm.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            ItemCartController itemCategory = fxmlLoader.getController();
            itemCategory.setData(myListenerItemCart,it);

            gridItemCart.add(anchorPane, 0, ++rowItemCart); // (child,column,row)
            // set grid width
            gridItemCart.setMinWidth(Region.USE_COMPUTED_SIZE);
            gridItemCart.setPrefWidth(Region.USE_COMPUTED_SIZE);
            gridItemCart.setMaxWidth(Region.USE_PREF_SIZE);

            // set grid height
            gridItemCart.setMinHeight(Region.USE_COMPUTED_SIZE);
            gridItemCart.setPrefHeight(Region.USE_COMPUTED_SIZE);
            gridItemCart.setMaxHeight(Region.USE_PREF_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Thêm vào giỏ hàng
    public void addItemCart() throws SQLException {
       if(Temp.account!=null){
           itemCart itemCart = new itemCart(0,SelectedProduct,Temp.account.getID(),keyCategory,Integer.parseInt(txtNumber.getText()));
           if(itemCart_dao.Insert(itemCart)){
               AlertNotification.showAlertSucces("","Kiểm tra giỏ hàng để xem nhé bạn!");
           }else{
               AlertNotification.showAlertError("","Có lỗi xảy ra");
           }
       }else {
           AlertNotification.showAlertWarning("Bạn chưa đăng nhập","Đăng nhập trước khi thao tác");
       }
    }

    //Khi hover vào các nav-item
    @FXML
    public void HoverMenuNavbar(MouseEvent e) {
        item1_pane.setVisible(false);
        item2_pane.setVisible(false);
        item3_pane.setVisible(false);
        item4_pane.setVisible(false);
        item5_pane.setVisible(false);
        item6_pane.setVisible(false);

        if (e.getSource() == menu1_btn) {
            item1_pane.setVisible(true);
        } else if (e.getSource() == menu2_btn) {
            item2_pane.setVisible(true);
        } else if (e.getSource() == menu3_btn) {
            item3_pane.setVisible(true);
        } else if (e.getSource() == menu4_btn) {
            item4_pane.setVisible(true);
        } else if (e.getSource() == menu5_btn) {
            item5_pane.setVisible(true);
        } else if (e.getSource() == menu6_btn) {
            item6_pane.setVisible(true);
        }
    }

    //Khi di chuột ra màn hình chính hoặc các chỗ khác sẽ bị mất list item (làm này cho dễ)
    public void hiddenListItemOfMenu(MouseEvent e) {
        item1_pane.setVisible(false);
        item2_pane.setVisible(false);
        item3_pane.setVisible(false);
        item4_pane.setVisible(false);
        item5_pane.setVisible(false);
        item6_pane.setVisible(false);
    }

    public void resetBackgroundMenu(){
        menu1_btn.setStyle("-fx-background-color: #fff");
        menu2_btn.setStyle("-fx-background-color: #fff");
        menu3_btn.setStyle("-fx-background-color: #fff");
        menu4_btn.setStyle("-fx-background-color: #fff");
        menu5_btn.setStyle("-fx-background-color: #fff");
        menu6_btn.setStyle("-fx-background-color: #fff");
        menu7_btn.setStyle("-fx-background-color: #fff");
    }
    public void displayBackgroundItemNav(MouseEvent event) {
        menu1_btn.setStyle("-fx-background-color: #fff");
        menu2_btn.setStyle("-fx-background-color: #fff");
        menu3_btn.setStyle("-fx-background-color: #fff");
        menu4_btn.setStyle("-fx-background-color: #fff");
        menu5_btn.setStyle("-fx-background-color: #fff");
        menu6_btn.setStyle("-fx-background-color: #fff");
        menu7_btn.setStyle("-fx-background-color: #fff");
        if (event.getSource() == item1_pane) {
            menu1_btn.setStyle("-fx-background-color: #e3fafc");
        } else if (event.getSource() == item2_pane) {
            menu2_btn.setStyle("-fx-background-color: #e3fafc");
        } else if (event.getSource() == item3_pane) {
            menu3_btn.setStyle("-fx-background-color: #e3fafc");
        } else if (event.getSource() == item4_pane) {
            menu4_btn.setStyle("-fx-background-color: #e3fafc");
        } else if (event.getSource() == item5_pane) {
            menu5_btn.setStyle("-fx-background-color: #e3fafc");
        } else if (event.getSource() == item6_pane) {
            menu6_btn.setStyle("-fx-background-color: #e3fafc");
        } else if (event.getSource() == menu7_btn) {
            menu7_btn.setStyle("-fx-background-color: #e3fafc");
        }
    }

    @FXML
    public void EventAuthForm(MouseEvent e) throws IOException {
        if (e.getSource() == SignIn_lb) {
            freeResources();
            ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/LoginForm.fxml","Đăng nhập");
        } else if (e.getSource() == SignUp_lb) {
            freeResources();
            ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/RegisterForm.fxml","Đăng ký");
        }
    }

    public void hideForm(){
        paneListProductSearch.setVisible(false);
        paneProductMarket.setVisible(false);
        paneOrderDetail.setVisible(false);
        paneInformationProduct.setVisible(false);
    }

    private Category category = new Category();
    private int keyCategory = -1;
    /* OldKey..
   -Mục đích để kiểm tra nếu truy vấn danh mục khác nếu gặp giá trị rỗng thì k bị loi pointer
   -Do set lên GUI nên  cachedRow đang ở vị trí dòng bất kỳ, nếu: cachedProducts.beforeFirst(); dẫn đến các trang đều bị set giống nhau
   -Không có thì khi chọn danh mục khác bị null thì lỗi con trỏ
    */
    private int oldKeyCategory = -1;


    public void clickCategory(MouseEvent e) throws SQLException {

        if (e.getSource() == itemPhone_lb) {
            keyCategory = category.listCategory.get(itemPhone_lb.getText());
        } else if (e.getSource() == itemLaptop_lb) {
            keyCategory = category.listCategory.get(itemLaptop_lb.getText());
        } else if (e.getSource() == itemMayChoiGame_lb) {
            keyCategory = category.listCategory.get(itemMayChoiGame_lb.getText());
        } else if (e.getSource() == itemChamSocDa_lb) {
            keyCategory = category.listCategory.get(itemChamSocDa_lb.getText());
        } else if (e.getSource() == itemTrangDiem_lb) {
            keyCategory = category.listCategory.get(itemTrangDiem_lb.getText());
        } else if (e.getSource() == itemNuocHoa_lb) {
            keyCategory = category.listCategory.get(itemNuocHoa_lb.getText());
        } else if (e.getSource() == itemThucPhamChucNang_lb) {
            keyCategory = category.listCategory.get(itemThucPhamChucNang_lb.getText());
        } else if (e.getSource() == itemThietBiYTe_lb) {
            keyCategory = category.listCategory.get(itemThietBiYTe_lb.getText());
        } else if (e.getSource() == itemTrangPhucNam) {
            keyCategory = category.listCategory.get(itemTrangPhucNam.getText());
        } else if (e.getSource() == ItemGiayDepNam) {
            keyCategory = category.listCategory.get(ItemGiayDepNam.getText());
        } else if (e.getSource() == itemDongHoNam) {
            keyCategory = category.listCategory.get(itemDongHoNam.getText());
        } else if (e.getSource() == itemDoLotNam) {
            keyCategory = category.listCategory.get(itemDoLotNam.getText());
        } else if (e.getSource() == itemTrangPhucNu) {
            keyCategory = category.listCategory.get(itemTrangPhucNu.getText());
        } else if (e.getSource() == itemTuiXachNu) {
            keyCategory = category.listCategory.get(itemTuiXachNu.getText());
        } else if (e.getSource() == itemNoiYNu) {
            keyCategory = category.listCategory.get(itemNoiYNu.getText());
        } else if (e.getSource() == itemTrangSucNu) {
            keyCategory = category.listCategory.get(itemTrangSucNu.getText());
        } else if (e.getSource() == itemXeMayMoto) {
            keyCategory = category.listCategory.get(itemXeMayMoto.getText());
        } else if (e.getSource() == itemXeOto) {
            keyCategory = category.listCategory.get(itemXeOto.getText());
        } else if (e.getSource() == itemXeDiaHinh) {
            keyCategory = category.listCategory.get(itemXeDiaHinh.getText());
        } else if (e.getSource() == itemPhuKienTheThao) {
            keyCategory = category.listCategory.get(itemPhuKienTheThao.getText());
        } else if (e.getSource() == itemDoTheThao) {
            keyCategory = category.listCategory.get(itemDoTheThao.getText());
        } else if (e.getSource() == itemValiTuiXach) {
            keyCategory = category.listCategory.get(itemValiTuiXach.getText());
        } else if (e.getSource() == itemKinhMat) {
            keyCategory = category.listCategory.get(itemKinhMat.getText());
        } else if (e.getSource() == itemHoatDongDaNgoai) {
            keyCategory = category.listCategory.get(itemHoatDongDaNgoai.getText());
        } else if (e.getSource() == menu7_btn) {
            keyCategory = category.listCategory.get(menu7_btn.getText());
        }
        if (keyCategory != -1) {
            // paneProductMarket.setVisible(true);
            //calculatePage();
            loadData(e);

        }

    }

    private void showImage() {
        img_Messenger.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/icon-tin-nhan.png")));
        img_shopping_cart.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/icon-gio-hang.png")));
        img_iconSearch.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconSearch.png")));
        imgAvatar.setImage((new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/icon_User.png"))));
        imgLoading.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/loading.gif")));
        imgVoucherOrder.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/voucher.png")));
        imgUserOrder.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconUser.png")));
        imgPhoneOrder.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconPhone.png")));
        imgAddressOrder.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconAddress.png")));
        imgPayAtHome.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconFreeShip.jpg")));
        imgPayBank.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconThanhToan.jpg"))));
    }


    public void defaultForm() {
        if (Temp.account == null) {
            authForm_pane.setVisible(true);
            paneUser.setVisible(false);
        } else {
            authForm_pane.setVisible(false);
            paneUser.setVisible(true);
        }
    }

    public void click(MouseEvent e) throws IOException, SQLException {
        if (e.getSource() == imgAvatar) {
            freeResources();
           if(Temp.account.getPhanQuyen().equals("ADMIN")){
               ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/AdminForm.fxml","Quản lý phần mềm");
           }else {
               ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/ProfileUser.fxml","Hồ sơ");
           }
        }else if(e.getSource() == img_shopping_cart){
            loadDataItemCart();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultForm();
        clickPage();
        showImage();

//        ThreadImageV threadImageV;
//        Rectangle clip = new Rectangle(
//                img_slider.getFitWidth(), img_slider.getFitHeight()
//        );
//        clip.setArcWidth(20);
//        clip.setArcHeight(20);
//        img_slider.setClip(clip);
//
//        // snapshot the rounded image.
//        SnapshotParameters parameters = new SnapshotParameters();
//        parameters.setFill(Color.TRANSPARENT);
//        WritableImage image = img_slider.snapshot(parameters, null);
//
//        // remove the rounding clip so that our effect can show through.
//        img_slider.setClip(null);
//
//        // apply a shadow effect.
//        img_slider.setEffect(new DropShadow(20, Color.BLACK));
//
//        // store the rounded image in the imageView.
//        img_slider.setImage(image);
//        threadImageV = new ThreadImageV(img_slider);
//        threadImageV.start();
    }

    //Giari phóng tài nguyên scene
    public void freeResources(){
        gridVoucher.getChildren().clear();
        gridProductSearch.getChildren().clear();
        grid_Products.getChildren().clear();
        listvoucher.clear();
        listProduct.clear();
        voucherSelected = null;
        SelectedProduct = null;

        img_iconSearch.setImage(null);
        imgAvatar.setImage(null);
        imgLoading.setImage(null);
        imgVoucherOrder.setImage(null);
        imgUserOrder.setImage(null);
        imgPhoneOrder.setImage(null);
        imgAddressOrder.setImage(null);
        imgPayAtHome.setImage(null);
        imgPayBank.setImage(null);

        System.gc(); // Yêu cầu garbage collector thu hồi bộ nhớ

    }
}
