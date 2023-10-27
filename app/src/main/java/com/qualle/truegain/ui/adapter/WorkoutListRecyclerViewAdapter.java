package com.qualle.truegain.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.client.api.SimpleWorkout;
import com.qualle.truegain.databinding.ItemWorkoutListBinding;
import com.qualle.truegain.model.local.SimpleWorkoutProto;
import com.qualle.truegain.ui.listener.WorkoutListClickListener;
import com.qualle.truegain.util.DateFormatterUtil;

import java.util.List;

public class WorkoutListRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutListRecyclerViewAdapter.ViewHolder> {

    private final WorkoutListClickListener listener;
    private final List<SimpleWorkout> values;

    public WorkoutListRecyclerViewAdapter(WorkoutListClickListener listener, List<SimpleWorkout> values) {
        this.listener = listener;
        this.values = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemWorkoutListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SimpleWorkout workout = values.get(position);

        holder.mIdView.setText(DateFormatterUtil.formatApiDate(workout.getDate()));
        holder.mContentView.setText(workout.getExerciseCount() + " Exercises");

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

    private String formatDate(SimpleWorkout workout){
        return DateFormatterUtil.formatToSimpleDate(DateFormatterUtil.fromApiDate(workout.getDate()));
    }
}