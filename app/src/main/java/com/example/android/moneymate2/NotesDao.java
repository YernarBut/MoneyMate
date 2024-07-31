package com.example.android.moneymate2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM NOTES")
    Single<List<NotesEntity>> getNotes();

    @Insert
    Completable add(NotesEntity notes);

    @Query("DELETE FROM NOTES WHERE id =:id")
    Completable remove(int id);
}
