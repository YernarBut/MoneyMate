package com.example.android.moneymate2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface SecondDao {

    @Query("SELECT * FROM EXPENSES2")
    Single<List<SecondEntity>> getAmount();

    @Insert
    Completable add(SecondEntity amount);

    @Query("DELETE FROM expenses2 WHERE id = :id")
    Completable remove(int id);
}
