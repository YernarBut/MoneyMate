package com.example.android.moneymate2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Calculator extends BaseActivity {
    private BottomNavigationView bottomNavigationView;
    private TextView solutionTextView, resultTextView;
    private MaterialButton buttonZero, button1, button2, button3, button4, button5, button6,
            button7, button8, button9;
    private MaterialButton buttonRemove, buttonDivide, buttonMultiply, buttonMinus, buttonPlus,
            buttonEquals, buttonPercent;
    private MaterialButton buttonDot, buttonAC;
    String workings = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        initViews();


        bottomNavigationView.setSelectedItemId(R.id.calculator);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    Intent mainIntent = MainActivity.newIntent(Calculator.this);
                    startActivity(mainIntent);
                } else if (item.getItemId() == R.id.notes) {
                    Intent notesIntent = Notes.newIntent(Calculator.this);
                    startActivity(notesIntent);
                }
                return false;
            }
        });

    }

    private void setWorkings(String givenValue) {
        workings = solutionTextView.getText().toString();
        workings = workings + givenValue;
        solutionTextView.setText(workings);

    }

    public void divisionOnClick(View view) {
        setWorkings("/");

    }

    public void multiplyOnClick(View view) {
        setWorkings("*");
    }

    public void minusOnclick(View view) {
        setWorkings("-");
    }

    public void plusOnClick(View view) {
        setWorkings("+");
    }

    public void decimalOnClick(View view) {
        setWorkings(".");
    }

    public void zeroOnClick(View view) {
        setWorkings("0");
    }

    public void oneoOnClick(View view) {
        setWorkings("1");
    }

    public void twoOnClick(View view) {
        setWorkings("2");
    }

    public void threeOnClick(View view) {
        setWorkings("3");
    }

    public void fourOnClick(View view) {
        setWorkings("4");
    }
    public void fiveOnClick(View view) {
        setWorkings("5");
    }
    public void sixOnClick(View view) {
        setWorkings("6");
    }

    public void sevenOnClick(View view) {
        setWorkings("7");
    }

    public void eightOnClick(View view) {
        setWorkings("8");
    }

    public void nineOnClick(View view) {
        setWorkings("9");
    }
    public void percentOnClick(View view) {
        setWorkings("%");
    }


    public void clearOnClick(View view) {
        solutionTextView.setText("");
        workings = "";
        resultTextView.setText("");
    }

    public void equalsOnclick(View view) {
        String dataToCalc = solutionTextView.getText().toString();
        if (isDivisionByZero(dataToCalc)) {
            resultTextView.setText("You can't divide by zero");
            resultTextView.setTextSize(20);
            return;
        }

        dataToCalc = proccesExpression(dataToCalc);

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");

        try {
            Object result = engine.eval(dataToCalc);
            if (result instanceof Double) {
                Double doubleresult = (Double) result;
                if (doubleresult == Math.floor(doubleresult)) {
                    resultTextView.setText(String.valueOf(doubleresult.intValue()));
                } else {
                    resultTextView.setText(String.valueOf(doubleresult));
                }
            } else if (result instanceof Integer) {
                resultTextView.setText(String.valueOf((Integer) result));
            } else {
                resultTextView.setText(result.toString());
            }
        } catch (ScriptException e) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isDivisionByZero(String expression) {
        String simplifiedExpression = expression.replaceAll("\\s+","");
        String divisionByZeroPattern = "(/0(\\D|$))";
        Pattern pattern = Pattern.compile(divisionByZeroPattern);
        Matcher matcher = pattern.matcher(simplifiedExpression);
        return matcher.find();
    }

    private String proccesExpression (String expression) {
        String percentPattern = "(\\d+)%";
        Pattern pattern = Pattern.compile(percentPattern);
        Matcher matcher = pattern.matcher(expression);

        StringBuffer processedExpression = new StringBuffer();
        while (matcher.find()) {
            String number = matcher.group(1);
            double value = Double.parseDouble(number) / 100;
            matcher.appendReplacement(processedExpression, String.valueOf(value));
        }
        matcher.appendTail(processedExpression);
        return processedExpression.toString();
    }

    public void removeOnClick(View view) {
        workings = solutionTextView.getText().toString();
        if(workings.length() > 0) {
            workings = workings.substring(0, workings.length() - 1);
            solutionTextView.setText(workings);
        }
    }

    public void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        solutionTextView = findViewById(R.id.solutionTextView);
        resultTextView = findViewById(R.id.resultTextView);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonRemove = findViewById(R.id.buttonRemove);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonPercent = findViewById(R.id.buttonPercent);
        buttonZero = findViewById(R.id.buttonZero);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonDot = findViewById(R.id.buttonDot);
        buttonAC = findViewById(R.id.buttonAC);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, Calculator.class);
    }
}