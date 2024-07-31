package com.example.android.moneymate2;

import android.app.Application;

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

public class NotesViewModel extends AndroidViewModel {

    private NotesDatabase notesDatabase;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable disposable;
    private MutableLiveData<List<NotesEntity>> notes = new MutableLiveData<>();

    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesDatabase = NotesDatabase.getInstance(application);
    }

    public LiveData<List<NotesEntity>> getNotes() {
        return notes;
    }

    public void refreshList() {
        Disposable disposable1 = notesDatabase.NotesDao().getNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NotesEntity>>() {
                    @Override
                    public void accept(List<NotesEntity> notesEntities) throws Throwable {
                        notes.setValue(notesEntities);
                    }
                });
        compositeDisposable.add(disposable1);
    }

    public void remove(NotesEntity notes) {
        disposable = notesDatabase.NotesDao().remove(notes.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        refreshList();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
