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

    public static Map<String,String> listBanks = new HashMap<>();

    private String ChuSoHuu;

    private User user;


    public Bank() {pushBank();}


    public Bank(String soTaiKhoan, String tenNH, String chuSoHuu, User user) {
        SoTaiKhoan = soTaiKhoan;
        TenNH = tenNH;
        ChuSoHuu = chuSoHuu;
        this.user = user;
    }
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
        return "Bank [SoTaiKhoan=" + SoTaiKhoan + ", TenNH=" + TenNH + ", ChuSoHuu=" + ChuSoHuu + "]";
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



    public static final void pushBank(){
        listBanks.put("BIDV", "Đầu tư và Phát triển Việt Nam");
        listBanks.put("VPBank", "Việt Nam Thịnh Vượng");
        listBanks.put("VietinBank", "Công thương Việt Nam");
        listBanks.put("Vietcombank", "Ngoại Thương Việt Nam");
        listBanks.put("MB", "Quân Đội");
        listBanks.put("Techcombank", "Kỹ Thương");
        listBanks.put("Agribank", "NN&PT Nông thôn Việt Nam");
        listBanks.put("ACB", "Á Châu");
        listBanks.put("SHB", "Sài Gòn – Hà Nội");
        listBanks.put("VIB", "Quốc Tế ");
        listBanks.put("HDBank", "Phát triển Thành phố Hồ Chí Minh");
        listBanks.put("SeABank", "Đông Nam Á");
        listBanks.put("VBSP", "Chính sách xã hội Việt Nam");
        listBanks.put("Sacombank", "Sài Gòn Thương Tín");
        listBanks.put("LienVietPostBank", "Bưu điện Liên Việt");
        listBanks.put("MSB", "Hàng Hải");
        listBanks.put("SCB", "Sài Gòn");
        listBanks.put("VDB", "Phát triển Việt Nam");
        listBanks.put("OCB", "Phương Đông");
        listBanks.put("Eximbank", "Xuất Nhập Khẩu");
        listBanks.put("TPBank", "Tiên Phong");
        listBanks.put("PVcomBank", "Đại Chúng Việt Nam");
        listBanks.put("Bac A Bank", "TMCP Bắc Á");
        listBanks.put("Woori", "Woori Việt Nam");
        listBanks.put("HSBC", "HSBC Việt Nam");
        listBanks.put("SCBVL", "Standard Chartered Việt Nam");
        listBanks.put("PBVN", "Public Bank Việt Nam");
        listBanks.put("NCB", "Quốc dân");
        listBanks.put("VietABank", "Việt Á");
        listBanks.put("DongA Bank", "Đông Á");
        listBanks.put("Vietbank", "Việt Nam Thương Tín");
        listBanks.put("Nam A Bank", "Nam Á");
        listBanks.put("OceanBank", "Đại Dương");
        listBanks.put("CIMB", "CIMB Việt Nam");
        listBanks.put("SAIGONBANK", "Sài Gòn Công Thương");
        listBanks.put("BAOVIET Bank", "Bảo Việt");

    }


}
