package com.qualle.shapeup.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qualle.shapeup.client.api.Workout;
import com.qualle.shapeup.databinding.ItemWorkoutListBinding;
import com.qualle.shapeup.ui.listener.WorkoutListClickListener;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class WorkoutListRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutListRecyclerViewAdapter.ViewHolder> {
    private final  WorkoutListClickListener listener;
    private final List<Workout> values;

    public WorkoutListRecyclerViewAdapter(WorkoutListClickListener listener, List<Workout> values) {
        this.listener = listener;
        this.values = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemWorkoutListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Workout workout = values.get(position);

        holder.mIdView.setText(workout.getDate().getDayOfMonth() + " " + workout.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        holder.mContentView.setText("Achievements Count: " + workout.getAchievementsCount());

        holder.workout.setOnClickListener(v -> listener.onWorkoutClick(workout.getId()));

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final LinearLayout workout;

        public ViewHolder(ItemWorkoutListBinding binding) {
            super(binding.getRoot());
            mIdView = binding.date;
            mContentView = binding.exerciseCount;
            workout = binding.workoutLayout;
        }
    }
}