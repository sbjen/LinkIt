package com.example.linkit.model;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Page.class},version = 1)
public abstract class PageDatabase extends RoomDatabase {
    public abstract PageDAO getPageDao();
}
