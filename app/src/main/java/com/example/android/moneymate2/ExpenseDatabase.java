package com.example.android.moneymate2;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;

@Database(entities = {ExpenseEntity.class}, version = 1, exportSchema = false)
public abstract class ExpenseDatabase extends RoomDatabase {

    private static ExpenseDatabase instance = null;
    private static final String DB_NAME = "expenses.db";

    public static ExpenseDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    ExpenseDatabase.class,
                    DB_NAME
            ).build();
        }
        return instance;
    }

    public abstract ExpensesDao ExpensesDao();


}
