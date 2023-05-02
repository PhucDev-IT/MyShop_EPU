package com.epu.oop.myshop.model;

import java.sql.Date;

public class Messenger {
    private int ID;

    private String imgSrc;

    private String nameSender;

    private String content;

    private Date sentDate;

    private boolean status;

    private int Account_ID;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getAccount_ID() {
        return Account_ID;
    }

    public void setAccount_ID(int account_ID) {
        Account_ID = account_ID;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "Messenger{" +
                "ID=" + ID +
                ", imgSrc='" + imgSrc + '\'' +
                ", nameSender='" + nameSender + '\'' +
                ", content='" + content + '\'' +
                ", sentDate=" + sentDate +
                ", status=" + status +
                ", Account_ID=" + Account_ID +
                '}';
    }
}
