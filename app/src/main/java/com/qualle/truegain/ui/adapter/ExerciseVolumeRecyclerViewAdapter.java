package com.qualle.truegain.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.R;
import com.qualle.truegain.databinding.ItemExerciseValueBinding;
import com.qualle.truegain.model.local.VolumeProto;

import java.util.List;

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
