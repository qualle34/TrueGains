package com.qualle.shapeup.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.shapeup.R;
import com.qualle.shapeup.model.dto.Exercise;
import com.qualle.shapeup.model.dto.Record;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>{

    private List<Record> records;

    public ExerciseAdapter(List<Record> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_card, parent, false);

        return new ExerciseViewHolder(view).withAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        holder.textView.setText(records.get(position).getExercise().getName());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ExerciseAdapter adapter;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textview);

        }

        public ExerciseViewHolder withAdapter(ExerciseAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

}
