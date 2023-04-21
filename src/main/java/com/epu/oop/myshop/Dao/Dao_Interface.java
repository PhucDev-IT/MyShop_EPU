package com.epu.oop.myshop.Dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao_Interface<T> {

    public boolean Insert(T t) throws SQLException;

    public List<T> SelectAll() throws SQLException;

    public T SelectByID(T t);

    public int Update(T t);

    public int Delete(T t);
}