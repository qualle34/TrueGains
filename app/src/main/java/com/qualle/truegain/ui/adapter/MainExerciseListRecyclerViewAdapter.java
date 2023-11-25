package com.qualle.truegain.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.client.api.SimpleExercise;
import com.qualle.truegain.client.api.SimpleWorkout;
import com.qualle.truegain.databinding.FragmentCardExerciseBinding;
import com.qualle.truegain.databinding.FragmentCardMainExerciseBinding;
import com.qualle.truegain.databinding.FragmentCardWorkoutBinding;
import com.qualle.truegain.ui.listener.ExerciseListClickListener;
import com.qualle.truegain.ui.listener.WorkoutListClickListener;
import com.qualle.truegain.util.DateFormatterUtil;

import java.util.List;

public class MainExerciseListRecyclerViewAdapter extends RecyclerView.Adapter<MainExerciseListRecyclerViewAdapter.ViewHolder> {

    private final ExerciseListClickListener listener;
    private final List<SimpleExercise> values;

    public MainExerciseListRecyclerViewAdapter(ExerciseListClickListener listener, List<SimpleExercise> values) {
        this.listener = listener;
        this.values = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentCardMainExerciseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SimpleExercise exercise = values.get(position);

        holder.equipment.setText(exercise.getEquipment());
        holder.name.setText(exercise.getName());
        holder.records.setText(exercise.getRecordsCount() + " Records");

        holder.layout.setOnClickListener(v -> listener.onExerciseClick(exercise.getId()));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView equipment;
        public final TextView name;
        public final TextView records;
        public final LinearLayout layout;

        public ViewHolder(FragmentCardMainExerciseBinding binding) {
            super(binding.getRoot());
            equipment = binding.chartCardUpperText;
            name = binding.chartCardMiddleText;
            records = binding.chartCardLowerText;
            layout = binding.mainExerciseCard;
        }
    }
}