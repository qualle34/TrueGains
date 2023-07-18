package com.qualle.shapeup.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.shapeup.databinding.ItemExerciseValueBinding;

import java.util.List;

public class ExerciseVolumeRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseVolumeRecyclerViewAdapter.ViewHolder> {

    private final List<String> data;

    public ExerciseVolumeRecyclerViewAdapter(List<String> data) {
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
        holder.title.setText(data.get(position));
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView title;

        public ViewHolder(ItemExerciseValueBinding binding) {
            super(binding.getRoot());
            title = binding.volumeTitle;
        }
    }

}
