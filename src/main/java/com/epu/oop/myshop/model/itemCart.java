package com.epu.oop.myshop.model;

import java.util.Objects;

public class itemCart {
    private int idCart;

    private Product product;

    private int User_ID;

    private int Category_ID;

    private int quantity;

    private boolean choose;

    public itemCart()
    {

    }

    public itemCart(int idCart, Product product, int user_ID, int category_ID, int quantity) {
        this.idCart = idCart;
        this.product = product;
        User_ID = user_ID;
        Category_ID = category_ID;
        this.quantity = quantity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        itemCart itemCart = (itemCart) o;
        return idCart == itemCart.idCart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCart);
    }
}
