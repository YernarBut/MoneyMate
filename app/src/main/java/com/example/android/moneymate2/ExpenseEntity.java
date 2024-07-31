package com.example.android.moneymate2;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "expenses")
@TypeConverters(Converters.class)
public class ExpenseEntity {

    @PrimaryKey (autoGenerate = true)
    private int id;
    private int amount;
    private String category;
    private Date date;

    public ExpenseEntity(int id, int amount, String category, Date date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    @Ignore
    public ExpenseEntity(int amount, String category, Date date) {
        this(0, amount, category, date);
    }
    @Ignore
    public ExpenseEntity(int amount) {
        this(0, amount, "Default category", new Date());
    }


    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

}
