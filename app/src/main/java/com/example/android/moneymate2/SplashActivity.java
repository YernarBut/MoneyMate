package com.example.android.moneymate2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SplashActivity extends BaseActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("It Workrs", "It Works");
                mainViewModel.getAmount().observe(SplashActivity.this, new Observer<List<SecondEntity>>() {
                    @Override
                    public void onChanged(List<SecondEntity> secondEntities) {
                        Log.d("onChanged started", "onChanged started");
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser == null) {
                            Intent intent = StartPageActivity.newIntent(SplashActivity.this);
                            startActivity(intent);
                        } else if (secondEntities == null || secondEntities.isEmpty()) {
                            Log.d("Data", "No data found");
                            Intent intent = AddYourMonthBudget.newIntent(SplashActivity.this);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d("Data", "Data found");
                            int budget = secondEntities.get(0).getAmount();
                            if (budget == 0) {
                                Intent intent = AddYourMonthBudget.newIntent(SplashActivity.this);
                                startActivity(intent);
                            } else {
                                Intent intent = MainActivity.newIntent(SplashActivity.this);
                                startActivity(intent);
                            }
                            finish();
                        }
                    }
                });
            }
        }, 2300);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.refreshList2();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }
}