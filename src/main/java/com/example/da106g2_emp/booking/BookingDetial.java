package com.example.da106g2_emp.booking;

import java.io.Serializable;

public class BookingDetial implements Serializable {
    private String bk_number;
    private String cs_number;
    private Integer detial_price;
    private String note;

    public BookingDetial() {
    }

    public String getBk_number() {
        return bk_number;
    }

    public void setBk_number(String bk_number) {
        this.bk_number = bk_number;
    }

    public String getCs_number() {
        return cs_number;
    }

    public void setCs_number(String cs_number) {
        this.cs_number = cs_number;
    }

    public Integer getDetial_price() {
        return detial_price;
    }

    public void setDetial_price(Integer detial_price) {
        this.detial_price = detial_price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
