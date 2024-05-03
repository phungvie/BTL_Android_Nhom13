package com.example.sonic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class UserDTO {
    @SerializedName("userID")
    @Expose
    private Integer userID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("dateOfBirth")
    @Expose
    private Object dateOfBirth;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("img")
    @Expose
    private Object img;
    @SerializedName("followers")
    @Expose
    private Integer followers;
    @SerializedName("following")
    @Expose
    private Integer following;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Object getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Object dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", mail='" + mail + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", country=" + country +
                ", img=" + img +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }
}
