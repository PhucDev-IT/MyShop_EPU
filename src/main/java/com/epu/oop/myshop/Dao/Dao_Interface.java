package com.epu.oop.myshop.Dao;

import java.util.List;

public interface Dao_Interface<T> {

    public int Insert(T t);

    public List<T> SelectAll();

    public T SelectByID(T t);

    public int Update(T t);

    public int Delete(T t);
}