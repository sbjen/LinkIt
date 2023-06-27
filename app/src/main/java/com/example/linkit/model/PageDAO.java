package com.example.linkit.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.security.PublicKey;

@Dao
public interface PageDAO {
    @Insert
    public Long addPage(Page page);

    @Query("SELECT * FROM table_PageToLink WHERE  page_code ==:pageCode")
    public Page getPageFromPageCode(String pageCode);
}
