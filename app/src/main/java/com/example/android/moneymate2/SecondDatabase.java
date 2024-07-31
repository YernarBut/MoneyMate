package com.example.android.moneymate2;

import android.app.Application;
import android.media.tv.interactive.AppLinkInfo;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SecondEntity.class}, version = 1, exportSchema = false)
public abstract class SecondDatabase extends RoomDatabase {

    private static SecondDatabase instance = null;

    private static final String DB_NAME = "second.db";


    public static SecondDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    SecondDatabase.class,
                    DB_NAME
            ).build();
        }
        return instance;
    }

    public abstract SecondDao SecondDao();
}
