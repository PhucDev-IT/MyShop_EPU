package com.epu.oop.myshop.model;

import java.math.BigDecimal;
import java.util.Objects;

public class itemCartModel {
    private int idCart;

    private Product product;

    private int User_ID;

    private int Category_ID;

    private int quantity;

    private boolean choose;

    private BigDecimal SumMoney;
    public itemCartModel()
    {

    }

    public itemCartModel(int idCart, Product product, int user_ID, int category_ID, int quantity) {
        this.idCart = idCart;
        this.product = product;
        User_ID = user_ID;
        Category_ID = category_ID;
        this.quantity = quantity;
    }

    public itemCartModel(Product product,int number)
    {
        this.product = product;
        this.quantity = number;
    }
    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public int getCategory_ID() {
        return Category_ID;
    }

    public void setCategory_ID(int category_ID) {
        Category_ID = category_ID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    public BigDecimal getSumMoney() {
        return SumMoney;
    }

    public void setSumMoney(BigDecimal sumMoney) {
        SumMoney = sumMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        itemCartModel itemCartModel = (itemCartModel) o;
        return idCart == itemCartModel.idCart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCart);
    }

    @Override
    public String toString() {
        return "itemCartModel{" +
                "idCart=" + idCart +
              //  ", product=" + product +
                ", User_ID=" + User_ID +
                ", Category_ID=" + Category_ID +
                ", quantity=" + quantity +
                ", choose=" + choose +
                ", SumMoney=" + SumMoney +
                '}';
    }
}
