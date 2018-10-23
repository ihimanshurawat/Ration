package com.himanshurawat.ration.respository.network;

import com.google.gson.annotations.SerializedName;
import com.himanshurawat.ration.respository.db.entity.People;

import java.io.Serializable;
import java.util.List;

public class PeopleResponse implements Serializable {


    private String status;
    private int from;
    private int to;

    @SerializedName("people")
    private List<People> people;

    private String error;

    public PeopleResponse(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public List<People> getPeople() {
        return people;
    }

    public void setPeople(List<People> people) {
        this.people = people;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

