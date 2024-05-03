package com.example.sonic.model;

import com.google.gson.annotations.Expose;

public class LoginRequest {
    @Expose
    String username;
    @Expose
    String password;

    public LoginRequest() {
        this.username = "viet@gmail.com";
        this.password = "123";
    }
    public LoginRequest(String username,String password) {
        this.username = username;
        this.password = password;
    }
}
