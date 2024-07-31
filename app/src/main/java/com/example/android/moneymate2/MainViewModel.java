package com.example.android.moneymate2;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    private ExpenseDatabase expenseDatabase;
    private SecondDatabase secondDatabase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable disposable;
    private MutableLiveData<List<ExpenseEntity>> expense = new MutableLiveData<>();
    private MutableLiveData<List<SecondEntity>> amount = new MutableLiveData<>();
    private MutableLiveData<List<ExpenseEntity>> category = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        expenseDatabase = ExpenseDatabase.getInstance(application);
        secondDatabase = SecondDatabase.getInstance(application);
    }

    public LiveData<List<ExpenseEntity>> getExpense() {
        return expense;
    }
    public LiveData<List<SecondEntity>> getAmount() {
        return amount;
    }

    public LiveData<List<ExpenseEntity>> getCategory() {
        return category;
    }

    public void refreshList() {
        Disposable disposable1 = expenseDatabase.ExpensesDao().getExpenses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ExpenseEntity>>() {
                    @Override
                    public void accept(List<ExpenseEntity> expenseEntities) throws Throwable {
                        expense.setValue(expenseEntities);
                    }
                });
        compositeDisposable.add(disposable1);
    }

    public void remove(ExpenseEntity expense) {
        disposable = expenseDatabase.ExpensesDao().remove(expense.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Log.d("MainViewModel", "remove");
                        refreshList();
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void refreshList2() {
        Disposable disposable1 = secondDatabase.SecondDao().getAmount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SecondEntity>>() {
                    @Override
                    public void accept(List<SecondEntity> amountEntities) throws Throwable {
                        amount.setValue(amountEntities);
                    }
                });
        compositeDisposable.add(disposable1);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
