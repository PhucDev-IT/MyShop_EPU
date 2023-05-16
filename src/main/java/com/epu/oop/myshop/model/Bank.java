package com.epu.oop.myshop.model;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



public class Bank implements Serializable {

    private String SoTaiKhoan;

    private String tenChiNhanh;

    private String soCCCD;
    private String TenNH;

    public static final Map<String,String> listBanks = pushBank();

    private String ChuSoHuu;

    private User user;


    public Bank(){}
    public Bank(User u)
    {
        this.user = u;
    }

    public Bank(String soTaiKhoan, String tenChiNhanh, String soCCCD, String tenNH, String chuSoHuu, User user) {
        SoTaiKhoan = soTaiKhoan;
        this.tenChiNhanh = tenChiNhanh;
        this.soCCCD = soCCCD;
        TenNH = tenNH;
        ChuSoHuu = chuSoHuu;
        this.user = user;
    }

    public String getSoTaiKhoan() {
        return SoTaiKhoan;
    }


    public void setSoTaiKhoan(String soTaiKhoan) {
        SoTaiKhoan = soTaiKhoan;
    }


    public String getTenNH() {
        return TenNH;
    }


    public void setTenNH(String tenNH) {
        TenNH = tenNH;
    }


    public String getChuSoHuu() {
        return ChuSoHuu;
    }


    public void setChuSoHuu(String chuSoHuu) {
        ChuSoHuu = chuSoHuu;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public String getTenChiNhanh() {
        return tenChiNhanh;
    }

    public void setTenChiNhanh(String tenChiNhanh) {
        this.tenChiNhanh = tenChiNhanh;
    }

    public String getSoCCCD() {
        return soCCCD;
    }

    public void setSoCCCD(String soCCCD) {
        this.soCCCD = soCCCD;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "SoTaiKhoan='" + SoTaiKhoan + '\'' +
                ", tenChiNhanh='" + tenChiNhanh + '\'' +
                ", soCCCD='" + soCCCD + '\'' +
                ", TenNH='" + TenNH + '\'' +
                ", ChuSoHuu='" + ChuSoHuu + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(ChuSoHuu, SoTaiKhoan, TenNH, user);
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bank other = (Bank) obj;
        return Objects.equals(ChuSoHuu, other.ChuSoHuu) && Objects.equals(SoTaiKhoan, other.SoTaiKhoan)
                && Objects.equals(TenNH, other.TenNH) && Objects.equals(user, other.user);
    }



    public static final Map pushBank(){
        Map<String,String> listBank = new HashMap<>();
        listBank.put("BIDV", "Đầu tư và Phát triển Việt Nam");
        listBank.put("VPBank", "Việt Nam Thịnh Vượng");
        listBank.put("VietinBank", "Công thương Việt Nam");
        listBank.put("Vietcombank", "Ngoại Thương Việt Nam");
        listBank.put("MB", "Quân Đội");
        listBank.put("Techcombank", "Kỹ Thương");
        listBank.put("Agribank", "NN&PT Nông thôn Việt Nam");
        listBank.put("ACB", "Á Châu");
        listBank.put("SHB", "Sài Gòn – Hà Nội");
        listBank.put("VIB", "Quốc Tế ");
        listBank.put("HDBank", "Phát triển Thành phố Hồ Chí Minh");
        listBank.put("SeABank", "Đông Nam Á");
        listBank.put("VBSP", "Chính sách xã hội Việt Nam");
        listBank.put("Sacombank", "Sài Gòn Thương Tín");
        listBank.put("LienVietPostBank", "Bưu điện Liên Việt");
        listBank.put("MSB", "Hàng Hải");
        listBank.put("SCB", "Sài Gòn");
        listBank.put("VDB", "Phát triển Việt Nam");
        listBank.put("OCB", "Phương Đông");
        listBank.put("Eximbank", "Xuất Nhập Khẩu");
        listBank.put("TPBank", "Tiên Phong");
        listBank.put("PVcomBank", "Đại Chúng Việt Nam");
        listBank.put("Bac A Bank", "TMCP Bắc Á");
        listBank.put("Woori", "Woori Việt Nam");
        listBank.put("HSBC", "HSBC Việt Nam");
        listBank.put("SCBVL", "Standard Chartered Việt Nam");
        listBank.put("PBVN", "Public Bank Việt Nam");
        listBank.put("NCB", "Quốc dân");
        listBank.put("VietABank", "Việt Á");
        listBank.put("DongA Bank", "Đông Á");
        listBank.put("Vietbank", "Việt Nam Thương Tín");
        listBank.put("Nam A Bank", "Nam Á");
        listBank.put("OceanBank", "Đại Dương");
        listBank.put("CIMB", "CIMB Việt Nam");
        listBank.put("SAIGONBANK", "Sài Gòn Công Thương");
        listBank.put("BAOVIET Bank", "Bảo Việt");
        return listBank;
    }


}
