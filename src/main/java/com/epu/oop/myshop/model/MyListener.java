package com.epu.oop.myshop.model;

import java.sql.SQLException;

public interface MyListener<T> {

    public void onClickListener(T t) ;
}