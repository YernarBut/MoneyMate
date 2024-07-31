package com.example.android.moneymate2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder> {

    private List<ExpenseEntity> expense = new ArrayList<>();

    public List<ExpenseEntity> getExpense() {
        return new ArrayList<>(expense);
    }

    public void setExpense(List<ExpenseEntity> expense) {
        this.expense = expense;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_expense_item,
                parent,
                false
        );
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        ExpenseEntity expenseEntity = expense.get(position);
        holder.labelTextView.setText(truncateText(expenseEntity.getCategory(), 25));
        holder.amountTextView.setText(String.valueOf(expenseEntity.getAmount()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
        String formattedDate = simpleDateFormat.format(expenseEntity.getDate());
        holder.timestampTextView.setText(formattedDate);
    }

    public String truncateText(String text, int maxLenght) {
        if (text.length() > maxLenght) {
            return text.substring(0, maxLenght - 3) + "...";
        }
        return text;
    }

    @Override
    public int getItemCount() {
        return expense.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private TextView labelTextView;
        private TextView amountTextView;
        private TextView timestampTextView;


        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            labelTextView = itemView.findViewById(R.id.label);
            amountTextView = itemView.findViewById(R.id.amount);
            timestampTextView = itemView.findViewById(R.id.timestamp);
        }
    }

}
