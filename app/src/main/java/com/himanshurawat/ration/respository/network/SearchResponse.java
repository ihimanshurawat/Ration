package com.himanshurawat.ration.respository.network;

import com.google.gson.annotations.SerializedName;
import com.himanshurawat.ration.respository.db.entity.People;

import java.io.Serializable;
import java.util.List;

public class SearchResponse implements Serializable {

    private String status;
    private String error;
    private int count;

    @SerializedName("people")
    private List<People> people;

    public SearchResponse() {
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<People> getPeople() {
        return people;
    }

    public void setPeople(List<People> people) {
        this.people = people;
    }
}
