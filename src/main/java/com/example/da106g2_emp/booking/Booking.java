package com.example.da106g2_emp.booking;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bk_number;
    private String member_id;
    private String staff_id;
    private Date bk_start;
    private Date bk_end;
    private String estimated;
    private Timestamp bk_time;
    private Integer bk_cs_count;
    private Integer bk_status;
    private Integer price;

    public Booking() {
    }

    public String getBk_number() {
        return bk_number;
    }
    public void setBk_number(String bk_number) {
        this.bk_number = bk_number;
    }
    public String getMember_id() {
        return member_id;
    }
    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
    public String getStaff_id() {
        return staff_id;
    }
    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }
    public Date getBk_start() {
        return bk_start;
    }
    public void setBk_start(Date bk_start) {
        this.bk_start = bk_start;
    }
    public Date getBk_end() {
        return bk_end;
    }
    public void setBk_end(Date bk_end) {
        this.bk_end = bk_end;
    }
    public String getEstimated() {
        return estimated;
    }
    public void setEstimated(String estimated) {
        this.estimated = estimated;
    }
    public Timestamp getBk_time() {
        return bk_time;
    }
    public void setBk_time(Timestamp bk_time) {
        this.bk_time = bk_time;
    }
    public Integer getBk_cs_count() {
        return bk_cs_count;
    }
    public void setBk_cs_count(Integer bk_cs_count) {
        this.bk_cs_count = bk_cs_count;
    }
    public Integer getBk_status() {
        return bk_status;
    }
    public void setBk_status(Integer bk_status) {
        this.bk_status = bk_status;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
}
