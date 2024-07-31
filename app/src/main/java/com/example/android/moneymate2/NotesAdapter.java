package com.example.android.moneymate2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<NotesEntity> notes = new ArrayList<>();


    public List<NotesEntity> getNotes() {
        return new ArrayList<>(notes);
    }

    public void setNotes(List<NotesEntity> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_notes_item,
                parent,
                false
        );
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        NotesEntity notesEntity = notes.get(position);
        holder.label.setText(notesEntity.getDescription());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
        String formattedDate2 = simpleDateFormat.format(notesEntity.getDate());
        holder.timestamp.setText(formattedDate2);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView label;
        private TextView timestamp;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.labelNotes);
            timestamp = itemView.findViewById(R.id.timestampNotes);
        }
    }
}
