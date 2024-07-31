package com.example.android.moneymate2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class Notes extends BaseActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabExpense;
    private NotesAdapter notesAdapter;
    private RecyclerView recycleNotes;
    private NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        initViews();

        notesAdapter = new NotesAdapter();
        recycleNotes.setAdapter(notesAdapter);

        notesViewModel.getNotes().observe(this, new Observer<List<NotesEntity>>() {
            @Override
            public void onChanged(List<NotesEntity> notesEntities) {
                notesAdapter.setNotes(notesEntities);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                NotesEntity notes = notesAdapter.getNotes().get(position);
                notesViewModel.remove(notes);
            }
        });
        itemTouchHelper.attachToRecyclerView(recycleNotes);

        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddNoteActivity.newIntent(Notes.this);
                startActivity(intent);
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.notes);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    Intent mainIntent = MainActivity.newIntent(Notes.this);
                    startActivity(mainIntent);
                } else if (item.getItemId() == R.id.calculator) {
                    Intent calculatorIntent = Calculator.newIntent(Notes.this);
                    startActivity(calculatorIntent);
                }
                return false;
            }
        });
    }

    public void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        recycleNotes = findViewById(R.id.recycleNotes);
        fabExpense = findViewById(R.id.fabExpense);
    }

    @Override
    protected void onResume() {
        super.onResume();
        notesViewModel.refreshList();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, Notes.class);
    }
}