package com.qualle.shapeup.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.shapeup.client.api.CurrentExercise;
import com.qualle.shapeup.databinding.ItemSaveWorkoutExerciseBinding;
import com.qualle.shapeup.model.CurrentWorkoutViewModel;

public class WorkoutExerciseRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutExerciseRecyclerViewAdapter.ViewHolder> {

    private CurrentWorkoutViewModel workoutViewModel;

    public WorkoutExerciseRecyclerViewAdapter(CurrentWorkoutViewModel workoutViewModel) {
        this.workoutViewModel = workoutViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkoutExerciseRecyclerViewAdapter
                .ViewHolder(ItemSaveWorkoutExerciseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CurrentExercise exercise = workoutViewModel.getExercise(position);

        holder.title.setText(exercise.getTitle());

        if (position == getItemCount() - 1) {
            holder.records.setVisibility(View.VISIBLE);
        }

        RecyclerView recyclerView = holder.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getRootView().getContext()));
        WorkoutRecordRecyclerViewAdapter adapter = new WorkoutRecordRecyclerViewAdapter(workoutViewModel, position);
        recyclerView.setAdapter(adapter);

        holder.titleLayout.setOnClickListener(v -> {

            if (View.VISIBLE == holder.records.getVisibility()) {
                holder.records.setVisibility(View.GONE);
            } else {
                holder.records.setVisibility(View.VISIBLE);
            }
        });

        holder.addSet.setOnClickListener(v -> {
            workoutViewModel.updateRecords(position, adapter.getRecords());
            workoutViewModel.createEmptyRecord(position);

            adapter.notifyDataSetChanged();

            adapter.setFocus();
        });
    }

    @Override
    public int getItemCount() {
        return workoutViewModel.getExercisesCount();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView title;
        public final LinearLayout records;
        public final LinearLayout titleLayout;
        public final LinearLayout exercise;
        public final ImageView menu;
        public final RecyclerView recyclerView;
        public final TextView addSet;

        public ViewHolder(ItemSaveWorkoutExerciseBinding binding) {
            super(binding.getRoot());
            title = binding.recordCardTitle;
            records = binding.recordCardRecords;
            titleLayout = binding.exerciseTitleLayout;
            exercise = binding.saveWorkoutExercise;
            menu = binding.recordCardMenu;
            recyclerView = binding.recordRecyclerView;
            addSet = binding.recordAddSet;
        }
    }
}
