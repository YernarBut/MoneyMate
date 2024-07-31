package com.example.android.moneymate2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddExpenseActivity extends BaseActivity {

    private EditText editTextAmount;
    private ImageButton backButton;
    private Spinner categorySpinner;
    private Button addButton;
    private AddExpenseViewModel addExpenseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        addExpenseViewModel = new ViewModelProvider(this).get(AddExpenseViewModel.class);
        addExpenseViewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldCloseScreen) {
                if (shouldCloseScreen) {
                    finish();
                }
            }
        });
        initViews();

        List<String> categoryList = new ArrayList<String>() {{
            add("Groceries");
            add("Apartment and connectivity");
            add("Kids");
            add("Health and beauty");
            add("Pets");
            add("Mortgages, debts, loans");
            add("Car");
            add("Education");
            add("Clothing and accessories");
            add("Recreation and entertainment");
            add("Household goods");
            add("Transport");
        }};

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.item_spinner_title,
                R.layout.item_spinner_dropdown, categoryList);
        categorySpinner.setAdapter(customAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExpense();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.newIntent(AddExpenseActivity.this);
                startActivity(intent);
            }
        });
    }

    public void initViews() {
        backButton = findViewById(R.id.backButton);
        categorySpinner = findViewById(R.id.categorySpinner);
        editTextAmount = findViewById(R.id.editTextAmount);
        addButton = findViewById(R.id.addButton);
    }

    public void saveExpense() {
        String text = editTextAmount.getText().toString().trim();
        int amount = Integer.parseInt(text);
        String category = categorySpinner.getSelectedItem().toString().trim();
        Date currentDate = new Date();
        ExpenseEntity expense = new ExpenseEntity(amount, category, currentDate);
        addExpenseViewModel.saveExpense(expense);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddExpenseActivity.class);
    }
}