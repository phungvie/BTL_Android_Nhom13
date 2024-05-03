package com.example.sonic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VietMessage {
    @SerializedName("statusCode")
    @Expose
    private int statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private String data;

    public VietMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public VietMessage(int statusCode, String message, String data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "VietMessage{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
