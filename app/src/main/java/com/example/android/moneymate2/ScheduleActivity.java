package com.example.android.moneymate2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ScheduleActivity extends BaseActivity {
    private ImageButton backButton;
    private PieChart pieChart;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initViews();

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getExpense().observe(this, new Observer<List<ExpenseEntity>>() {
            @Override
            public void onChanged(List<ExpenseEntity> expenseEntities) {
                Map<String, Float> monthToAmountMap = new HashMap<>();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM", Locale.getDefault());


                for (ExpenseEntity expense : expenseEntities) {
                    String category = expense.getCategory().toString().trim();
                    String month = simpleDateFormat.format(expense.getDate());
                    pieChart.setCenterText(month);
                    float amount = monthToAmountMap.getOrDefault(month, 0f);
                    monthToAmountMap.put(category, amount + expense.getAmount());
                }

                List<PieEntry> entries = new ArrayList<>();
                for (Map.Entry<String, Float> entry : monthToAmountMap.entrySet()) {
                    entries.add(new PieEntry(entry.getValue(), entry.getKey()));
                }

                PieDataSet dataSet = new PieDataSet(entries, "Expenses");

                List<Integer> colors = new ArrayList<>();
                colors.add(getResources().getColor(R.color.red));
                colors.add(getResources().getColor(R.color.violet));
                colors.add(getResources().getColor(R.color.orange));
                colors.add(getResources().getColor(R.color.green));
                colors.add(getResources().getColor(R.color.ocean));
                colors.add(getResources().getColor(R.color.yellow));
                colors.add(getResources().getColor(R.color.purple));
                colors.add(getResources().getColor(R.color.peach));
                colors.add(getResources().getColor(R.color.indigo));
                colors.add(getResources().getColor(R.color.brass));
                colors.add(getResources().getColor(R.color.apples));
                colors.add(getResources().getColor(R.color.brown_red));

                dataSet.setColors(colors);

                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);

                Legend legend = pieChart.getLegend();
                legend.setEnabled(false);

                pieChart.getDescription().setEnabled(true);
                pieChart.setExtraOffsets(5, 10, 5, 5);

                pieChart.setDragDecelerationFrictionCoef(0.95f);
                pieChart.getDescription().setEnabled(false);
                pieChart.setCenterTextSize(20f);
                pieChart.setCenterTextColor(Color.WHITE);
                pieChart.setDrawHoleEnabled(true);
                pieChart.setHoleColor(Color.BLACK);
                pieChart.setHoleRadius(58f);
                pieChart.setTransparentCircleRadius(61f);
                pieChart.setEntryLabelTextSize(15f);
                pieChart.setEntryLabelColor(Color.WHITE);
                PieData pieData = new PieData(dataSet);
                pieData.setValueTextSize(10f);
                pieData.setValueTextColor(Color.WHITE);
                pieChart.setData(pieData);
                pieChart.invalidate();
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.newIntent(ScheduleActivity.this);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.refreshList();
    }

    public void initViews() {
        pieChart = findViewById(R.id.pieChart);
        backButton = findViewById(R.id.backButton);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ScheduleActivity.class);
    }
}