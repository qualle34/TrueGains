package com.qualle.shapeup.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.shapeup.R;
import com.qualle.shapeup.databinding.ItemSaveWorkoutExerciseBinding;
import com.qualle.shapeup.model.CurrentWorkoutViewModel;
import com.qualle.shapeup.model.local.CurrentExerciseProto;

public class WorkoutExerciseRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutExerciseRecyclerViewAdapter.ViewHolder> {

    private CurrentWorkoutViewModel workoutViewModel;
    private Resources resources;
    private Activity activity;

    private int selectedItemPosition = -1;
    private boolean needToChange = false;

    public WorkoutExerciseRecyclerViewAdapter(Activity activity, CurrentWorkoutViewModel workoutViewModel) {
        this.resources = activity.getResources();
        this.activity = activity;
        this.workoutViewModel = workoutViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkoutExerciseRecyclerViewAdapter
                .ViewHolder(ItemSaveWorkoutExerciseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CurrentExerciseProto exercise = workoutViewModel.getExercise(position);

        holder.exerciseTitle.setText(exercise.getName());
        holder.equipmentTitle.setText(exercise.getEquipment());

        if (workoutViewModel.isExerciseAdded()){
            selectedItemPosition = getItemCount() - 1;
            needToChange = true;
            workoutViewModel.resetExerciseAdded();
        }

        if (selectedItemPosition == position) {

            if (needToChange) {

                if (View.GONE == holder.records.getVisibility()) {
                    holder.records.setVisibility(View.VISIBLE);
                    holder.exercise.setBackground(ResourcesCompat.getDrawable(resources, R.drawable.background_round_corners_border_twine, null));
                    holder.arrow.setRotation(180);

                } else {
                    holder.records.setVisibility(View.GONE);
                    holder.exercise.setBackground(ResourcesCompat.getDrawable(resources, R.drawable.background_round_corners, null));
                    holder.arrow.setRotation(0);
                }
            }

        } else {
            holder.records.setVisibility(View.GONE);
            holder.exercise.setBackground(ResourcesCompat.getDrawable(resources, R.drawable.background_round_corners, null));
            holder.arrow.setRotation(0);
        }

        RecyclerView recyclerView = holder.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getRootView().getContext()));
        WorkoutRecordRecyclerViewAdapter adapter = new WorkoutRecordRecyclerViewAdapter(workoutViewModel, position, selectedItemPosition == position && View.VISIBLE == holder.records.getVisibility());
        recyclerView.setAdapter(adapter);


        holder.arrow.setOnClickListener(v -> {
            selectedItemPosition = position;
            needToChange = true;
            holder.exercise.clearFocus();

            workoutViewModel.updateRecords(position, adapter.getRecords());
        });

        holder.addSet.setOnClickListener(v -> {
            selectedItemPosition = position;
            needToChange = false;
            holder.exercise.clearFocus();



            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);


            workoutViewModel.createEmptyRecord(position);
            workoutViewModel.updateRecords(position, adapter.getRecords());

        });
    }

    @Override
    public int getItemCount() {
        return workoutViewModel.getExercisesCount();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView exerciseTitle;
        public final TextView equipmentTitle;
        public final LinearLayout records;
        public final LinearLayout titleLayout;
        public final LinearLayout exercise;
        public final ImageView arrow;
        public final RecyclerView recyclerView;
        public final ImageView addSet;

        public ViewHolder(ItemSaveWorkoutExerciseBinding binding) {
            super(binding.getRoot());
            exerciseTitle = binding.recordCardExerciseTitle;
            equipmentTitle = binding.recordCardEquipmentTitle;
            records = binding.recordCardRecords;
            titleLayout = binding.exerciseTitleLayout;
            exercise = binding.saveWorkoutExercise;
            arrow = binding.recordCardArrow;
            recyclerView = binding.recordRecyclerView;
            addSet = binding.recordAddSet;
        }
    }
}
