package com.example.android.moneymate2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
@Dao
public interface ExpensesDao {

   @Query("SELECT * FROM EXPENSES")
    Single<List<ExpenseEntity>> getExpenses();

   @Insert
    Completable add(ExpenseEntity expense);

   @Query("DELETE FROM expenses WHERE id = :id")
    Completable remove(int id);

}
