package com.epu.oop.myshop.model;

public class ProductSeller {
    private Product product_ID;
    private User User_ID;

    public ProductSeller(){}

    public ProductSeller(Product product_ID, User user_ID) {
        this.product_ID = product_ID;
        User_ID = user_ID;
    }

    public Product getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(Product product_ID) {
        this.product_ID = product_ID;
    }

    public User getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(User user_ID) {
        User_ID = user_ID;
    }
}
