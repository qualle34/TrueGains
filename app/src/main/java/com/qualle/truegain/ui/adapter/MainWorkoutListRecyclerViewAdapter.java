package com.qualle.truegain.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.client.api.SimpleWorkout;
import com.qualle.truegain.databinding.FragmentCardWorkoutBinding;
import com.qualle.truegain.databinding.ItemWorkoutListBinding;
import com.qualle.truegain.ui.listener.WorkoutListClickListener;
import com.qualle.truegain.util.DateFormatterUtil;

import java.util.List;

public class MainWorkoutListRecyclerViewAdapter extends RecyclerView.Adapter<MainWorkoutListRecyclerViewAdapter.ViewHolder> {

    private final WorkoutListClickListener listener;
    private final List<SimpleWorkout> values;

    public MainWorkoutListRecyclerViewAdapter(WorkoutListClickListener listener, List<SimpleWorkout> values) {
        this.listener = listener;
        this.values = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentCardWorkoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SimpleWorkout workout = values.get(position);

        holder.date.setText(DateFormatterUtil.formatApiDate(workout.getDate()));
        holder.exercise.setText(workout.getExerciseCount() + " Exercises");
        holder.achievements.setText("2 new achievements");

        holder.layout.setOnClickListener(v -> listener.onWorkoutClick(workout.getId()));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView date;
        public final TextView exercise;
        public final TextView achievements;
        public final LinearLayout layout;

        public ViewHolder(FragmentCardWorkoutBinding binding) {
            super(binding.getRoot());
            date = binding.workoutCardUpperText;
            exercise = binding.workoutCardMiddleText;
            achievements = binding.workoutCardLowerText;
            layout = binding.mainWorkoutCard;
        }
    }
}