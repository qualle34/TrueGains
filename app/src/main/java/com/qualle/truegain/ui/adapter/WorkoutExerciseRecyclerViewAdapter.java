package com.qualle.truegain.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.R;
import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.databinding.ItemSaveWorkoutExerciseBinding;
import com.qualle.truegain.model.CurrentWorkoutViewModel;
import com.qualle.truegain.model.local.CurrentExerciseProto;
import com.qualle.truegain.util.AssetManagerUtil;

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
        Exercise exercise = workoutViewModel.getExercise(position);

        holder.exerciseTitle.setText(exercise.getName());
        holder.equipmentTitle.setText(exercise.getEquipment());
        holder.image.setImageDrawable(AssetManagerUtil.getImage(holder.image.getContext(), exercise.getIconLink()));


        if (workoutViewModel.isAvailable()){
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

        holder.editExercise.setOnClickListener(v -> {
            holder.editExercise.showContextMenu(holder.editExercise.getX(), holder.editExercise.getY());
        });

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

    public int getPosition() {
        return selectedItemPosition;
    }

    public long getExerciseIdByPosition() {
        Exercise exercise = workoutViewModel.getExercise(selectedItemPosition);
        return exercise.getId();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public final TextView exerciseTitle;
        public final TextView equipmentTitle;
        public final LinearLayout records;
        public final LinearLayout titleLayout;
        public final LinearLayout exercise;
        public final ImageView arrow;
        public final RecyclerView recyclerView;
        public final ImageView addSet;
        public final ImageView editExercise;
        public final ImageView image;

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
            editExercise = binding.recordEdit;
            image = binding.recordCardIcon;

            binding.getRoot().setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

                menu.add(Menu.NONE, 1, 1, "About exercise");
                menu.add(Menu.NONE, 2, 2, "Delete empty sets");
                menu.add(Menu.NONE, 3, 3, "Delete exercise");
        }
    }
}
