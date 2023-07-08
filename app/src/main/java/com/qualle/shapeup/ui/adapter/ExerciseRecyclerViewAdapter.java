package com.qualle.shapeup.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.shapeup.client.api.Exercise;
import com.qualle.shapeup.databinding.ItemBottomMenuExerciseBinding;
import com.qualle.shapeup.repository.LocalRepository;
import com.qualle.shapeup.ui.listener.MenuClickListener;

import java.util.List;

public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ViewHolder> {

    private final List<Exercise> values;
    private final MenuClickListener menuClickListener;

    public ExerciseRecyclerViewAdapter(List<Exercise> items, MenuClickListener menuClickListener) {
        this.values = items;
        this.menuClickListener = menuClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBottomMenuExerciseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Exercise exercise = values.get(position);
        holder.item = exercise;
        holder.title.setText(exercise.getName());



        holder.layout.setOnClickListener(v -> {
            menuClickListener.onExerciseSelect(exercise.getId());
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout layout;
        public final TextView title;
        public final ImageView image;

        public Exercise item;

        public ViewHolder(ItemBottomMenuExerciseBinding binding) {
            super(binding.getRoot());
            layout = binding.exerciseItem;
            title = binding.exerciseItemTitle;
            image = binding.exerciseItemImage;
        }

    }
}