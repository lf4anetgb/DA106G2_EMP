package com.example.da106g2_emp.staff;

import java.io.Serializable;

public class Staff implements Serializable {
    private String staff_id;
    private Integer sf_status;
    private String sf_accont;
    private String sf_password;
    private String sf_name;
    private Integer sf_gender;
    private String sf_phone;
    private String sf_email;
    private byte[] sf_photo;

    public Staff() {
        super();
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public Integer getSf_status() {
        return sf_status;
    }

    public void setSf_status(Integer sf_status) {
        this.sf_status = sf_status;
    }

    public String getSf_accont() {
        return sf_accont;
    }

    public void setSf_accont(String sf_accont) {
        this.sf_accont = sf_accont;
    }

    public String getSf_password() {
        return sf_password;
    }

    public void setSf_password(String sf_password) {
        this.sf_password = sf_password;
    }

    public String getSf_name() {
        return sf_name;
    }

    public void setSf_name(String sf_name) {
        this.sf_name = sf_name;
    }

    public Integer getSf_gender() {
        return sf_gender;
    }

    public void setSf_gender(Integer sf_gender) {
        this.sf_gender = sf_gender;
    }

    public String getSf_phone() {
        return sf_phone;
    }

    public void setSf_phone(String sf_phone) {
        this.sf_phone = sf_phone;
    }

    public String getSf_email() {
        return sf_email;
    }

    public void setSf_email(String sf_email) {
        this.sf_email = sf_email;
    }

    public byte[] getSf_photo() {
        return sf_photo;
    }

    public void setSf_photo(byte[] sf_photo) {
        this.sf_photo = sf_photo;
    }
}
