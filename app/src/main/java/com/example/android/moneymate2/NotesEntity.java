package com.example.android.moneymate2;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "notes")
@TypeConverters(Converters.class)
public class NotesEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private Date date;

    public NotesEntity(int id, String description, Date date) {
        this.id = id;
        this.description = description;
        this.date = date;
    }
    @Ignore
    public NotesEntity(String description, Date date) {
        this(0, description, date);
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }
}
