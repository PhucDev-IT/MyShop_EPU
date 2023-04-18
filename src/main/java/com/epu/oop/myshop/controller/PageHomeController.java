package com.epu.oop.myshop.controller;


import com.epu.oop.myshop.Dao.Product_Dao;
import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
import javafx.util.Callback;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PageHomeController implements Initializable {

    //------------------------------- HEADER----------------------------------------------------------------------
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

    private MyListener<Product> productMyListener;

    int numbersBuyProduct = 1;

    private final int maxProductsOfPage = 10; //1 trang tối đa 10 sản phẩm


    int pageSelected ;
    private CachedRowSet cachedProducts;

    private Product SelectedProduct;    //Lưu giá trị product vừa được click

    private Product_Dao product_Dao = Product_Dao.getInstance();

    //-------------------------------------------- HIỂN THỊ CÁC SẢN PHẨM THUỘC DANH MUC ?------------


    //Tính số trang và lấy dữ liệu từ cachedRowSet để hiển thị lên GUI
    public void calculatePage() throws SQLException {
        int totalPages = 0;
        int totalProducts = 0;

        cachedProducts = product_Dao.CachedProduct(keyCategory);

        if(cachedProducts != null && cachedProducts.size() > 0) {   //TRánh việc chia cho 0
            totalProducts = cachedProducts.size();
            totalPages = totalProducts > 0 ? (int) Math.ceil((double) totalProducts / maxProductsOfPage) : 0;
        }

        if(totalProducts<=0){
            lbNoData.setVisible(true);
        }else{
            lbNoData.setVisible(false);
        }
        pagination.setPageCount(totalPages>0?totalPages:1);
        pageSelected = 1;   //Ban đầu sẽ hiện danh sách ở page 1
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

        int offset = (pageSelected - 1) * maxProductsOfPage;
        int limit = maxProductsOfPage;
        Product pro;
        cachedProducts.absolute(offset + 1);
        cachedProducts.beforeFirst();    //Đặt lại con trỏ đọc của CachedRowSet vào vị trí đầu tiên
        //CachedRowSet.next() để di chuyển con trỏ đến hàng đầu tiên của CachedRowSet.
        while (cachedProducts.next() && limit-- > 0) {
            int ID = cachedProducts.getInt("ID");
            String TenSP = cachedProducts.getString("TenSP");
            float soLuong = cachedProducts.getFloat("SoLuong");
            BigDecimal donGia = cachedProducts.getBigDecimal("DonGia");
            String MoTa = cachedProducts.getString("MoTa");
            String SrcImg = cachedProducts.getString("SrcImg");
            int DanhMuc = cachedProducts.getInt("Category_ID");
            pro = new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,DanhMuc);
            setDataProduct(pro);
        }
    }
    int row = 1;
    int col = 0;
    public void setDataProduct(Product prod)
    {
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

    //------------------------------------- HIỂN THỊ THÔNG TIN SẢNPHAAMRM VỪA CHỌN--------------------------------
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
        priceProdInfor.setText(App.numf.format(SelectedProduct.getDonGia())+"đ");
        remainInfor_lb.setText(SelectedProduct.getSoLuong()+"");
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
        // Tạo một TextFormatter có sẵn để giới hạn giá trị chỉ là số
        TextFormatter<String> formatter = new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            } else {
                return null;
            }
        });

        // Thiết lập text formatter cho TextField
        txtNumber.setTextFormatter(formatter);

        // Thêm sự kiện TextChange vào để xóa các kí tự không phải là số
        txtNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtNumber.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    //Nhấn cloes information
    @FXML
    public void closeForm(MouseEvent event)
    {
        if(event.getSource() == closeInfromation){
            paneInformationProduct.setVisible(false);
        }
    }




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
            paneProductMarket.setVisible(true);
            calculatePage();
        }

    }
    private void showImage(){
        img_iconSearch.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconSearch.png"));
        imgAvatar.setImage((new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/img1.jpg"))));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cachedProducts = RowSetProvider.newFactory().createCachedRowSet();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }


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
