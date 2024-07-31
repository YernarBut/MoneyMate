package com.example.android.moneymate2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Date;

public class AddNoteActivity extends BaseActivity {
    private ImageButton backButton;
    private EditText editTextNote;
    private Button addButton;
    private AddNoteViewModel addNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        addNoteViewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldCloseScreen) {
                if (shouldCloseScreen) {
                    finish();
                }
            }
        });
        initViews();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotes();
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Notes.newIntent(AddNoteActivity.this);
                startActivity(intent);
            }
        });
    }

    public void saveNotes() {
        String text = editTextNote.getText().toString();
        Date currentDate = new Date();
        NotesEntity notes = new NotesEntity(text, currentDate);
        addNoteViewModel.saveNotes(notes);
    }

    public void initViews() {
        backButton = findViewById(R.id.backButton);
        editTextNote = findViewById(R.id.editTextNote);
        addButton = findViewById(R.id.addButton);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }
}