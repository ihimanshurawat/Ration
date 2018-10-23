package com.himanshurawat.ration.respository.network;

import java.io.Serializable;

public class SignupReponse implements Serializable {

    private String status;
    private String token;
    private String createdAt;
    private String error;


    public SignupReponse(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
