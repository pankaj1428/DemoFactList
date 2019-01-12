package com.sample.demofactlist.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FactList {


    // to handle exception
    private String exception;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("rows")
    @Expose
    private ArrayList<Row> rows = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Row> rows) {
        this.rows = rows;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
