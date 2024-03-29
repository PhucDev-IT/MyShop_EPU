package com.epu.oop.myshop.model;

import com.epu.oop.myshop.Dao.Product_Dao;
import com.epu.oop.myshop.Dao.VoucherDao;
import com.epu.oop.myshop.JdbcConnection.ConnectionPool;

import java.io.File;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class CreateSQL {
    public static final String databaseName = "MyShop";
    private static final String url = "jdbc:sqlserver://localhost:1433;";
    public static final String urlConnect =  "jdbc:sqlserver://localhost:1433;databaseName="+databaseName;
    public static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String userName = "sa";

    public static final String password = "123456";


   
    public boolean checkExistDatabase() throws SQLException {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM sys.databases WHERE name = '" + databaseName + "'");
            if (resultSet.next()) {
                System.out.println("Đã tồn tại");
                return true;    //Nếu tồn tại trả về kq không rỗng
            } else {
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
           if(conn!=null)
           {
               conn.close();
           }
        }
    }


//    public void restoreData() throws SQLException {
//        Connection conn = null;
//        String sql = "USE master RESTORE DATABASE YourDatabaseName FROM DISK='D:\\myshop.bak' WITH REPLACE;";
//        try {
//            // Chuyển đổi chế độ tự động commit thành false để bắt đầu một transaction
//            Class.forName(driver);
//            conn = DriverManager.getConnection(url, userName, password);
//            conn.setAutoCommit(false);
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.executeUpdate();
//
//            conn.commit();
//        } catch (SQLException e) {
//            if(conn!=null){
//                conn.rollback();
//                System.out.println("Roll back restore: "+e.getMessage());
//            }
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }finally {
//            conn.setAutoCommit(true);
//            conn.close();
//        }
//    }

    public void autoCreate() throws SQLException, ClassNotFoundException {
        if (createDatabase()) {
            if (creatTable())
                if (createTigger())
                    if (createIndex())
                        if (createProcedure())
                            if (insertData())
                                if (insertDataChild()) {
                                    setDataOnDatabase();
                                    insertVoucher();
                                }
        } else {
            //Xóa database
        }

    }
    public boolean createDatabase() throws SQLException {
        Connection conn = null;
        try{
            // Chuyển đổi chế độ tự động commit thành false để bắt đầu một transaction
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);

            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            String sql = "CREATE DATABASE "+databaseName;

           stmt.execute(sql);
            conn.commit();

            System.out.println("Tạo database thành công");
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            System.out.println("Không thể tạo databse: "+e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(conn!=null)
            {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        return true;
    }

    public boolean creatTable() throws SQLException {
        Statement stm = null;
        int result = 1;
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(urlConnect, userName, password);
            // Chuyển đổi chế độ tự động commit thành false để bắt đầu một transaction
            connection.setAutoCommit(false);
            stm = connection.createStatement();

            stm.execute(TblAccount);
            stm.execute(TblUser);
            stm.execute(TblBank);
            stm.execute(TblCategory);
            stm.execute(TblProduct);
            stm.execute(Tblvoucher);
            stm.execute(TblVoucherUser);
            stm.execute(Tblorder);
            stm.execute(TblOrderDetails);
            stm.execute(TblPaymentHistory);
            stm.execute(TblItemCart);
            stm.execute(tblMessenger);
            connection.commit();

            System.out.println("Tạo table thành công");
        }catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            result = 0;
            System.out.println("Không thể tạo table: "+e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(stm!=null){
                stm.close();
            }
            if(connection!=null)
            {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return result>0;
    }

    public boolean createTigger() throws SQLException {
            Statement stm = null;
            Connection connection = null;
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(urlConnect, userName, password);
                // Chuyển đổi chế độ tự động commit thành false để bắt đầu một transaction
                connection.setAutoCommit(false);
                stm = connection.createStatement();

                stm.execute(TriggerOne);
                stm.execute(TriggerTwo);
                stm.execute(TriggerUpdateVoucher);
                stm.execute(TriggerLockProduct);

                connection.commit();
                System.out.println("Tạo trigger thành công");
            } catch (SQLException e) {
                if (connection != null) {
                    connection.rollback();
                }
                System.out.println("Không thể tạo trigger: ");
                e.printStackTrace();
                return false;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                if (stm != null) {
                    stm.close();
                }
                if(connection!=null)
                {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            }
        return true;
    }
    public boolean createIndex() throws SQLException {

        AtomicBoolean check = new AtomicBoolean(true);
        Statement stm = null;
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(urlConnect, userName, password);
            // Chuyển đổi chế độ tự động commit thành false để bắt đầu một transaction
            connection.setAutoCommit(false);
            stm = connection.createStatement();
            stm.execute(indexUser);
            stm.execute(indexOrder);
            stm.execute(indexPayment);
            stm.execute(indexProduct);
            stm.execute(indexItemCart);
            stm.execute(indexMessenger);
            connection.commit();
            System.out.println("Tạo index thành công");
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
                check.set(false);
            }
            System.out.println("Không thể tạo index: "+e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (stm != null) {
                stm.close();
            }
            if(connection!=null)
            {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return check.get() == true;
    }

    public boolean createProcedure() throws SQLException {

        Statement stm = null;
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(urlConnect,userName,password);
            // Chuyển đổi chế độ tự động commit thành false để bắt đầu một transaction
            connection.setAutoCommit(false);
            stm = connection.createStatement();
            stm.execute(proc_getProductOfPages);
            stm.execute(proc_PaymentHistory);
            stm.execute(proc_SearchProduct);
            stm.execute(proc_selectProd_User);
            connection.commit();
            System.out.println("Tạo Procedure thành công");
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            System.out.println("Không thể tạo Procedure: "+e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (stm != null) {
                stm.close();
            }
            if(connection!=null)
            {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return true;
    }
    //Test dữ liệu tự động

    public boolean insertDataChild() throws SQLException {
        AtomicBoolean check = new AtomicBoolean(true);
        PreparedStatement preUser = null;
        PreparedStatement preProduct = null;
        PreparedStatement preProSeler = null;
        Connection connection = null;
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(urlConnect, userName, password);
            connection.setAutoCommit(false);

            String sqlUser = "INSERT INTO Users(Account_ID,FullName,Email)" +
                    " VALUES (?,?,?)";


            preUser = connection.prepareStatement(sqlUser);
            preUser.setInt(1,1);
            preUser.setString(2,"ADMIN");
            preUser.setString(3,"nguyenvanphuc362003@gmail.com");
            preUser.executeUpdate();

            preUser.setInt(1,2);
            preUser.setString(2,"Nguyễn Văn Phúc");
            preUser.setString(3,"myshop@gmail.com");
            preUser.executeUpdate();

            preUser.setInt(1,3);
            preUser.setString(2,"Hoàng Hải Nam");
            preUser.setString(3,"hainam@gmail.com");
            preUser.executeUpdate();

            preUser.setInt(1,4);
            preUser.setString(2,"Trần Thị Ngọc Trinh");
            preUser.setString(3,"trinhsat@gmail.com");
            preUser.executeUpdate();

            connection.commit();
            System.out.println("Thêm dữ liệu khóa ngoài thành công");
        } catch (SQLException e) {
            if(connection!=null){
                connection.rollback();
            }
            check.set(false);
            System.out.println("Không thêm được dữ liệu: "+e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {

            if(preProduct!=null){
                preProduct.close();
            }

            if(preUser!=null){
                preUser.close();
            }

            if(preProSeler!=null) preProSeler.close();

            if(connection!=null)
            {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return check.get();
    }
    public boolean insertData() throws SQLException {
        PreparedStatement preaccount = null;

        PreparedStatement preCategory = null;
        Connection connection = null;
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(urlConnect, userName, password);
            connection.setAutoCommit(false);

            String sqlAccount = "INSERT INTO Account(UserName,Passwords,PhanQuyen)" +
                    " VALUES (?,?,?)";

            String sqlCategory = "INSERT INTO Category(Category_ID,TenDanhMuc)" +
                    " VALUES (?,?)";

            preaccount = connection.prepareStatement(sqlAccount);
            preaccount.setString(1,"admin");
            preaccount.setString(2,"admin");
            preaccount.setString(3,"ADMIN");
            preaccount.executeUpdate();

            preaccount.setString(1,"myshop@gmail.com");
            preaccount.setString(2,"123456");
            preaccount.setString(3,"Seller");
            preaccount.executeUpdate();

            preaccount.setString(1,"hainam@gmail.com");
            preaccount.setString(2,"123456");
            preaccount.setString(3,"Seller");
            preaccount.executeUpdate();

            preaccount.setString(1,"xinchao@gmail.com");
            preaccount.setString(2,"123456");
            preaccount.setString(3,"Seller");
            preaccount.executeUpdate();


            preCategory = connection.prepareStatement(sqlCategory);
            Category category = new Category();
            for(Map.Entry<String,Integer> entry : category.listCategory.entrySet()) {
                preCategory.setInt(1,entry.getValue());
                preCategory.setString(2,entry.getKey());
                preCategory.executeUpdate();
            }

            connection.commit();
            System.out.println("Tạo bảng khóa chính thành công");
        } catch (SQLException e) {
            if(connection!=null){
                connection.rollback();
            }
            System.out.println("Không thêm được dữ liệu: "+e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {

            if(preaccount!=null){
                preaccount.close();
            }

            if(preCategory!=null){
                preCategory.close();
            }

            if(connection!=null)
            {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return true;
    }

    public void setDataOnDatabase() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(urlConnect, userName, password);
        String[] linkName = {"phone", "Laptop", "may-choi-game", "cham-soc-da", "trang-diem", "Nước hoa", "thực phẩm chức năng",
                "thiết bị y tế", "quan-ao-nam", "giay-nam", "dong-ho", "", "thoi-trang-nu", "tui-xach-nu", "do-ngu-nu", "trang sức nữ", "xe-may"
                , "oto", "", "phu-kien-the-thao", "", "Vali-túi xách", "kính mắt", "", ""};
        Random random = new Random();

        int i=1;
        for(String lastFileName : linkName){

            File folder = new File("src/main/resources/com/epu/oop/myshop/image/Product/"+lastFileName);
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String cutName = fileName.substring(0, fileName.lastIndexOf('.'));
                    String filePath = "/" + file.getPath().substring(file.getPath().indexOf("com")).replace('\\', '/');

                    int sold = random.nextInt(5921)+4;
                    BigDecimal total = new BigDecimal(sold*593.7);
                    Product product = new Product(0,cutName,random.nextInt(10000)+12,total,"Người bán không" +
                            " nói gì",filePath,sold,total.multiply(BigDecimal.valueOf(sold)),i, new User(random.nextInt(3) + 2,""));

                    try {
                        Insert(product,connection);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            System.out.println(i++);
        }

        if (connection != null) {
            connection.close();
        }
    }

    public synchronized boolean Insert(Product t,Connection connection) throws SQLException {
        int check = 0;
        PreparedStatement preProduct = null;
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO Product(TenSP,Quantity,Price,MoTa,SrcImg,Category_ID,Sold,TotalRevenue,Seller_ID)" +
                    " VALUES (?,?,?,?,?,?,?,?,?)";

            preProduct = connection.prepareStatement(sql);

            preProduct.setString(1,t.getTenSP());
            preProduct.setInt(2,t.getQuantity());
            preProduct.setBigDecimal(3,t.getPrice());
            preProduct.setString(4,t.getMoTa());
            preProduct.setString(5,t.getSrcImg());
            preProduct.setInt(6,t.getCategory());
            preProduct.setInt(7,t.getSold());
            preProduct.setBigDecimal(8,t.getTotalRevenue());
            preProduct.setInt(9,t.getUser().getID());
            check = preProduct.executeUpdate();

            connection.commit();
        }catch (SQLException e) {
            if(connection!=null){
                connection.rollback();
                System.out.println("Roll Back: "+e.getMessage());
            }
        }finally {
            if(preProduct!=null)    preProduct.close();

            connection.setAutoCommit(true);

        }
        return check>0;
    }

    private static final SecureRandom random = new SecureRandom();
    public void insertVoucher()
    {
        String result;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        VoucherDao voucherDao = VoucherDao.getInstance(connectionPool);
        VoucherModel vc;
        for(int i=1;i<=12;i++)
        {
            byte[] bytes = new byte[6];
            random.nextBytes(bytes);
            result = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, 6).toUpperCase();
            result = result.replace("_", ""); // loại bỏ ký tự '_'

            if(i%2==0){
                 vc = new VoucherModel(result,i*3,null,i*2,"Tặng bạn mã voucher nhé","/com/epu/oop/myshop/image/voucher.png",
                        new Date(System.currentTimeMillis()),new Date(2026-1900,9,12));
            }else {
                 vc = new VoucherModel(result,0,new BigDecimal(i*10000),i*3,"Tặng bạn mã voucher nhé","/com/epu/oop/myshop/image/voucher.png",
                        new Date(System.currentTimeMillis()),new Date(2026-1900,9,12));
            }
            voucherDao.Insert(vc);
        }
    }

    private final String TblAccount = "CREATE TABLE Account ("+
            " ID INT PRIMARY KEY IDENTITY," +
            " UserName VARCHAR(40) UNIQUE," +
            " Passwords VARCHAR(30)," +
            " Currency DECIMAL DEFAULT (0)," +
            " Activity VARCHAR(8) DEFAULT ('ON')," +
            " PhanQuyen NVARCHAR(8) DEFAULT ('Member') );";

    private final  String TblUser = "CREATE TABLE Users " +
            "( " +
            " Account_ID INT, " +
            " PRIMARY KEY(Account_ID)," +
            " FOREIGN KEY(Account_ID) REFERENCES Account(ID)," +
            " FullName NVARCHAR(100)," +
            " Gender NVARCHAR(10) DEFAULT N'Khác'," +
            " DateOfBirth DATE, " +
            " HomeTown NVARCHAR(500), " +
            " CCCD VARCHAR(20), " +
            " Email VARCHAR(30) NOT NULL UNIQUE," +
            " Phone VARCHAR(15), " +
            " SrcAvatar NVARCHAR(400) " +
            ");";
    private final String TblBank = "CREATE TABLE Bank " +
            "( " +
            " SoTaiKhoan VARCHAR(20) PRIMARY KEY, " +
            " TenNH NVARCHAR(200), " +
            " TenChiNhanh NVARCHAR(100), " +
            " SoCCCD VARCHAR(25),"+
            " ChuSoHuu NVARCHAR(100), " +
            " Users_ID INT," +
            " FOREIGN KEY (Users_ID) REFERENCES Users(Account_ID) " +
            ");";

    private final String TblCategory =
            "CREATE TABLE Category " +
                    "( " +
                    " Category_ID INT PRIMARY KEY," +
                    " TenDanhMuc NVARCHAR(50) " +
                    ");";
    private final String TblProduct = "CREATE TABLE Product " +
            "( " +
            " MaSP INT PRIMARY KEY IDENTITY," +
            " TenSP NVARCHAR(300)," +
            " Quantity INT, " +
            " Price DECIMAL, " +
            " MoTa NVARCHAR(MAX) DEFAULT (N'Người bán không viết gì')," +
            " SrcImg NVARCHAR(1000)," +
            " Activity VARCHAR(10) DEFAULT 'ON', " +
            " Sold FLOAT, " +
            " TotalRevenue DECIMAL DEFAULT 0, " +
            " Category_ID INT, " +
            " FOREIGN KEY(Category_ID) REFERENCES Category(Category_ID), " +
            " Seller_ID INT FOREIGN KEY REFERENCES Users(Account_ID)" +
            ");";

    private final String Tblvoucher = "CREATE TABLE Voucher" +
            "(" +
            " MaVoucher VARCHAR(20) PRIMARY KEY," +
            " TiLeGiamGia FLOAT," +
            " SotienGiamGia DECIMAL," +
            " SoLuong INT," +
            " NoiDung NVARCHAR(500)," +
            " ImgVoucher NVARCHAR(500)," +
            " NgayBatDau DATE," +
            " NgayKetThuc DATE" +
            ");";
    private final String TblVoucherUser = "CREATE TABLE VoucherUser " +
            "(" +
            " MaVoucher VARCHAR(20)," +
            " FOREIGN KEY(MaVoucher) REFERENCES Voucher(MaVoucher)," +
            " ID_User INT, " +
            " FOREIGN KEY(ID_User) REFERENCES Users(Account_ID)," +
            " PRIMARY KEY(MaVoucher,ID_User)" +
            ");";
    private final String Tblorder = "CREATE TABLE orders " +
            "( " +
            " Order_ID INT PRIMARY KEY IDENTITY," +
            " NgayLapHD DATE, " +
            " TongTien DECIMAL, " +
            " MaVoucher VARCHAR(20), " +
            " FOREIGN KEY(MaVoucher) REFERENCES Voucher(MaVoucher)," +
            " ThanhTien DECIMAL, " +
            " Users_ID INT," +
            " FOREIGN KEY(Users_ID) REFERENCES Users(Account_ID)" +
            ");";
    private final String TblOrderDetails = "CREATE TABLE OrderDetails " +
            "( " +
            " Product_ID INT," +
            " FOREIGN KEY (Product_ID) REFERENCES Product(MaSP)," +
            " Order_ID INT," +
            " FOREIGN KEY(Order_ID) REFERENCES Orders(Order_ID)," +
            " Quantity FLOAT," +
            " Price DECIMAL," +
            " PRIMARY KEY (Order_ID, Product_ID)" +
            ");";
    private final String TblPaymentHistory = "CREATE TABLE PaymentHistory " +
            "( " +
            " ID INT PRIMARY KEY IDENTITY," +
            " TenGiaoDich NVARCHAR(20)," +
            " NoiDung NVARCHAR(50)," +
            " SoTien DECIMAL," +
            " NgayGiaoDich DATE," +
            " SrcImgIcon NVARCHAR(500)," +
            " Users_ID INT," +
            " FOREIGN KEY(Users_ID) REFERENCES Users(Account_ID)," +
            " Account_ID INT," +
            " FOREIGN KEY(Account_ID) REFERENCES Account(ID)" +
            ");";

    private final String TblItemCart = "CREATE TABLE itemCart " +
            "( " +
            " ID_Cart INT FOREIGN KEY REFERENCES Account(ID), " +
            " Product_ID INT  FOREIGN KEY REFERENCES Product(MaSP)," +
            " Quantity INT," +
            " PRIMARY KEY(ID_Cart,Product_ID)" +
            ")";


    private final String tblMessenger = "CREATE TABLE Messenger " +
            "( " +
            " ID INT PRIMARY KEY IDENTITY, " +
            " Sender NVARCHAR(100), " +
            " Content NVARCHAR(1000), " +
            " Statuss BIT, " +
            " SentDate DATE," +
            " SrcIcon NVARCHAR(500)," +
            " Account_ID INT FOREIGN KEY REFERENCES Account(ID) " +
            " )";

    //------------- TẠO CHỈ MỤC - TĂNG TỐC TRUY VẤN
    String indexProduct ="CREATE INDEX idx_MaSP ON Product(MaSP); " +
                        "CREATE INDEX idx_Category_ID ON Product(Category_ID); " +
                         "CREATE INDEX idx_Activity ON Product(Activity);" +
                        "CREATE INDEX idx_TotalRevenue ON Product(TotalRevenue)" +
                        " CREATE INDEX idx_Seller_ID ON Product(Seller_ID)";

    String indexUser = "CREATE INDEX idx_Account_ID ON Users(Account_ID)";

    String indexOrder = "CREATE INDEX idx_OrderID ON Orders(Order_ID) " +
            "CREATE INDEX idx_UsersID ON Orders(Users_ID) " +
            "CREATE INDEX idx_NgayLapHD ON Orders(NgayLapHD)";

    String indexPayment = "CREATE INDEX idx_IDPayment ON PaymentHistory(ID) " +
            "CREATE INDEX idx_Users_ID ON PaymentHistory(Users_ID) " +
            "CREATE INDEX idx_Account_ID ON PaymentHistory(Account_ID)";


    private final String indexItemCart = "CREATE INDEX idx_id_Cart ON itemCart(id_Cart) " +
            " CREATE INDEX idx_product_ID ON itemCart(Product_ID)";


    private final String indexMessenger = "CREATE INDEX idx_Status ON Messenger(Statuss)" +
            " CREATE INDEX idx_sentDate ON Messenger(SentDate) " +
            "CREATE INDEX idx_Account_ID ON Messenger(Account_ID)";
    //---------------------------- TRIGGER ---------------------------------------
    private final String TriggerOne = "CREATE TRIGGER TRIG_Update_MoneySeller ON OrderDetails" +
            " FOR INSERT " +
            " AS" +
            " BEGIN" +
            "    Update Account" +
            " SET Currency = Currency+(SELECT SUM(Quantity*Price) FROM inserted)" +
            " where Account.ID = (SELECT p.Seller_ID FROM inserted cthd JOIN Product p" +
            "                       ON p.MaSP = cthd.Product_ID) " +
            "END";

    private final String TriggerTwo = "CREATE TRIGGER TRIG_Update_Product ON OrderDetails " +
            "FOR INSERT " +
            "AS " +
            "BEGIN " +
            "    UPDATE Product" +
            "    SET Quantity = Quantity - (SELECT Quantity FROM inserted WHERE Product_ID = Product.MaSP)" +
            "    WHERE EXISTS (SELECT * FROM inserted WHERE inserted.Product_ID = Product.MaSP)" +
            "    " +
            "    UPDATE Product" +
            "    SET Sold += (SELECT (Quantity) FROM inserted WHERE inserted.Product_ID = Product.MaSP)" +
            "    WHERE EXISTS (SELECT * FROM inserted WHERE inserted.Product_ID = Product.MaSP)" +
            "    " +
            "    UPDATE Product" +
            "    SET TotalRevenue += (SELECT SUM(Quantity*Price) FROM inserted WHERE inserted.Product_ID = Product.MaSP)" +
            "    WHERE EXISTS (SELECT * FROM inserted WHERE inserted.Product_ID = Product.MaSP)" +
            "END";


    private final String TriggerUpdateVoucher = "CREATE TRIGGER Trig_UpdateVoucher ON Orders " +
            "FOR INSERT " +
            "AS " +
            "BEGIN " +
            " IF exists (SELECT MaVoucher FROM inserted) " +
            " BEGIN " +
            " UPDATE Voucher " +
            "            SET SoLuong = SoLuong-1 " +
            "             WHERE Voucher.MaVoucher = (SELECT MaVoucher From inserted) " +
            " END " +
            "END";


    //Khi bkhoasoa tài khoản thì các san phẩm đang baán sẽ bị thay đổi trạng thái để không được sellec
    private final String TriggerLockProduct = "CREATE TRIGGER Trig_LockProduct_WhenLockAccount ON Account " +
            "FOR UPDATE " +
            "AS " +
            "BEGIN " +
            " DECLARE @Status NVARCHAR(6) " +
            " IF EXISTS (SELECT ID FROM inserted WHERE Activity = 'ON') " +
            "   SET @Status = 'ON' " +
            " ELSE" +
            "   SET @Status = 'LOCK' " +
            "    " +
            " UPDATE PRODUCT " +
            " SET Activity = @Status " +
            " WHERE Seller_ID = (SELECT ID FROM inserted) " +
            "END";

    //------------------------- STORE PROCEDURE---------------------------------------
    private final String proc_getProductOfPages = "CREATE PROC proc_getProductOfPages " +
            " @Category_ID INT," +
            " @Offset int, " +
            " @Limit int" +
            " AS " +
            " BEGIN " +
            " SELECT p.*, u.FullName FROM Product p JOIN Users u ON p.Seller_ID = u.Account_ID " +
            " WHERE Category_ID = @Category_ID " +
            " AND p.Activity = 'ON'" +
            " ORDER BY TotalRevenue DESC" +
            " OFFSET @Offset ROWS FETCH NEXT @Limit ROWS ONLY " +
            " END";

    private final String proc_SearchProduct = "CREATE PROC proc_SearchProduct " +
            " @nameProduct NVARCHAR(40), " +
            " @Offset int, " +
            " @Limit int" +
            " AS " +
            " BEGIN " +
            " SELECT p.*, u.FullName FROM Product p  JOIN Users u ON p.Seller_ID = u.Account_ID " +
            " WHERE p.TenSP LIKE @nameProduct " +
            " AND p.Activity = 'ON' " +
            " ORDER BY TotalRevenue DESC" +
            " OFFSET @Offset ROWS FETCH NEXT @Limit ROWS ONLY " +
            " END";

    private final String proc_selectProd_User = "CREATE PROC proc_getProduct_ofSeller " +
            " @User_ID INT, " +
            " @Offset int, " +
            " @Limit int " +
            " AS " +
            " BEGIN " +
            " SELECT * FROM Product " +
            " WHERE Seller_ID = @User_ID " +
            " AND Activity = 'ON' " +
            " ORDER BY TotalRevenue DESC" +
            " OFFSET @Offset ROWS FETCH NEXT @Limit ROWS ONLY " +
            " END";

    private final String proc_PaymentHistory = "CREATE PROC proc_PaymentHistory " +
            " @User_ID INT, " +
            " @Offset int, " +
            " @Limit int " +
            " AS " +
            " BEGIN " +
            " SELECT " +
            " PaymentHistory.*, " +
            " CASE WHEN Account_ID IS NULL THEN 'Unknown' " +
            " ELSE (SELECT FullName FROM Users WHERE Account_ID=PaymentHistory.Account_ID) END AS NguoiNhan " +
            " FROM PaymentHistory " +
            " WHERE Users_ID = @User_ID OR Account_ID = @User_ID " +
            " ORDER BY NgayGiaoDich DESC " +
            " OFFSET @Offset ROWS FETCH NEXT @Limit ROWS ONLY " +
            " END";
}
