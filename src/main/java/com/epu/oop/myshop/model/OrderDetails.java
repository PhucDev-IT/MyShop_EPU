package com.epu.oop.myshop.model;



import java.math.BigDecimal;


public class OrderDetails {

    private Order hoaDon;

    private Product product;

    private int quantity;

    private BigDecimal price;

    //------------------------------- CONSTRUCTOR ---------------------------------------------------------


    public OrderDetails(){}
    public OrderDetails(Product p, int soLuong, BigDecimal dongia){
        this.product = p;
        this.quantity = soLuong;
        this.price = dongia;
    }
    public OrderDetails(Order hoadon, Product product)
    {
        this.hoaDon = hoadon;
        this.product = product;
    }




    //-----------------------------------------SETTER - GETTER --------------------------------------------------------


    public Order getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(Order hoaDon) {
        this.hoaDon = hoaDon;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }



}




