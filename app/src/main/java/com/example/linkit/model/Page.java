package com.example.linkit.model;
import static java.lang.Long.parseLong;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "table_PageToLink")
public class Page {


    @ColumnInfo(name = "page_id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "page_code")
    private String code;

    @ColumnInfo(name = "page_url")
    private String url;




    public long getId() {
        return id;
    }

    public void setId(long id) {

        this.id = id;
    }


    public String getCode() {
        return code;
    }

    @Ignore
    public Page() {
    }

    public Page(String code, String url) {

        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);
        this.id = parseLong(datetime);


        String temp = code.toUpperCase();
        temp = temp.replaceAll(" ","");
        this.code = temp;
        this.url = url;
    }

    public void setCode(String code) {
        String temp = code.toUpperCase();
        temp = temp.replaceAll(" ","");
        this.code = temp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



}
