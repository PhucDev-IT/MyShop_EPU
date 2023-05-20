package com.epu.oop.myshop.Main;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        BigDecimal phiRut = new BigDecimal(5000);
        BigDecimal money = new BigDecimal(90000);
        money = money.add(phiRut);
        System.out.println(money);
    }
}
