package com.example.da106g2_emp.member;

import java.io.Serializable;
import java.sql.Date;

public class Member implements Serializable {
    private static final long serialVersionUID = -4627761893166770560L;
    // PrimaryKey is member_id
    private String member_id;
    private String password;
    private String name;
    private Integer gender;
    private Date birthday;
    private String cellphone;
    private String email;
    private byte[] profile;
    private Integer validation_status;
    private Date registeration_date;
    private String self_introduction;
    private String nick_name;
    private Integer point;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public Integer getValidation_status() {
        return validation_status;
    }

    public void setValidation_status(Integer validation_status) {
        this.validation_status = validation_status;
    }

    public Date getRegisteration_date() {
        return registeration_date;
    }

    public void setRegisteration_date(Date registeration_date) {
        this.registeration_date = registeration_date;
    }

    public String getSelf_introduction() {
        return self_introduction;
    }

    public void setSelf_introduction(String self_introduction) {
        this.self_introduction = self_introduction;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
