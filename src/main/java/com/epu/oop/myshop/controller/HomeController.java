package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Dao.*;
import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.*;

public class HomeController implements Initializable {


    @FXML
    private Text PageHome_txt;

    //---------- Search product Form ---------------------------

    @FXML
    private JFXTextField search_jtxt;

    @FXML
    private AnchorPane SearchProducts_Form;

    @FXML
    private GridPane grid_Search;

    private List<Product> listproductSearch;


    // ------------FORM BÁN HÀNG -------------------------


    @FXML
    private AnchorPane BanHangForm;

    @FXML
    private GridPane girdBanHang;

    @FXML
    private ImageView img_ThreadNotification;
    private MyListener<Product> myListener_Prod;
    private List<Product> listProduct = new ArrayList<>();


    // -------------------Category FORM------------------------


    @FXML
    private GridPane gridDanhMuc;

    private MyListener<Category> mylistener_Category;

    private List<Category> listCategory = new ArrayList<>();

    //-------------------- Form đầu tiên --------------------------

    @FXML
    private AnchorPane startForm;

    @FXML
    private ImageView Calendar_img;
    @FXML
    private Label timeDay_label;

    @FXML
    private ImageView img1_BanChay;

    @FXML
    private ImageView img2_BanChay;

    @FXML
    private ImageView img3_BanChay;

    @FXML
    private ImageView imgTop3;

    @FXML
    private Text nameProdTop3_text;

    @FXML
    private Label priceProdTop3_lb;

    @FXML
    private ImageView imgTop2;

    @FXML
    private Text nameProdTop2_text;

    @FXML
    private Label priceProdTop2_lb;

    @FXML
    private ImageView imgTop1;

    @FXML
    private Text nameProdTop1_text;

    @FXML
    private Label priceProdTop1_lb;


    //------------------------------------ DEFAULT VAlue ------------------------------------


    @FXML
    private ImageView imagUser;


    //----------------------- Thông tin Từng Sản Phẩm Form + Mua hàng --------------------
    @FXML
    private Pane formPayProduct;
    @FXML
    private JFXTextField Jtxt_DiaChiNhanHang;

    @FXML
    private Label tenKhInBuy_lb;

    @FXML
    private Label phoneInBuy_lb;

    @FXML
    private ImageView imgTenKhInBuy;

    @FXML
    private ImageView imgPhone;

    @FXML
    private ImageView imgDiaChiNhanHang;

    @FXML
    private ImageView imgSanPhamInMuaHang;

    @FXML
    private Text tenSpInMuaHang_txt;

    @FXML
    private Label giaGocInMuaHang_txt;


    @FXML
    private Label soSanPhamBuy_lb;

    @FXML
    private Label TotalMoney_lb;

    @FXML
    private Label tongThanhToan_lb;

    @FXML
    private Pane pnPayHome;

    @FXML
    private ImageView imgPayHome;

    @FXML
    private Pane pnPayBank;

    @FXML
    private ImageView imgPayBank;

    @FXML
    private Pane InformationProduct;

    @FXML
    private ImageView imgInFor;

    @FXML
    private Text tenSPInFor_txt;

    @FXML
    private Label GiaBanInFor_label;

    @FXML
    private Label SoHangConLai_Label;

    @FXML
    private JFXTextArea NoiDungInfor_txta;

    @FXML
    private Label nguoiBanInFor_label;


    @FXML
    private Label closeInforProduct;

    @FXML
    private JFXTextField inputSoLuong_Jtxt;

    @FXML
    private Label iconTru_label;

    @FXML
    private Label iconCong_label;

    @FXML
    private Label GiamTienVoucher_lb;

    @FXML
    private ImageView imgVoucher;

    @FXML
    private Label SaleMoney_lb;

    @FXML
    private Pane VoucherForm_pane;

    @FXML
    private GridPane gridVoucher;

    @FXML
    private JFXButton addVoucher;

    private int soLuongMuaHang = 1;
    private User userSeller;
    private Product product;

    private MyListener<VoucherModel> myListener_Voucher;


    //-----------------------------------

    //Định dạng tiền tệ việt nam


    private final Product_Dao product_Dao = Product_Dao.getInstance();
    private final UserDao user_Dao = UserDao.getInstance();
    private final HoaDon_Dao hoaDon_Dao = HoaDon_Dao.getInstance();
    private final CTHoaDon_Dao ctHoaDon_dao = CTHoaDon_Dao.getInstance();
    private final Account_Dao account_dao = Account_Dao.getInstance();
    private final PaymentHistory_Dao paymentHistory_dao = PaymentHistory_Dao.getInstance();
    private final Bank_Dao  bank_Dao = Bank_Dao.getInstance();
    private final VoucherDao voucherDao = VoucherDao.getInstance();
    static boolean isStringEmpty(String string) {
        return string == null || string.length() == 0 || string.equalsIgnoreCase("null");
    }

    //-------------------------------- MUA HÀNG -------------------------------------------------------
    private float soluong;

    //Cần kiểm tra số lượng mua có lớn hơn số lượng đang còn của hàng không nữa
    @FXML
    public void MuaHang(ActionEvent e) throws IOException {
        //* Kiểm tra số lượng > 0 và hàng mua nữa
        if(Temp.account==null)
        {
            if(AlertNotification.showAlertConfirmation("", "Đăng nhập để mua sản phẩm")) {
                ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/LoginForm.fxml");
            }

        }else {

            if(product.getSoLuong()<=0)
            {
                AlertNotification.showAlertWarning("", "Sản phẩm đã hết hàng!");
            }else {
                if(checkInputNumber())
                {
                    soluong = Float.parseFloat(inputSoLuong_Jtxt.getText());

                    if(soluong>product.getSoLuong())
                    {
                        AlertNotification.showAlertWarning("", "Số lượng hàng không đủ!");

                    }else if(soluong<=0) {
                        AlertNotification.showAlertWarning("","Vui lòng nhập số lượng và lớn hơn 0");
                        inputSoLuong_Jtxt.setText("1");
                    }else {
                        formPayProduct.setVisible(true);
                        ShowFormThanhToan();
                    }
                }
            }
        }
    }

    private List<VoucherModel> listvoucher = new ArrayList<>();

    public void chooseVoucher(ActionEvent e){
        listvoucher = voucherDao.getVoucherConTime(Temp.account.getID());
        if(listvoucher==null){
            AlertNotification.showAlertError("","Bạn không có voucher nào");
        }else if(e.getSource() == addVoucher ){
            if(addVoucher.getText().equals("+")){
                VoucherForm_pane.setVisible(true);
                setDataVoucher();
            }else{
                addVoucher.setText("+");
                voucherClicked = null;
                setDataChiTietHD();
            }
        }
    }
    //Nếu nhấn ra ngoài form voucher mất
    public void outHideVoucherForm(MouseEvent e){
        VoucherForm_pane.setVisible(false);
    }



    private VoucherModel voucherClicked = null;
    private BigDecimal tongTien ;   //Sau khi nhấn mua hàng thì sẽ tính tiền vì khi đó mới có dữ liệu của product
    private BigDecimal thanhTien;

    public void clickChooseVoucher(){
        myListener_Voucher = voucherModel -> {

            voucherClicked = voucherModel;

            VoucherForm_pane.setVisible(false);
            addVoucher.setText("-");
            //Chọn voucher xong set data hiển thị
            setDataChiTietHD();
        };
    }
    public void setDataVoucher(){
        int col = 0;
        int row = 0;
        clickChooseVoucher();
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
    public void PayOrder(MouseEvent e)
    {
        if(isStringEmpty(phoneInBuy_lb.getText()) || isStringEmpty(Jtxt_DiaChiNhanHang.getText())){
            AlertNotification.showAlertError("","Thông tin của bạn đang thiếu");
        }else{
            if(e.getSource() == pnPayHome){
                ThanhToan();
            }else if(e.getSource() == pnPayBank){
                PayByBank();
            }
        }
    }

    public void PayByBank()
    {
        Bank bank = bank_Dao.SelectByID(new Bank(Temp.user));
        if(bank!=null){
            Temp.bank = bank;
            if(Temp.account.getMoney().compareTo(BigDecimal.ZERO) <= 0){
                AlertNotification.showAlertError("","Số dư của bạn không đủ");
            }else{
                if(thanhTien.compareTo(Temp.account.getMoney())<=0){
                    ThanhToan();
                    PaymentHistory paymentBank = new PaymentHistory("Mua Hàng","Thanh toán cho MyShop",thanhTien,
                    new Date(System.currentTimeMillis()),"/com/epu/oop/myshop/image/iconThanhToan.jpg",Temp.user,null);
                    paymentHistory_dao.PaymentMyShop(paymentBank);

                    Temp.account.setMoney(Temp.account.getMoney().subtract(thanhTien));
                    account_dao.Update(Temp.account);
                }else{
                    AlertNotification.showAlertError("","Số dư của bạn không đủ");
                }
            }
        }else{
            AlertNotification.showAlertError("","Bạn chưa liên kết ngân hàng.");
        }
    }
    public void ThanhToan(){
        HoaDon hoadon = new HoaDon(new Date(System.currentTimeMillis()),tongTien,voucherClicked,thanhTien, new User(Temp.account.getID()));
        CTHoaDon cthoadon = new CTHoaDon(product,soluong,product.getDonGia());
        int check = 0;
        if(voucherClicked!=null){
            check =  hoaDon_Dao.Insert(hoadon);
        }else{
            //check=  hoaDon_Dao.HoaDonNotVoucher(hoadon);
        }

        if(check>0)
        {
            ctHoaDon_dao.Insert(cthoadon);
            AlertNotification.showAlertSucces("","Mua hàng thành công!");
                            /*
                            Mỗi khi mua hàng xong cần reset lại số lượng còn lại và đã bán. SELECT lại all dữ liệu
                            (cách này tốn tài nguyên và chậm chương trình) để set
                            Nếu mua thành công xong remoe product đó và add lại với số lượng khác thì sẽ không trùng khớp
                            với mua khi tìm SP và khi mua bằng select ban đầu
                            Vì vậy: cần select lại và add lại tất cả sản phẩm thộc danh mục đang ở bằng cách lấy thông tin
                            danh mục của sản phẩm vừa mua
                             */
//            listProduct.clear();
//            listProduct.addAll(product_Dao.getListProduct(new Category(product.getID())));
            setGiaoDienProduct();
            InformationProduct.setVisible(false);
            voucherClicked = null;
        }else {
            AlertNotification.showAlertError("","Mua hàng không thành công, Vui" +
                    "lòng thử lại sau.");
        }
    }
    public void setDataChiTietHD(){
    if(voucherClicked!=null){
        if(voucherClicked.getTiLeGiamGia()>0.0){
            GiamTienVoucher_lb.setText(voucherClicked.getTiLeGiamGia()+"%");
            BigDecimal tilegiam = new BigDecimal(voucherClicked.getTiLeGiamGia());
            thanhTien = tongTien.subtract(tongTien.multiply((tilegiam.divide(BigDecimal.valueOf(100)))));

        }else if(voucherClicked.getSoTienGiam()!=null || voucherClicked.getSoTienGiam().compareTo(BigDecimal.ZERO)>0){
            GiamTienVoucher_lb.setText(App.numf.format(voucherClicked.getSoTienGiam()));
            thanhTien = tongTien.subtract(voucherClicked.getSoTienGiam());
            if(thanhTien.compareTo(BigDecimal.ZERO)<0)
                thanhTien = new BigDecimal("0");
        }
    }else{
        thanhTien = tongTien;
        GiamTienVoucher_lb.setText("");
    }
        soSanPhamBuy_lb.setText(inputSoLuong_Jtxt.getText());
        TotalMoney_lb.setText(App.numf.format(tongTien)+"");
        SaleMoney_lb.setText(GiamTienVoucher_lb.getText());
        tongThanhToan_lb.setText(App.numf.format(thanhTien)+"");
    }

    public void ShowFormThanhToan(){
        tongTien = new BigDecimal(product.getDonGia().multiply(BigDecimal.valueOf(soluong))+"");
        imgSanPhamInMuaHang.setImage(imgInFor.getImage());
        tenSpInMuaHang_txt.setText(tenSPInFor_txt.getText());
        giaGocInMuaHang_txt.setText(GiaBanInFor_label.getText()+"đ");



        //Thông tin nhận hàng
        Temp.user = user_Dao.SelectByID(new User(Temp.account.getID()));
        tenKhInBuy_lb.setText(Temp.user.getFullName());
        phoneInBuy_lb.setText(Temp.user.getNumberPhone());
        Jtxt_DiaChiNhanHang.setText(Temp.user.getAddress());

        //Chi tiết hóadđơn
        setDataChiTietHD();




        //Set img
        imgTenKhInBuy.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/icon_user.png"))));
        imgPhone.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconPhone.png"));
        imgDiaChiNhanHang.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconAddress.png"));
        imgPayHome.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconFreeShip.jpg"));
        imgPayBank.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconThanhToan.jpg"))));
    }

    public void clickProducts()
    {

        myListener_Prod = new MyListener<>() {
            @Override
            public void onClickListener(Product t) {
                InformationProduct.setVisible(false);
                soLuongMuaHang = 0;
                product = t;
                userSeller = product_Dao.selectSeller(product);

                setProfileProduct(userSeller, t);
            }
        };
    }

    @FXML
    public void up_down(MouseEvent e)
    {

        if(e.getSource() == iconTru_label )
        {
            soLuongMuaHang--;
            if(soLuongMuaHang<=1) {
                soLuongMuaHang = 1;
            }
        }else if(e.getSource() == iconCong_label)
        {
            soLuongMuaHang++;
        }
        inputSoLuong_Jtxt.setText(soLuongMuaHang+"");
    }

    //Kiểm tra người dùng nhập số lượng từ bàn phím có kí tự
    public boolean checkInputNumber()
    {
        int n = inputSoLuong_Jtxt.getText().length();
        for(int i=0;i<n;i++)
        {
            if((inputSoLuong_Jtxt.getText().charAt(i)>=65 && inputSoLuong_Jtxt.getText().charAt(i)<=90) ||
                    (inputSoLuong_Jtxt.getText().charAt(i)>=97 && inputSoLuong_Jtxt.getText().charAt(i)<=122)
                    || (inputSoLuong_Jtxt.getText().charAt(i)=='0' && inputSoLuong_Jtxt.getText().length()==1))
            {
                return false;
            }
        }
        return true;
    }


    //------------ Khi nhấn sản phẩm bất kì thì hiện form thông tin để mua hàng --------------------
    public void setProfileProduct(User u,Product product)
    {
        formPayProduct.setVisible(false);
        InformationProduct.setVisible(true);
        imgInFor.setImage(new Image(product.getSrcImg()));
        tenSPInFor_txt.setText(product.getTenSP());
        GiaBanInFor_label.setText(App.numf.format(product.getDonGia())+"");
        NoiDungInfor_txta.setText(product.getMoTa());
        nguoiBanInFor_label.setText(u.getFullName());
        SoHangConLai_Label.setText(product.getSoLuong()+"");
        inputSoLuong_Jtxt.setText(soLuongMuaHang+"");	//Ban đầu sẽ hiện số 1
        //daBanInFor_Label.
    }





    /*
     * Mỗi lần click vào 1 danh mục nào đó thì Category có ID đó sẽ truy vấn lấy list product thông qua OneToMany
     * Sau khi lấy dữ liệu thì sẽ xóa các dữ liệu của category khác còn trong gridpane để hiện cái mới
     */
    private Category categoryclicked;
    int lastProductID;  //Lưu sô lượng đã select ra sản phẩm
    public void setDanhMuc() {
        mylistener_Category = new MyListener<>() {
            @Override
            public void onClickListener(Category c) {
                startForm.setVisible(false);
                InformationProduct.setVisible(false);
                BanHangForm.setVisible(true);
                formPayProduct.setVisible(false);
                listProduct.clear();
                lastProductID = 0;

                categoryclicked = c;
                //Xóa các node trong gridPane
                girdBanHang.getChildren().clear();
                setGiaoDienProduct();
            }

    };}

    /*
    Mỗi sản phẩm đều được nằm trên 1 Node
    MouseEvent trong JavaFX là một sự kiện xảy ra khi người dùng tương tác với chuột trên một Node.
    AnchorPane lấy fxml và mỗi fxml sẽ có controller. Controller này có nhiệm vụ setdata
    Nhưng mỗi lần set dữ liệu thì ta đều truyền mouseEven và sản phẩm thứ i trong danh sách, nên có l Node đó có thông tin
    của sản phẩm đó
    Mỗi khi ta nhấn chuột vào sản phẩm thì bên controller sẽ tiếp nhận thông tin truoc và truyền thông tin đó đến phía bên kia
    Và hiển nhiên sự kiện được hoạt động nên các phần phía sau trong onlick đều được thực thi
     */
    public void setGiaoDienDanhMuc() {
        int col = 0;
        int row = 1;
        setDanhMuc();
        for (Category category:listCategory)
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/itemDanhMuc.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                gridDanhMuc.add(anchorPane, col, row++); // (child,column,row)
                // set grid width
                gridDanhMuc.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridDanhMuc.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridDanhMuc.setMaxWidth(Region.USE_PREF_SIZE);

                // set grid height
                gridDanhMuc.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridDanhMuc.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridDanhMuc.setMaxHeight(Region.USE_PREF_SIZE);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }



    /*Mục tiêu: Sẽ sử dụng chung 1 Panel để set cho All products thuộc category.
     * Khi click vào ccategory và lấy dữ liệu product thuộc category đó xong thì this method set lại các sp
     * Phải truyền category để nó biết gọi thằng nào
     * */

    //Truyền clickProduct vào vì
    //Đây là giao diện chứa các sản phẩm. Mỗi khi nhấn vào sản phẩm bất kỳ thì sẽ truyền thông tin đến listener
    //Mỗi lần đều chạy clickproduct
    public void setGiaoDienProduct() {
        int col = girdBanHang.getColumnCount();
        int row = girdBanHang.getRowCount();
        clickProducts();
        try {
            for (Product prod:listProduct) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/ItemofMarket.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemMarketController item = fxmlLoader.getController();
                item.setData(myListener_Prod, prod);
                if(col == 6)
                {
                    col = 0;
                    row++;
                }

                girdBanHang.add(anchorPane, col++, row); //(child,column,row)
                //set grid width
                girdBanHang.setMinWidth(Region.USE_COMPUTED_SIZE);
                girdBanHang.setPrefWidth(Region.USE_COMPUTED_SIZE);
                girdBanHang.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                girdBanHang.setMinHeight(Region.USE_COMPUTED_SIZE);
                girdBanHang.setPrefHeight(Region.USE_COMPUTED_SIZE);
                girdBanHang.setMaxHeight(Region.USE_PREF_SIZE);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Khi có hành động cuộn thì sẽ select các sản phẩm tiếp theo
    public void eventScrollProduct(ScrollEvent event){
        lastProductID += listProduct.size();
        System.out.println(lastProductID);
        if(categoryclicked!=null ){
            listProduct.clear();
            if(listProduct.size() == 0)
            System.out.println("helo");
       //     lastProductID = girdBanHang.getChildren().isEmpty() ? 0 : ((Product) girdBanHang.getChildren().get(girdBanHang.getChildren().size() - 1)).getProduct().getID();

            setGiaoDienProduct();
        }
    }

    //----------------- Show top 3 product bán chạy nhất ---------------------------------------
    private List<Product> listTop3 = new ArrayList<>();
    public void showTop3()
    {

        listTop3 = product_Dao.topProducts();

        for(int i=0;i<listTop3.size();i++){
            if(i==0) {
                imgTop1.setImage(new Image(listTop3.get(i).getSrcImg()));
                nameProdTop1_text.setText((listTop3.get(i).getTenSP()));
                priceProdTop1_lb.setText(App.numf.format(listTop3.get(i).getDonGia()) + "đ");
            }else if(i==1){
                imgTop2.setImage(new Image(listTop3.get(1).getSrcImg()));
                    nameProdTop2_text.setText((listTop3.get(1).getTenSP()));
                    priceProdTop2_lb.setText(App.numf.format(listTop3.get(1).getDonGia()) + "đ");
            }else{
                imgTop3.setImage(new Image(listTop3.get(2).getSrcImg()));
                    nameProdTop3_text.setText((listTop3.get(2).getTenSP()));
                    priceProdTop3_lb.setText(App.numf.format(listTop3.get(2).getDonGia()) + "đ");
            }

        }

    }
    @FXML
    private Pane panelTop1;
    @FXML
    private Pane panelTop2;
    @FXML
    private Pane panelTop3;

    @FXML
    public void ClickViewTopProduct(MouseEvent e)
    {
        if(e.getSource() == panelTop1){
            userSeller = product_Dao.selectSeller(listTop3.get(0));
            setProfileProduct(userSeller,listTop3.get(0));
        }else if(e.getSource() == panelTop2){
            userSeller = product_Dao.selectSeller(listTop3.get(1));
            setProfileProduct(userSeller,listTop3.get(1));
        }else if(e.getSource() == panelTop3){
            userSeller = product_Dao.selectSeller(listTop3.get(2));
            setProfileProduct(userSeller,listTop3.get(2));
        }

    }


    public void click(MouseEvent e)
    {
        if(e.getSource() == closeInforProduct)
        {
            InformationProduct.setVisible(false);
        }else if(e.getSource() == InformationProduct)
        {
            InformationProduct.setVisible(false);
        }else if(e.getSource() == PageHome_txt)
        {
            startForm.setVisible(true);
            InformationProduct.setVisible(false);
            BanHangForm.setVisible(false);
        }else if(e.getSource() == formPayProduct){
            VoucherForm_pane.setVisible(false);
        }
    }
    public void clickExit(MouseEvent e)
    {
        if(e.getSource() == SearchProducts_Form)
        {
            SearchProducts_Form.setVisible(false);
        }
    }

    public void ProfileUser(MouseEvent e) throws IOException {


        if(Temp.account!=null) {
            String url;
            if(Temp.account.getPhanQuyen().equals("ADMIN"))
            {
                url = "/com/epu/oop/myshop/GUI/AdminForm.fxml";
            }else {
                threadImageV.stop();
                url = "/com/epu/oop/myshop/GUI/ProfileUser.fxml";
            }
            ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),url);
        }else
        {
            if(AlertNotification.showAlertConfirmation("","Bạn chưa đăng nhập, Bạn muốn đăng nhập??")){
                ConverForm.showForm((Stage) ((Node) e.getSource()).getScene().getWindow(),"/com/epu/oop/myshop/GUI/LoginForm.fxml");
            }

        }
    }


    //------------ Bắt đầu form Page Home---------------

    public void SeachProduct(KeyEvent e) {

        InformationProduct.setVisible(false);
        SearchProducts_Form.setVisible(true);
        String key = search_jtxt.getText();
            listproductSearch.clear();

        listproductSearch = product_Dao.SearchProducts(key);
        if(listproductSearch!=null)
            setFormSearchProduct();

        if(key.equals(""))
        {
            grid_Search.getChildren().clear();
            SearchProducts_Form.setVisible(false);
        }

    }


    /*Giai thích:
    Mỗi lần nhập 1 kí tự là sẽ tự động selectByID vè set dữ liệu vào gridpane
    Lấy ví dụ là đã nhập và select được 1 danh sách sản phẩm.Ta sẽ lần lượt duyệt từng sản phẩm trong list và gán nó vào
    trong gridpane. Mỗi lần làm vậy ta sẽ cần FXML để set thông tin hiển thị, và controller để xử lý view đó.
    Controller chỉ có thể set dữ liệu đầy đủ với những thuộc tính đang có trong FXML, nếu gán lung tung sẽ bị lỗi
    Vì mỗi lần truyền dữ liệu từ list đến controller ta cần 2 tham số Listenner và giá trị tại vị trí..
    Sau khi set xong dữ liệu, FXML sẽ chứa thông tin đó để hiển thị, và  GridPane có trách nhiệm chứa FXML (các thông tin)
    đó trên 1 NODE;
    Bên controller ta phải tạo mouseEven cho FXML, mỗi khi nhấn vào sản phẩm bất kì(hay node bất kỳ ) thì controller sẽ thông qua
    sự kiện truyền thông tin(trên node) product đến tất cả những nơi đang khởi tạo, chờ sự kiện để tiếp tục thực thi.
    clickProducts(); sẽ thực hiện các nhiệm vụ set và hiển thị phía sau
     */
    public void setFormSearchProduct() {
        int col = 0;
        int row = 1;
        clickProducts();
        grid_Search.getChildren().clear();

        for(Product prod:listproductSearch)
        {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/epu/oop/myshop/GUI/ItemSearchProduct.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemMarketController item = fxmlLoader.getController();
                item.setDataSearchProduct(myListener_Prod,prod);

                grid_Search.add(anchorPane, col, row++); // (child,column,row)
                // set grid width
                grid_Search.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid_Search.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid_Search.setMaxWidth(Region.USE_PREF_SIZE);

                // set grid height
                grid_Search.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid_Search.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid_Search.setMaxHeight(Region.USE_PREF_SIZE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void defaulValue()
    {
        timeDay_label.setText(App.timeDay);

        Calendar_img.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/Calendar.png")));
        imagUser.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/img1.jpg")));
        img1_BanChay.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconBanNhieuSP.jpg")));
        img2_BanChay.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconBanNhieuSP.jpg")));
        img3_BanChay.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/iconBanNhieuSP.jpg")));

    }




    ThreadImageV threadImageV;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        defaulValue();
        showTop3();

        Temp.Listcategory = listCategory;
        setGiaoDienDanhMuc();

        Rectangle clip = new Rectangle(
                img_ThreadNotification.getFitWidth(), img_ThreadNotification.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        img_ThreadNotification.setClip(clip);

        // snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = img_ThreadNotification.snapshot(parameters, null);

        // remove the rounding clip so that our effect can show through.
        img_ThreadNotification.setClip(null);

        // apply a shadow effect.
        img_ThreadNotification.setEffect(new DropShadow(20, Color.BLACK));

        // store the rounded image in the imageView.
        img_ThreadNotification.setImage(image);
         threadImageV =  new ThreadImageV(img_ThreadNotification);
         threadImageV.start();
    }
}

