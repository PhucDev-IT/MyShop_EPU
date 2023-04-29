package com.epu.oop.myshop.model;


import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class VoucherModel {
    private String MaVoucher;

    private float TiLeGiamGia;

    private BigDecimal soTienGiam;

    private int soLuong;

    private String noiDung;

    private String ImgVoucher;

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private User user;

    public VoucherModel(){}

    public String getMaVoucher() {
        return MaVoucher;
    }

    public VoucherModel(String id){
        this.MaVoucher = id;
    }
    public VoucherModel(String maVoucher, float giamGia,BigDecimal sotiengiam, int soLuong, String noiDung,String ImgVoucher, Date ngayBatDau, Date ngayKetThuc) {
        MaVoucher = maVoucher;
        TiLeGiamGia = giamGia;
        this.soLuong = soLuong;
        this.noiDung = noiDung;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.ImgVoucher = ImgVoucher;
        this.soTienGiam = sotiengiam;
    }

    public VoucherModel(String maVoucher, float tiLeGiamGia, BigDecimal soTienGiam, int soLuong, String noiDung,
                        String imgVoucher, Date ngayBatDau, Date ngayKetThuc, User user) {
        MaVoucher = maVoucher;
        TiLeGiamGia = tiLeGiamGia;
        this.soTienGiam = soTienGiam;
        this.soLuong = soLuong;
        this.noiDung = noiDung;
        ImgVoucher = imgVoucher;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.user = user;
    }

    public String getImgVoucher() {
        return ImgVoucher;
    }

    public void setImgVoucher(String imgVoucher) {
        ImgVoucher = imgVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        MaVoucher = maVoucher;
    }

    public float getTiLeGiamGia() {
        return TiLeGiamGia;
    }

    public void setTiLeGiamGia(float giamGia) {
        TiLeGiamGia = giamGia;
    }


    public BigDecimal getSoTienGiam() {
        return soTienGiam;
    }

    public void setSoTienGiam(BigDecimal soTienGiam) {
        this.soTienGiam = soTienGiam;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "VoucherModel{" +
                "MaVoucher='" + MaVoucher + '\'' +
                ", TiLeGiamGia=" + TiLeGiamGia +
                ", soTienGiam=" + soTienGiam +
                ", soLuong=" + soLuong +
                ", noiDung='" + noiDung + '\'' +
                ", ImgVoucher='" + ImgVoucher + '\'' +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherModel that = (VoucherModel) o;
        return Objects.equals(MaVoucher, that.MaVoucher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MaVoucher);
    }
}
