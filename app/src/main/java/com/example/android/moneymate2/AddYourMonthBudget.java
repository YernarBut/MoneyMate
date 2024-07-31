package com.example.android.moneymate2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddYourMonthBudget extends BaseActivity {

    private EditText editBudget;
    private Button nextButton;
    private AddYourMonthBudgetViewModel addYourMonthBudgetViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_your_month_budget);
        addYourMonthBudgetViewModel = new ViewModelProvider(this).get(AddYourMonthBudgetViewModel.class);
        addYourMonthBudgetViewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldCloseScreen) {
                if (shouldCloseScreen) {
                    finish();
                    Intent intent = MainActivity.newIntent(AddYourMonthBudget.this);
                    startActivity(intent);
                }
            }
        });
        initViews();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFromEditText();
            }
        });
    }
    public void saveFromEditText() {
        String textFromEditText = editBudget.getText().toString().trim();
        int budget = Integer.parseInt(textFromEditText);
        SecondEntity secondEntity = new SecondEntity(budget);
        addYourMonthBudgetViewModel.saveAmount(secondEntity);
        Log.d("saveFromEditText", "saveFromEditText");
    }

    public void initViews() {
        editBudget = findViewById(R.id.editBudget);
        nextButton = findViewById(R.id.nextButton);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddYourMonthBudget.class);
    }
}