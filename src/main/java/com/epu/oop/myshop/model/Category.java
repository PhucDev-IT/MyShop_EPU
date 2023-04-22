package com.epu.oop.myshop.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class Category implements Serializable {

   public static final Map<String,Integer> listCategory = new HashMap<>();

   public Category()
   {
       listCategory.put("Điện thoại",1);
       listCategory.put("Laptop",2);
       listCategory.put("Máy chơi game",3);
       listCategory.put("Chăm sóc da",4);
       listCategory.put("Trang điểm",5);
       listCategory.put("Nước hoa",6);
       listCategory.put("Thực phẩm chức năng",7);
       listCategory.put("Thiết bị y tế",8);
       listCategory.put("Trang phục nam",9);
       listCategory.put("Giày dép nam",10);
       listCategory.put("Đồng hồ nam",11);
       listCategory.put("Đồ lót nam",12);
       listCategory.put("Trang phục nữ",13);
       listCategory.put("Túi xách nữ",14);
       listCategory.put("Đồ ngủ & Nội y",15);
       listCategory.put("Trang sức nữ",16);
       listCategory.put("Xe máy & Moto",17);
       listCategory.put("Xe ô tô",18);
       listCategory.put("Xe địa hình",19);
       listCategory.put("Phụ kiện thể thao",20);
       listCategory.put("Đồ thể thao nam - nữ",21);
       listCategory.put("Vali & Túi du lịch",22);
       listCategory.put("Kính mát",23);
       listCategory.put("Hoạt động dã ngoại",24);
       listCategory.put("Sản phẩm khác",25);

   }

}
