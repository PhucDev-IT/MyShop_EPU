package com.epu.oop.myshop.Dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao_Interface<T> {

    public boolean Insert(T t) throws SQLException;

    public List<T> SelectAll() throws SQLException;

    public T SelectByID(T t) throws SQLException;

    public int Update(T t) throws SQLException;

    public int Delete(T t);
}