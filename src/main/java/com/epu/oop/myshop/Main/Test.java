package com.epu.oop.myshop.Main;

import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.User;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class test {
    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
    }



    public static void main(String[] args) {


        String[] linkName = {"phone", "Laptop", "may-choi-game", "cham-soc-da", "trang-diem", "Nước hoa", "thực phẩm chức năng",
                "thiết bị y tế", "quan-ao-nam", "giay-nam", "dong-ho", "", "thoi-trang-nu", "tui-xach-nu", "do-ngu-nu", "trang sức nữ", "xe-may"
                , "oto", "", "phu-kien-the-thao", "", "Vali-túi xách", "kính mắt", "", ""};


        Random random = new Random();

        StringBuilder filePathBuilder = new StringBuilder();
        StringBuilder newFilePathBuilder = new StringBuilder();
        int i=1;
        for(String lastFileName : linkName){
            System.out.println(i++);
            File folder = new File("src/main/resources/com/epu/oop/myshop/image/Product/"+lastFileName);
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                    String filePath = "/" + file.getPath().substring(file.getPath().indexOf("com")).replace('\\', '/');
                    System.out.println("Name: "+nameWithoutExtension+" - Link: "+filePath);
                }
            }
        }
    }
}
