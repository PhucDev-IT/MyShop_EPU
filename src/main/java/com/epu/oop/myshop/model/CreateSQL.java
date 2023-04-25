package com.epu.oop.myshop.model;

import java.sql.*;

public class CreateSQL {
    public static final String databaseName = "MyShop";

    //public static final String url = "jdbc:mysql://localhost:3306/";
   // public static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:sqlserver://localhost:1433;";

    public static final String urlConnect =  "jdbc:sqlserver://localhost:1433;databaseName="+databaseName;
    public static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String userName = "sa";
    public static final String password = "123456";

    private boolean check = false;


   
    public boolean checkExistDatabase(){
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM sys.databases WHERE name = '" + databaseName + "'");
            if (resultSet.next()) {
                return true;    //Nếu tồn tại trả về kq không rỗng
            } else {
                return false;
            }
        } catch (SQLException e) {
            // Error occurred while connecting to the database
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void restoreData() throws SQLException {
        Connection conn = null;
        String sql = "USE master RESTORE DATABASE YourDatabaseName FROM DISK='D:\\myshop.bak' WITH REPLACE;";
        try {
            // Chuyển đổi chế độ tự động commit thành false để bắt đầu một transaction
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if(conn!=null){
                conn.rollback();
                System.out.println("Roll back restore: "+e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public boolean createDatabase() throws SQLException {
        Connection conn = null;
        try{
            // Chuyển đổi chế độ tự động commit thành false để bắt đầu một transaction
            Class.forName(driver);
            conn = DriverManager.getConnection(url,userName,password);
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            String sql = "CREATE DATABASE "+databaseName;

            stmt.executeUpdate(sql);
            conn.commit();
            System.out.println("Tạo database thành công");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            if (conn != null) {
                conn.rollback();
            }
            System.out.println("Không thể tạo databse: "+e.getMessage());
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
        return false;
    }
//
//    private void creatTable() throws SQLException, IOException {
//        if(!check){
//            return;
//        }
//        Connection conn = null;
//        Statement stm = null;
//        try {
//            Class.forName(driver);
//            conn = DriverManager.getConnection(urlConnect,userName,password);
//
//            // Chuyển đổi chế độ tự động commit thành false để bắt đầu một transaction
//            conn.setAutoCommit(false);
//            stm = conn.createStatement();
//
//            stm.executeUpdate(TblAccount);
//            stm.executeUpdate(TblUser);
//            stm.executeUpdate(TblBank);
//            stm.executeUpdate(TblCategory);
//            stm.executeUpdate(TblProduct);
//            stm.executeUpdate(TblProductSeller);
//            stm.executeUpdate(Tblvoucher);
//            stm.executeUpdate(TblVoucherUser);
//            stm.executeUpdate(Tblorder);
//            stm.executeUpdate(TblOrderDtails);
//            stm.executeUpdate(TblPaymentHistory);
//
//            conn.commit();
//
//            System.out.println("Tạo table thành công");
//        }catch (SQLException | ClassNotFoundException e) {
//            if (conn != null) {
//                conn.rollback();
//            }
//            check = false;
//            System.out.println("Không thể tạo table: "+e.getMessage());
//        } finally {
//            if(stm!=null){
//                stm.close();
//            }
//            conn.setAutoCommit(true);
//            conn.close();
//        }
//
//    }
//
//    public void createTigger() throws SQLException {
//        if(!check){
//            return;
//        }
//        Connection conn = null;
//            Statement stm = null;
//            try {
//                Class.forName(driver);
//                conn = DriverManager.getConnection(urlConnect,userName,password);
//                // Chuyển đổi chế độ tự động commit thành false để bắt đầu một transaction
//                conn.setAutoCommit(false);
//                stm = conn.createStatement();
//
//                stm.executeUpdate(TriggerOne);
//                stm.executeUpdate(TriggerTwo);
//                stm.executeUpdate(TriggerThree);
//                conn.commit();
//                System.out.println("Tạo trigger thành công");
//            } catch (SQLException e) {
//                if (conn != null) {
//                    conn.rollback();
//                    check = false;
//                }
//                System.out.println("Không thể tạo trigger: "+e.getMessage());
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            } finally {
//                if (stm != null) {
//                    stm.close();
//                }
//                conn.setAutoCommit(true);
//                conn.close();
//            }
//
//    }
//
//    //Test dữ liệu tự động
//
//    public void insertDataChild() throws SQLException {
//        if(!check){
//            return;
//        }
//        Connection conn = null;
//        PreparedStatement preUser = null;
//        PreparedStatement preProduct = null;
//        PreparedStatement preProSeler = null;
//        try{
//            conn = ConnectionPool.getConnection();
//            conn.setAutoCommit(false);
//
//            String sqlUser = "INSERT INTO Users(Account_ID,FullName,Email)" +
//                    " VALUES (?,?,?)";
//
//            String sqlProduct = "INSERT INTO Product(TenSP,SoLuong,DonGia,MoTa,SrcImg,Category_ID)" +
//                    " VALUES (?,?,?,?,?,?)";
//            String sqlProdSeller = "INSERT INTO ProductSeller (Product_ID,Users_ID) " +
//                    "VALUES (?,?)";
//
//            preUser = conn.prepareStatement(sqlUser);
//            preUser.setInt(1,1);
//            preUser.setString(2,"ADMIN");
//            preUser.setString(3,"nguyenvanphuc362003@gmail.com");
//            preUser.executeUpdate();
//
//            preUser.setInt(1,2);
//            preUser.setString(2,"Nguyễn Văn Phúc");
//            preUser.setString(3,"myshop@gmail.com");
//            preUser.executeUpdate();
//
//            preUser.setInt(1,3);
//            preUser.setString(2,"Hoàng Hải Nam");
//            preUser.setString(3,"hainam@gmail.com");
//            preUser.executeUpdate();
//
//            preUser.setInt(1,4);
//            preUser.setString(2,"Trần Thị Ngọc Trinh");
//            preUser.setString(3,"trinhsat@gmail.com");
//            preUser.executeUpdate();
//
//            conn.commit();
//            System.out.println("Tạo bảng khóa ngoại thành công");
//        } catch (SQLException e) {
//            if(conn!=null){
//                conn.rollback();
//                check = false;
//            }
//            System.out.println("Không thêm được dữ liệu: "+e.getMessage());
//        }finally {
//
//            if(preProduct!=null){
//                preProduct.close();
//            }
//
//            if(preUser!=null){
//                preUser.close();
//            }
//
//            if(preProSeler!=null) preProSeler.close();
//
//            conn.setAutoCommit(true);
//            conn.close();
//        }
//    }
//    public void insertData() throws SQLException {
//        if(!check){
//            return;
//        }
//        Connection conn = null;
//        PreparedStatement preaccount = null;
//
//        PreparedStatement preCategory = null;
//
//        try{
//            conn = ConnectionPool.getConnection();
//            conn.setAutoCommit(false);
//
//            String sqlAccount = "INSERT INTO Account(UserName,Passwords,PhanQuyen)" +
//                    " VALUES (?,?,?)";
//
//            String sqlCategory = "INSERT INTO Category(Category_ID,TenDanhMuc)" +
//                    " VALUES (?,?)";
//
//            preaccount = conn.prepareStatement(sqlAccount);
//            preaccount.setString(1,"admin");
//            preaccount.setString(2,"admin");
//            preaccount.setString(3,"ADMIN");
//            preaccount.executeUpdate();
//
//            preaccount.setString(1,"myshop@gmail.com");
//            preaccount.setString(2,"123456");
//            preaccount.setString(3,"Member");
//            preaccount.executeUpdate();
//
//            preaccount.setString(1,"hainam@gmail.com");
//            preaccount.setString(2,"123456");
//            preaccount.setString(3,"Member");
//            preaccount.executeUpdate();
//
//            preaccount.setString(1,"xinchao@gmail.com");
//            preaccount.setString(2,"123456");
//            preaccount.setString(3,"Member");
//            preaccount.executeUpdate();
//
//
//            preCategory = conn.prepareStatement(sqlCategory);
//            Category category = new Category();
//            for(Map.Entry<String,Integer> entry : category.listCategory.entrySet()) {
//                preCategory.setInt(1,entry.getValue());
//                preCategory.setString(2,entry.getKey());
//                preCategory.executeUpdate();
//            }
//
//            conn.commit();
//            System.out.println("Tạo bảng khóa chính thành công");
//        } catch (SQLException e) {
//            if(conn!=null){
//                conn.rollback();
//                check = false;
//            }
//            System.out.println("Không thêm được dữ liệu: "+e.getMessage());
//        }finally {
//
//            if(preaccount!=null){
//                preaccount.close();
//            }
//
//            if(preCategory!=null){
//                preCategory.close();
//            }
//
//            conn.setAutoCommit(true);
//            conn.close();
//        }
//    }
//
//    public void setDataOnDatabase(){
//        if(!check){
//            return;
//        }
//        Product_Dao productDao = Product_Dao.getInstance();
//        Random random = new Random();
//        for (int i = 1; i < 10000; i++) {
//            Product product = new Product("Sản phẩm " + i, (random.nextInt(9200) + 1), BigDecimal.valueOf(123 * i), "Không nói gì",
//                    "/com/epu/oop/myshop/image/Product/product1.png", random.nextInt(24) + 1, new User(random.nextInt(3) + 2));
//            try {
//                productDao.Insert(product);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(i);
//        }
//    }


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
            " FullName NVARCHAR(40)," +
            " Gender NVARCHAR(6)," +
            " DateOfBirth DATE, " +
            " HomeTown NVARCHAR(300), " +
            " CCCD VARCHAR(20), " +
            " Email VARCHAR(30) NOT NULL UNIQUE," +
            " Phone VARCHAR(15), " +
            " SrcAvatar VARCHAR(300) " +
            ");";
    private final String TblBank = "CREATE TABLE Bank " +
            "( " +
            " SoTaiKhoan VARCHAR(20) PRIMARY KEY, " +
            " TenNH NVARCHAR(20), " +
            " ChuSoHuu NVARCHAR(50), " +
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
            " TenSP NVARCHAR(40)," +
            " Quantity INT, " +
            " Price DECIMAL, " +
            " MoTa NVARCHAR(500) DEFAULT (N'Người bán không viết gì')," +
            " SrcImg VARCHAR(400)," +
            " Activity VARCHAR(10) DEFAULT 'ON', " +
            " Sold FLOAT, " +
            " TotalRevenue DECIMAL, " +
            " Category_ID INT, " +
            " FOREIGN KEY(Category_ID) REFERENCES Category(Category_ID) " +
            ");";
    private final String TblProductSeller = "CREATE TABLE ProductSeller " +
            "( " +
            " Product_ID INT," +
            " Users_ID INT," +
            " PRIMARY KEY(Product_ID,Users_ID)," +
            " FOREIGN KEY(Product_ID) REFERENCES Product(MaSP)," +
            " FOREIGN KEY(Users_ID) REFERENCES Users(Account_ID)" +
            ");";

    private final String Tblvoucher = "CREATE TABLE Voucher" +
            "(" +
            " MaVoucher VARCHAR(10) PRIMARY KEY," +
            " TiLeGiamGia FLOAT," +
            " SotienGiamGia DECIMAL," +
            " SoLuong INT," +
            " NoiDung NVARCHAR(300)," +
            " ImgVoucher VARCHAR(300)," +
            " NgayThem DATE," +
            " NgayKetThuc DATE" +
            ");";
    private final String TblVoucherUser = "CREATE TABLE VoucherUser " +
            "(" +
            " MaVoucher VARCHAR(10)," +
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
            " MaVoucher VARCHAR(10), " +
            " FOREIGN KEY(MaVoucher) REFERENCES Voucher(MaVoucher)," +
            " ThanhTien DECIMAL, " +
            " Users_ID INT," +
            " FOREIGN KEY(Users_ID) REFERENCES Users(Account_ID)" +
            ");";
    private final String TblOrderDtails = "CREATE TABLE OrderDetails " +
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
            " SrcImgIcon VARCHAR(300)," +
            " Users_ID INT," +
            " FOREIGN KEY(Users_ID) REFERENCES Users(Account_ID)," +
            " Account_ID INT," +
            " FOREIGN KEY(Account_ID) REFERENCES Account(ID)" +
            ");";

    //------------- TẠO CHỈ MỤC - TĂNG TỐC TRUY VẤN
    String indexProduct ="CREATE INDEX idx_MaSP ON Product(MaSP); " +
                        "CREATE INDEX idx_Category_ID ON Product(Category_ID); " +
                         "CREATE INDEX idx_Activity ON Product(Activity);";

    String indexUser = "CREATE INDEX idx_Account_ID ON Users(Account_ID)";

    String indexOrder = "CREATE INDEX idx_OrderID ON Orders(Order_ID) " +
            "CREATE INDEX idx_UsersID ON Orders(Users_ID) " +
            "CREATE INDEX idx_NgayLapHD ON Orders(NgayLapHD)";

    String indexPayment = "CREATE INDEX idx_IDPayment ON PaymentHistory(ID) " +
            "CREATE INDEX idx_Users_ID ON PaymentHistory(Users_ID) " +
            "CREATE INDEX idx_Account_ID ON PaymentHistory(Account_ID)";


    private final String TriggerOne = "CREATE TRIGGER TRIG_Update_MoneySeller ON OrderDetails" +
            " FOR INSERT " +
            " AS" +
            " BEGIN" +
            "    Update Account" +
            " SET Currency = Currency+(SELECT SUM(Quantity*Price) FROM inserted)" +
            " where Account.ID = (SELECT ps.Users_ID FROM inserted cthd JOIN Product p" +
            "                       ON p.MaSP = cthd.Product_ID " +
            "                       JOIN ProductSeller ps " +
            "                       ON p.MaSP = ps.Product_ID) " +
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
            "    SET Sold = (SELECT SUM(Quantity) FROM OrderDetails WHERE Product_ID = Product.MaSP)" +
            "    WHERE EXISTS (SELECT * FROM inserted WHERE inserted.Product_ID = Product.MaSP)" +
            "    " +
            "    UPDATE Product" +
            "    SET TotalRevenue = (SELECT SUM(Quantity*Price) FROM OrderDetails WHERE Product_ID = Product.MaSP)" +
            "    WHERE EXISTS (SELECT * FROM inserted WHERE inserted.Product_ID = Product.MaSP)" +
            "END";

    private final String TriggerThree = "CREATE TRIGGER TRIG_Update_MoneyCustomer ON Orders " +
            "FOR INSERT " +
            "AS " +
            "BEGIN " +
            "    UPDATE Account " +
            "    SET Currency = Currency - (SELECT ThanhTien FROM inserted) " +
            "    WHERE Account.ID = (SELECT Users_ID FROM inserted) " +
            "END";

    private final String proc_getProductOfPages = "CREATE PROC proc_getProductOfPages " +
            " @Category_ID INT," +
            " @Offset int, " +
            " @Limit int" +
            "AS " +
            "BEGIN " +
            " SELECT p.*, u.FullName FROM Product p JOIN ProductSeller ps " +
            " ON p.MaSP = ps.Product_ID " +
            " JOIN Users u ON ps.Users_ID = u.Account_ID " +
            " WHERE Category_ID = @Category_ID " +
            " AND p.Activity = 'ON'" +
            " ORDER BY MaSP" +
            " OFFSET @Offset ROWS FETCH NEXT @Limit ROWS ONLY " +
            " END";

    private final String proc_SearchProduct = "CREATE PROC proc_SearchProduct " +
            " @nameProduct NVARCHAR(40), " +
            " @Offset int, " +
            " @Limit int" +
            " AS " +
            " BEGIN " +
            " SELECT p.*, u.FullName FROM Product p JOIN ProductSeller ps " +
            " ON p.MaSP = ps.Product_ID " +
            " JOIN Users u ON ps.Users_ID = u.Account_ID " +
            " WHERE p.TenSP LIKE @nameProduct " +
            " AND p.Activity = 'ON' " +
            " ORDER BY MaSP " +
            " OFFSET @Offset ROWS FETCH NEXT @Limit ROWS ONLY " +
            " END";

    private final String proc_selectProd_User = "CREATE PROC prcd_getProduct_ofSeller " +
            " @User_ID INT, " +
            " @Offset int, " +
            " @Limit int " +
            " AS " +
            " BEGIN " +
            " SELECT Product.* FROM Product INNER JOIN ProductSeller  " +
            " ON Product.MaSP = ProductSeller.Product_ID " +
            " AND ProductSeller.Users_ID = @User_ID " +
            " AND Product.Activity = 'ON' " +
            " ORDER BY TotalRevenue " +
            " OFFSET @Offset ROWS FETCH NEXT @Limit ROWS ONLY " +
            " END";

    private final String proc_PaymentHistory = "CREATE PROC pcd_PaymentHistory " +
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
