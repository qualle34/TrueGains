package com.qualle.shapeup.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.shapeup.R;
import com.qualle.shapeup.databinding.ItemExerciseValueBinding;
import com.qualle.shapeup.model.local.VolumeProto;

import java.util.List;
import java.util.Map;

public class ExerciseVolumeRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseVolumeRecyclerViewAdapter.ViewHolder> {

    private final List<VolumeProto> data;

    public ExerciseVolumeRecyclerViewAdapter(List<VolumeProto> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExerciseVolumeRecyclerViewAdapter
                .ViewHolder(ItemExerciseValueBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.exercise.setText(data.get(position).getExercise());
        holder.volume.setText(data.get(position).getValue() + " kg");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView exercise;
        public final TextView volume;
        public final LinearLayout layout;

        public ViewHolder(ItemExerciseValueBinding binding) {
            super(binding.getRoot());
            exercise = binding.exerciseTitle;
            volume = binding.volumeTitle;
            layout = binding.exerciseCard;
        }
    }

}
