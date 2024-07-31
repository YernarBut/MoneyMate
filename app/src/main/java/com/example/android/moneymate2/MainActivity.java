package com.example.android.moneymate2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InsertGesture;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private static final String PREFERENCES = "SWITCH_PREFERENCES";
    private static final String PREFERENCES2 = "MODE";
    SharedPreferences settings;
    private FloatingActionButton fabExpense;
    private ImageButton settingsButton;
    private ImageButton imageCurrency;
    private ImageButton iconGraph;
    private TextView summView;
    private ExpensesAdapter expensesAdapter;
    private RecyclerView recycleExpense;
    private MainViewModel mainViewModel;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        settings = getSharedPreferences(PREFERENCES2, Context.MODE_PRIVATE);
        boolean nightMode = settings.getBoolean("nightMode", false);

        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initViews();

        expensesAdapter = new ExpensesAdapter();
        recycleExpense.setAdapter(expensesAdapter);

        mainViewModel.getExpense().observe(this, new Observer<List<ExpenseEntity>>() {
            @Override
            public void onChanged(List<ExpenseEntity> expense) {
                expensesAdapter.setExpense(expense);
                Log.d("Expense", "Expense list set in adapter");
            }
        });

        mainViewModel.getAmount().observe(this, new Observer<List<SecondEntity>>() {
            @Override
            public void onChanged(List<SecondEntity> secondEntities) {
                if (secondEntities != null && !secondEntities.isEmpty()) {
                    SecondEntity firstSavedAmount = secondEntities.get(0);
                    int budgetValue = firstSavedAmount.getAmount();
                    summView.setText(String.valueOf(budgetValue));
                    mainViewModel.getExpense().observe(MainActivity.this, new Observer<List<ExpenseEntity>>() {
                        @Override
                        public void onChanged(List<ExpenseEntity> expenseEntities) {
                            int totalExpense = 0;
                            for (ExpenseEntity expense : expenseEntities) {
                                totalExpense += expense.getAmount();
                            }
                            int remainingBudget = budgetValue - totalExpense;
                            summView.setText(String.valueOf(remainingBudget));
                        }
                    });
                }
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
            public void onSwiped(
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    int direction) {
                int position = viewHolder.getAdapterPosition();
                ExpenseEntity expense = expensesAdapter.getExpense().get(position);
                mainViewModel.remove(expense);
            }
        });
        itemTouchHelper.attachToRecyclerView(recycleExpense);



        settings = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        boolean switchState = settings.getBoolean("switch_state", false);
        if (switchState) {
            imageCurrency.setVisibility(View.GONE);
        } else {
            imageCurrency.setVisibility(View.VISIBLE);
        }

        int positionSpinner = settings.getInt("positionSpinner", 0);

        if (positionSpinner == 1) {
            imageCurrency.setImageResource(R.drawable.ic_euro);
        } else if (positionSpinner == 2) {
            imageCurrency.setImageResource(R.drawable.ic_ruble);
        } else if (positionSpinner == 3) {
            imageCurrency.setImageResource(R.drawable.ic_pound);
        } else if (positionSpinner == 4) {
            imageCurrency.setImageResource(R.drawable.ic_yen);
        } else if (positionSpinner == 5) {
            imageCurrency.setImageResource(R.drawable.ic_dollar);
        } else {
            imageCurrency.setImageResource(R.drawable.ic_tenge3);
        }

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SettingsActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });

        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddExpenseActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });

        iconGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ScheduleActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.notes) {
                    Intent notesIntent = Notes.newIntent(MainActivity.this);
                    startActivity(notesIntent);
                } else if (item.getItemId() == R.id.calculator) {
                    Intent calculatorIntent = Calculator.newIntent(MainActivity.this);
                    startActivity(calculatorIntent);
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.refreshList();
        mainViewModel.refreshList2();
    }

    private void initViews() {
        settingsButton = findViewById(R.id.settingsButton);
        imageCurrency = findViewById(R.id.imageCurrency);
        fabExpense = findViewById(R.id.fabExpense);
        recycleExpense = findViewById(R.id.recycleExpense);
        summView = findViewById(R.id.summView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        iconGraph = findViewById(R.id.iconGraph);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}