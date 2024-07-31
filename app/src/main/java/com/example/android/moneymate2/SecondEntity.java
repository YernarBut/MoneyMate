package com.example.android.moneymate2;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses2")
public class SecondEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int amount;

    public SecondEntity(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Ignore
    public SecondEntity(int amount) {
        this(0, amount);
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }
}
