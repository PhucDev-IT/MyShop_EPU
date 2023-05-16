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
    private ImageView imgTop_ProductOne;

    @FXML
    private Label nameProductOne_lb;

    @FXML
    private Label price_ProductOne;

    @FXML
    private Label SoldProduct_one;

    @FXML
    private ImageView imgTop_ProductTwo;

    @FXML
    private Label nameProductTwo_lb;

    @FXML
    private Label price_ProductTwo;

    @FXML
    private Label SoldProduct_two;

    @FXML
    private ImageView imgTop_ProductThree;

    @FXML
    private Label nameProductThree_lb;

    @FXML
    private Label price_ProductThree;

    @FXML
    private Label SoldProduct_three;


    @FXML
    private ImageView imgTopSeller_one;

    @FXML
    private ImageView imgTopSeller_Two;

    @FXML
    private ImageView imgTopSeller_Three;
    @FXML
    private ScrollPane scrollProductSearch;
    @FXML
    private AnchorPane paneLoading;
    @FXML
    private ImageView img_slider;

    @FXML
    private AnchorPane pane_main;
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
    private ScrollPane scrollListProductDetail;
    @FXML
    private GridPane gridProdcut_OrderDetails;

    @FXML
    private AnchorPane paneOrderDetail;

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
    private Text txt_CloseCart;
    @FXML
    private AnchorPane paneShoppingCart;
    @FXML
    private GridPane gridItemCart;

    @FXML
    private Label numbersItemCart_lb;

    @FXML
    private Label sumMoney_itemCartLb;


    //--------------------------------- MESSENGER-----------------------------------------
    @FXML
    private Pane pane_Messenger;

    @FXML
    private GridPane grid_Messenge;

    //------------------------------------------------------------------


    private final int maxProductsOfPage = 10; //1 trang tối đa 10 sản phẩm

    List<Product> listProduct = new ArrayList<>();
    int pageSelected;

    private User user;
    private Product SelectedProduct;    //Lưu giá trị product vừa được click

    private VoucherModel voucherSelected = null;

    private List<VoucherModel> listvoucher = new ArrayList<>();

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final Product_Dao product_Dao = Product_Dao.getInstance(connectionPool);

    private final MessengeDao messengeDao = MessengeDao.getInstance(connectionPool);
    private final VoucherDao voucherDao = VoucherDao.getInstance(connectionPool);

    private final Order_Dao order_dao = Order_Dao.getInstance(connectionPool);
    private final UserDao userDao = UserDao.getInstance(connectionPool);

    private final itemCartDao itemCart_dao = itemCartDao.getInstance(connectionPool);

    //---------------------- KIỂM TRA RỖNG ----------------------------------
    public boolean checkStringIsempty(String str) {
        return str == null || str.length() == 0;
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

        int totalProducts;

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
        txtNumber.setText(String.valueOf(numbersBuyProduct));
        paneOrderDetail.setVisible(false);
        paneShoppingCart.setVisible(false);
        row = 1;
        col = 0;

        gridProductSearch.getChildren().clear();
        grid_Products.getChildren().clear();
        for (Product product : listProduct) {
            setDataProduct(product, grid_Products);
        }

    }

    //Quay trở về giao diện ban đầu sau khi mua hàng
    public void backFormAfterBuy() {

        numbersBuyProduct = 1;
        voucherSelected = null;
        txtNumber.setText(String.valueOf(numbersBuyProduct));
        paneOrderDetail.setVisible(false);
        paneShoppingCart.setVisible(false);
        gridProdcut_OrderDetails.getChildren().clear();
        row = 1;
        col = 0;
        if (grid_Products.getChildren().size() > 0) {
            gridProductSearch.getChildren().clear();
            grid_Products.getChildren().clear();
            for (Product product : listProduct) {
                setDataProduct(product, grid_Products);
            }
        } else if (gridProductSearch.getChildren().size() > 0) {
            gridProductSearch.getChildren().clear();
            grid_Products.getChildren().clear();
            for (Product product : listProduct) {
                setDataProduct(product, gridProductSearch);
            }
        } else {
            hideForm();
            pane_main.setVisible(true);
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
            setDataGridPane(gridPane, anchorPane, col++, row);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void loadData(Event e) {
        Task<Void> task = new Task<>() {
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
                    } else if (e.getSource() == SearchProduct_txt || e.getSource() == scrollProductSearch) {
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
            if (e.getSource() == SearchProduct_txt || e.getSource() == scrollProductSearch) {
                loadDataProductSearch();
            } else if (e instanceof MouseEvent) {

                try {
                    pagination.setPageCount(totalPages > 0 ? totalPages : 1);
                    loadDataProducts();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            Platform.runLater(() -> paneLoading.setVisible(false));
            thread.interrupt();
        });
    }

    //------------------------------------- HIỂN THỊ THÔNG TIN SẢN Phẩm VỪA CHỌN--------------------------------
    public void clickProduct() {
        productMyListener = product -> {
            SelectedProduct = product;
            paneInformationProduct.setVisible(true);
            displayInformationProduct();
        };
    }


    //Kiểm tra ảnh nằm trong project hay nằm ngoài project
    public void displayInformationProduct() {
        try {
            if (!SelectedProduct.getSrcImg().contains(":")) {
                imgProdInfor.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(SelectedProduct.getSrcImg()))));
            } else
                imgProdInfor.setImage(new Image(SelectedProduct.getSrcImg()));
        } catch (Exception e) {
            imgProdInfor.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png"))));
        }
        nameProdInfor_txt.setText(SelectedProduct.getTenSP());
        priceProdInfor.setText(App.numf.format(SelectedProduct.getPrice()) + "đ");
        remainInfor_lb.setText(String.valueOf(SelectedProduct.getQuantity()));
        nameSeller_Infor_lb.setText(SelectedProduct.getUser().getFullName());
        contentProInfor_txa.setText(SelectedProduct.getMoTa());
        soldInfror_lb.setText(String.valueOf(SelectedProduct.getSold()));
    }

    public void upDownNumber(ActionEvent event) {
        if (event.getSource() == BtnUpNumber) {
            numbersBuyProduct++;
        } else if (event.getSource() == BtndownNumber) {
            if (numbersBuyProduct > 1) {
                numbersBuyProduct--;
            }
        }
        txtNumber.setText(String.valueOf(numbersBuyProduct));
    }

    //Kiểm tra người dùng nhập số lượng có chữ cái và xoóa
    @FXML
    public void removeCharInput() {
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
            voucherSelected = null;
            resetDisplayDataVoucher();
            gridProdcut_OrderDetails.getChildren().clear();
            paneOrderDetail.setVisible(false);
        }
    }

    /*
    Khi người dùng nhấn mua sản phẩm thì sẽ xóa các dữlieujeu ở listChoose item để thêm sản phẩm vừa chọn + số lương vào
    Mục đích tái sử dụng hàm và dùng đồng thời mua nhiều sản phẩm cùng lúc
    Mỗi CTHD bắt buộc phải có số lượng và đơn giá, nên mượn itemCart để dùng nó truyền vào OrderDao
     */
    public void BuyProduct(ActionEvent e) throws IOException {
        if (e.getSource() == btnBuyProduct) {
            if (Temp.account != null) {

                if (SelectedProduct.getQuantity() <= 0) {
                    AlertNotification.showAlertWarning("", "Sản phẩm này tạm hết!");
                } else if (Integer.parseInt(txtNumber.getText()) > SelectedProduct.getQuantity()) {
                    AlertNotification.showAlertWarning("", "Số lượng hàng không đủ");
                } else {
                    paneInformationProduct.setVisible(false);
                    listChooseItemCartModel.clear();
                    listChooseItemCartModel.add(new itemCartModel(SelectedProduct, numbersBuyProduct));
                    numbersBuyProduct = Integer.parseInt(txtNumber.getText());
                    tongTien = SelectedProduct.getPrice().multiply(BigDecimal.valueOf(Long.parseLong(txtNumber.getText())));
                    displayListProductOrderDetails(SelectedProduct, numbersBuyProduct);
                    showOderDetail();
                }
            } else {
                if (AlertNotification.showAlertConfirmation("Bạn chưa đăng nhập", "Đăng nhập để mua hàng?")) {
                    ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/LoginForm.fxml", "Đăng nhập");
                }
            }
        }
    }

    //Thêm vào gior hàng
    @FXML
    public void addInCart() throws SQLException {
        addItemCart();
    }

    //---------------------------------- THÔNG TIN CHI TIẾT HÓA ĐƠN - MUA HÀNG -----------------------------------

    private BigDecimal tongTien = new BigDecimal(0);   //Sau khi nhấn mua hàng thì sẽ tính tiền vì khi đó mới có dữ liệu của product
    private BigDecimal thanhTien;
    public boolean checkAddOrder;

    //Thanh toán - Mua Hàng
    @FXML
    public void PayProduct(MouseEvent e) {

        if (checkStringIsempty(phoneUserOder_lb.getText()) || checkStringIsempty(AdressOrder_txt.getText())) {
            AlertNotification.showAlertWarning("Thiếu thông tin!", "Nhập đầy đủ thông tin nhận hàng hoặc cập nhật lại hồ sơ");
        } else {
            Order hoaDon = new Order(new Date(System.currentTimeMillis()), tongTien, voucherSelected, thanhTien, user);

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    Platform.runLater(() -> paneLoading.setVisible(true));
                    Thread.sleep(200);
                    try {
                        if (e.getSource() == payAtHome) {
                            checkAddOrder = PayProductAtHome(hoaDon, listChooseItemCartModel);
                        } else if (e.getSource() == payByBank) {
                            checkAddOrder = payProductByMyVi(hoaDon, listChooseItemCartModel);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                }
            };

            Thread thread = new Thread(task);
            thread.start();

            task.setOnSucceeded(event -> {
                if (checkAddOrder) {
                    resetDisplayDataVoucher();

                    for (itemCartModel item : listChooseItemCartModel) {
                        for (Product pro : listProduct) {
                            if (pro.getID() == item.getProduct().getID()) {
                                pro.setQuantity(pro.getQuantity() - item.getQuantity());
                                pro.setSold(pro.getSold() + item.getQuantity());
                                break;
                            }
                        }
                    }

                    Platform.runLater(() -> {
                        AlertNotification.showAlertSucces("Mua hàng thành công!", "Cảm ơn bạn đã mua hàng");
                        backFormAfterBuy();
                    });

                    //Nếu mua hàng từ giỏ hàng thì xóa các san phẩm trong giỏ hàng sau khi mua
                    if (listChooseItemCartModel.size() > 0) {
                        try {
                            removeAllItemCart();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

                Platform.runLater(() -> paneLoading.setVisible(false));
                thread.interrupt();
            });
        }

    }


    public void resetDisplayDataVoucher() {

        btnAddVoucher.setText("+");
        VcSaleOrder_lb.setText("");
        VcShelfOrder_lb.setText("");
        listvoucher.remove(voucherSelected);
        voucherSelected = null;
    }

    public boolean PayProductAtHome(Order hoaDon, List<itemCartModel> list) throws SQLException {
        if (checkAddOrder = order_dao.OrderDetailsPayAtHome(hoaDon, list)) {
            return true;
        } else {
            Platform.runLater(() -> AlertNotification.showAlertError("", "Có lỗi xảy ra"));
        }
        return false;
    }

    public boolean payProductByMyVi(Order hoaDon, List<itemCartModel> list) throws SQLException {

        if (Temp.account.getMoney().compareTo(thanhTien) <= 0) {
            Platform.runLater(() -> AlertNotification.showAlertWarning("Số dư không đủ!", "Vui lòng nạp thêm tiền..."));
        } else {
            PaymentHistory paymentBank = new PaymentHistory("Mua Hàng", "Thanh toán cho MyShop", thanhTien,
                    new Date(System.currentTimeMillis()), "/com/epu/oop/myshop/image/iconThanhToan.jpg", user, null);
            Temp.account.setMoney(Temp.account.getMoney().subtract(thanhTien));
            if (checkAddOrder = order_dao.OrderDetailsPayByBank(hoaDon, list, paymentBank)) {
                listvoucher.remove(voucherSelected);
                return true;
            } else {
                Platform.runLater(() -> AlertNotification.showAlertError("", "Có lỗi xảy ra"));
            }
        }
        return false;
    }

    public void caculateOrderDetail() {

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

    int rowPro = 1;

    public void displayListProductOrderDetails(Product prod, int number) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/ProductOrdersDetails.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            ProductOrderDetails productDetails = fxmlLoader.getController();
            productDetails.setData(prod, number);
            setDataGridPane(gridProdcut_OrderDetails, anchorPane, col, ++rowPro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //nameProdOrder_txt.setText(SelectedProduct.getTenSP());
        //priceProOder_lb.setText(App.numf.format(SelectedProduct.getPrice()) + "đ");
        numbersProduct.setText(txtNumber.getText());
    }

    public void showOderDetail() {
        paneOrderDetail.setVisible(true);
        //Lấy thông tin người dungf
        user = userDao.SelectByID(new User(Temp.account.getID(), ""));
        nameUserOder_lb.setText(user.getFullName());
        phoneUserOder_lb.setText(user.getNumberPhone());
        AdressOrder_txt.setText(user.getAddress());
        numbersProduct.setText(String.valueOf(numbersBuyProduct));
        //Tổng tiền hàng
        caculateOrderDetail();

    }

    private MyListener<VoucherModel> myListener_Voucher;

    @FXML
    public void addVoucher() {
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
        myListener_Voucher = voucherModel -> {
            voucherSelected = voucherModel;

            paneVoucher.setVisible(false);
            caculateOrderDetail();
            btnAddVoucher.setText("-");
        };
    }


    public void setDataVoucher() {
        int rowVoucher = 1;
        if (listvoucher.size() == 0) {
            listvoucher = voucherDao.getVoucherConTime(user.getID());
        }
        gridVoucher.getChildren().clear();
        clickVoucher();
        for (VoucherModel vc : listvoucher)
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/Voucher.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                VoucherController itemVoucher = fxmlLoader.getController();
                itemVoucher.setData(myListener_Voucher, vc);

                setDataGridPane(gridVoucher, anchorPane, col, ++rowVoucher);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void hiddenFormVoucher() {
        paneVoucher.setVisible(false);
    }

    //--------------------------------------TÌM KIẾM SẢN PHẨM-----------------------------------------
    private AtomicInteger lastIndex = new AtomicInteger(0);
    private final int maxProductSearch = 15;

    public void loadDataProductSearch() {
        paneListProductSearch.setVisible(true);
        if (listProduct.size() == 0) {

            lbNoData.setVisible(true);
        } else {
            lbNoData.setVisible(false);
        }
        int index = lastIndex.get();
        while (index < listProduct.size()) {
            setDataProduct(listProduct.get(index), gridProductSearch);
            index++;
        }
        lastIndex.set(lastIndex.get() + (listProduct.size() - lastIndex.get())); // thay đổi giá trị của biến lastIndex

    }

    public synchronized void getNameProductSearch(ActionEvent e) {
        if (!checkStringIsempty(SearchProduct_txt.getText())) {
            resetBackgroundMenu();
            row = 1;
            col = 0;
            grid_Products.getChildren().clear();
            lastIndex.set(0);   //Vì sau khi nhấn tìm lại thì vị trí bắt đầu đang bị nhảy lên cao r
            listProduct.clear();    //Giai phong bộ nhớ  các node khng dùng đến
            gridProdcut_OrderDetails.getChildren().clear();
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
    private List<itemCartModel> itemCartModelList = new ArrayList<>();

    private MyListener<itemCartModel> myListenerItemCart;

    private ListenerItemCart<Event, itemCartModel> eventMyListener;

    public List<itemCartModel> listChooseItemCartModel = new ArrayList<>();

    /*
    -Khi người dùng tích chọn product, nếu lần đầu tích thì sẽ đc thêm vào list và tính tiền
    -Khi hủy thì sẽ bị xóa khoit list và cập nhật lại tiền
    -Nếu có hành động tăng giảm số lượng thì duyệt vòng lặp để kiểm tra và cập nhật lại sô lượng + tính toán tiền
    -Tông số lượng trước đó = tổng số lượng trước đó + (số lượng mới tăng - số luwognj trc đó trong list) nếu hành động tăng
    -Tổng tiền =
    -setSumItemCarrt(); ở mỗi if vì khi người dùng chưa chọn sp mà nhấn tăng hoặc giảm sẽ xảy ra exceptiom
     */
    public void chooseItemCart() {
        myListenerItemCart = item -> {

            if (item.isChoose()) {
                listChooseItemCartModel.add(item);
                numbersBuyProduct += item.getQuantity();
                tongTien = tongTien.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                setSumItemCarrt();
            } else {
                listChooseItemCartModel.remove(item);
                numbersBuyProduct -= item.getQuantity();
                tongTien = tongTien.subtract(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                setSumItemCarrt();
            }
        };
    }

    public void upDownItemCart() {
        eventMyListener = (event, itm) -> {
            if (((JFXButton) event.getSource()).getId().equals("btn_removeItem")) {
                if (itm.isChoose()) {
                    numbersBuyProduct -= itm.getQuantity();
                    tongTien = tongTien.subtract(itm.getSumMoney());
                    listChooseItemCartModel.remove(itm);
                }
                System.out.println(listChooseItemCartModel);
                updateGridItemcart(itm);
            } else {

                numbersBuyProduct = 0;
                tongTien = new BigDecimal("0");
                System.out.println(listChooseItemCartModel.size());
                for (itemCartModel it : listChooseItemCartModel) {
                    System.out.println(it);
                    numbersBuyProduct += it.getQuantity();
                    tongTien = tongTien.add(it.getSumMoney());
                }
            }
            setSumItemCarrt();
        };
    }

    public void updateGridItemcart(itemCartModel itm) {
        // Tìm nút chứa sản phẩm cần xóa
        int indexToRemove = -1;
        for (int i = 0; i < gridItemCart.getChildren().size(); i++) {
            AnchorPane anchorPane = (AnchorPane) gridItemCart.getChildren().get(i);
            itemCartModel it = (itemCartModel) anchorPane.getUserData();
            if (it.getIdCart() == itm.getIdCart()) {
                indexToRemove = i;
                break;
            }
        }

        // Xóa nút chứa sản phẩm cần xóa và cập nhật lại vị trí của các nút còn lại
        if (indexToRemove != -1) {
            gridItemCart.getChildren().remove(indexToRemove);
            for (int i = indexToRemove; i < gridItemCart.getChildren().size(); i++) {
                AnchorPane anchorPane = (AnchorPane) gridItemCart.getChildren().get(i);
                updateRowAndColumn(anchorPane, i + 2, gridItemCart);
            }
        }
    }

    private void updateRowAndColumn(AnchorPane anchorPane, int index, GridPane gridPane) {

        gridPane.setColumnIndex(anchorPane, 0);
        gridPane.setRowIndex(anchorPane, index);
        // set grid width
        gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setMaxWidth(Region.USE_PREF_SIZE);

        // set grid height
        gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setMaxHeight(Region.USE_PREF_SIZE);
    }

    public void setSumItemCarrt() {
        numbersItemCart_lb.setText(String.valueOf(numbersBuyProduct));
        sumMoney_itemCartLb.setText(App.numf.format(tongTien) + "đ");
    }

    public void loadDataItemCart() throws SQLException {
        rowItemCart = 1;
        numbersBuyProduct = 0;
        tongTien = new BigDecimal(0);
        listChooseItemCartModel.clear();
        itemCartModelList.clear();
        hideForm();
        paneShoppingCart.setVisible(true);
        itemCartModelList.addAll(itemCart_dao.getDataByUser(Temp.account.getID()));
        for (itemCartModel it : itemCartModelList) {
            setDataBasket(it);
        }

    }

    public void setDataBasket(itemCartModel it) {

        upDownItemCart();
        chooseItemCart();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/itemCartForm.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            ItemCartController itemCategory = fxmlLoader.getController();
            itemCategory.setData(myListenerItemCart, it, eventMyListener);

            anchorPane.setUserData(it); //Lưu trữ các đối tượng tùy ý , và có thể truy cập lấy đối tượng đó ra xem
            setDataGridPane(gridItemCart, anchorPane, 0, ++rowItemCart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Thêm vào giỏ hàng
    public void addItemCart() throws SQLException {
        if (Temp.account != null) {
            itemCartModel itemCartModel = new itemCartModel(0, SelectedProduct, Temp.account.getID(), keyCategory, Integer.parseInt(txtNumber.getText()));
            if (itemCart_dao.Insert(itemCartModel)) {
                AlertNotification.showAlertSucces("", "Kiểm tra giỏ hàng để xem nhé bạn!");
            } else {
                AlertNotification.showAlertError("", "Có lỗi xảy ra");
            }
        } else {
            AlertNotification.showAlertWarning("Bạn chưa đăng nhập", "Đăng nhập trước khi thao tác");
        }
    }

    public void BuyProductInCart() {
        if (listChooseItemCartModel.size() >= 2) {
            scrollListProductDetail.setStyle("-fx-border-color: #53cfb4");
        }
        paneShoppingCart.setVisible(false);
        showOderDetail();
        for (itemCartModel it : listChooseItemCartModel) {
            displayListProductOrderDetails(it.getProduct(), it.getQuantity());
        }
    }

    //---- Xóa all product trong giỏ hàng sau khi đã mua xong
    public void removeAllItemCart() throws SQLException {
        for (itemCartModel it : listChooseItemCartModel) {
            itemCart_dao.Delete(it);
        }
    }

    //----------------------------------MESSENGER-----------------------------------------------
    private List<Messenger> listMess = new ArrayList<>();

    private boolean checkEndMess = true;
    AtomicInteger getNumbersMessenge = new AtomicInteger(0);
    int rowMess = 1;

    public void RefreshMessenger() throws SQLException {
        pane_Messenger.setVisible(true);
        getNumbersMessenge.set(0);
        checkEndMess = true;
        rowMess = 1;
        listMess.clear();
        grid_Messenge.getChildren().clear();
        setDataMessenge();
    }

    //Set dữ liệu
    public void setDataMessenge() throws SQLException {
        listMess.addAll(messengeDao.getDataMessenge(Temp.account.getID(), getNumbersMessenge));

        int index = getNumbersMessenge.get();
        while (index < listMess.size()) {
            DisplayMessenge(listMess.get(index));
            index++;
        }

        if ((listMess.size() - getNumbersMessenge.get()) == 0) {
            checkEndMess = false;
        }
        getNumbersMessenge.set(getNumbersMessenge.get() + (listMess.size() - getNumbersMessenge.get()));

    }

    //Cuộn xem tin nhắn
    public synchronized void ScrollMessenge() throws SQLException {
        if (!checkEndMess) {
            setDataMessenge();
        }
    }

    public void DisplayMessenge(Messenger messenger) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/MessengerForm.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            MessengerController mss = fxmlLoader.getController();
            mss.setData(messenger);

            setDataGridPane(grid_Messenge, anchorPane, 0, ++rowMess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Tắt form tin nhắn
    @FXML
    public void ShowHiddenMessenge() throws SQLException {

        if (pane_Messenger.isVisible()) {
            pane_Messenger.setVisible(false);
            grid_Messenge.getChildren().clear();
            listMess.clear();
        } else {
            pane_Messenger.setVisible(true);
            RefreshMessenger();
        }
    }

    //--------------------------- SET DATA TOP 3 PRODUCT ----------------------------------------
    private Vector<Product> listTopProduct = new Vector<>();

    public void setDataTopProduct() {

        if (listTopProduct.size() == 0) {
            try {
                listTopProduct.addAll(product_Dao.selectTopThreeProduct());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        imgTop_ProductOne.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(listTopProduct.get(0).getSrcImg()))));
        imgTop_ProductTwo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(listTopProduct.get(1).getSrcImg()))));
        imgTop_ProductThree.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(listTopProduct.get(2).getSrcImg()))));

        nameProductOne_lb.setText(listTopProduct.get(0).getTenSP());
        price_ProductOne.setText(App.numf.format(listTopProduct.get(0).getPrice()) + "đ");
        SoldProduct_one.setText(String.valueOf(listTopProduct.get(0).getSold()));

        nameProductTwo_lb.setText(listTopProduct.get(1).getTenSP());
        price_ProductTwo.setText(App.numf.format(listTopProduct.get(1).getPrice()) + "đ");
        SoldProduct_two.setText(String.valueOf(listTopProduct.get(1).getSold()));

        nameProductThree_lb.setText(listTopProduct.get(2).getTenSP());
        price_ProductThree.setText(App.numf.format(listTopProduct.get(2).getPrice()) + "đ");
        SoldProduct_three.setText(String.valueOf(listTopProduct.get(2).getSold()));

    }


    //-------------------------------------
    //Khi hover vào các nav-item
    @FXML
    public void HoverMenuNavbar(MouseEvent e) {
        hiddenListItemOfMenu();

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
    @FXML
    public void hiddenListItemOfMenu() {
        item1_pane.setVisible(false);
        item2_pane.setVisible(false);
        item3_pane.setVisible(false);
        item4_pane.setVisible(false);
        item5_pane.setVisible(false);
        item6_pane.setVisible(false);
    }

    public void resetBackgroundMenu() {
        menu1_btn.setStyle("-fx-background-color: #fff");
        menu2_btn.setStyle("-fx-background-color: #fff");
        menu3_btn.setStyle("-fx-background-color: #fff");
        menu4_btn.setStyle("-fx-background-color: #fff");
        menu5_btn.setStyle("-fx-background-color: #fff");
        menu6_btn.setStyle("-fx-background-color: #fff");
        menu7_btn.setStyle("-fx-background-color: #fff");
    }

    public void displayBackgroundItemNav(MouseEvent event) {
        resetBackgroundMenu();
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
            ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/LoginForm.fxml", "Đăng nhập");
        } else if (e.getSource() == SignUp_lb) {
            freeResources();
            ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/RegisterForm.fxml", "Đăng ký");
        }
    }

    public void hideForm() {
        paneListProductSearch.setVisible(false);
        paneProductMarket.setVisible(false);
        paneOrderDetail.setVisible(false);
        paneInformationProduct.setVisible(false);
        paneShoppingCart.setVisible(false);
        pane_main.setVisible(false);
        lbNoData.setVisible(false);
        gridItemCart.getChildren().clear();
        gridProductSearch.getChildren().clear();
        gridVoucher.getChildren().clear();
        grid_Products.getChildren().clear();


    }

    private Category category = new Category();
    private int keyCategory = -1;

    public void clickCategory(MouseEvent e) {

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
            voucherSelected = null; //Tránh trtruowngfhop mua sản phảm khác đang còn voucher cũ và tính tiền cũ
            resetDisplayDataVoucher();
            listChooseItemCartModel.clear();
            gridProdcut_OrderDetails.getChildren().clear();
            loadData(e);


        }

    }

    private void showImage() {

        imgTopSeller_Three.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/gai-xinh.jpg"))));
        imgTopSeller_Two.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/doanh-nhan.png"))));
        imgTopSeller_one.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/top-seller-1.png"))));
        img_Messenger.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/icon-tin-nhan.png"))));
        img_shopping_cart.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/icon-gio-hang.png"))));
        img_iconSearch.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconSearch.png"))));
        imgAvatar.setImage((new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/icon_User.png")))));
        imgLoading.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/loading.gif"))));
        imgVoucherOrder.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/profile/voucher.png"))));
        imgUserOrder.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconUser.png"))));
        imgPhoneOrder.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconPhone.png"))));
        imgAddressOrder.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconAddress.png"))));
        imgPayAtHome.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconFreeShip.jpg"))));
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
            if (Temp.account.getPhanQuyen().equals("ADMIN")) {
                ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/AdminForm.fxml", "Quản lý phần mềm");
            } else {
                ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(), "/com/epu/oop/myshop/GUI/ProfileUser.fxml", "Hồ sơ");
            }
        } else if (e.getSource() == img_shopping_cart) {
            loadDataItemCart();
        } else if (e.getSource() == txt_CloseCart) {
            listChooseItemCartModel.clear();
            paneShoppingCart.setVisible(false);
            pane_main.setVisible(true);
        } else if (e.getSource() == myShop_logo) {
            hideForm();
            pane_main.setVisible(true);
        }

    }


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


    public void slider() {
        ThreadImageV threadImageV;
        threadImageV = new ThreadImageV(img_slider);
        threadImageV.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultForm();
        clickPage();
        showImage();

        setDataTopProduct();
        slider();
    }

    //Giari phóng tài nguyên scene
    public void freeResources() {
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
