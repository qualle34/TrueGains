package com.qualle.shapeup.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.shapeup.model.local.CurrentRecordProto;
import com.qualle.shapeup.databinding.ItemSaveWorkoutRecordBinding;
import com.qualle.shapeup.model.CurrentWorkoutViewModel;

import java.util.HashMap;
import java.util.Map;

public class WorkoutRecordRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRecordRecyclerViewAdapter.ViewHolder> {

    private final int exercisePosition;
    private final CurrentWorkoutViewModel workoutViewModel;
    private RecyclerView recyclerView;
    private final Map<Integer, CurrentRecordProto> records;

    public WorkoutRecordRecyclerViewAdapter(CurrentWorkoutViewModel workoutViewModel, int exercisePosition) {
        this.workoutViewModel = workoutViewModel;
        this.exercisePosition = exercisePosition;
        records = new HashMap<>();

        int i = 0;
        for (CurrentRecordProto record : workoutViewModel.getExercise(exercisePosition).getRecords()) {
            records.put(i, record);
            i++;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkoutRecordRecyclerViewAdapter
                .ViewHolder(ItemSaveWorkoutRecordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CurrentRecordProto record = workoutViewModel.getRecord(exercisePosition, position);

        holder.previous.setText(record.getPrevious() == null
                ? " - " : record.getPrevious());

        holder.weight.setText(record.getWeight() == 0
                ? "" : record.getWeight() + "");

        holder.reps.setText(record.getReps() == 0
                ? "" : record.getReps() + "");

        if (position == getItemCount() - 1) {
            holder.weight.requestFocus();
        }

        holder.weight.addTextChangedListener(new RecordWeightTextWatcher(records, position));
        holder.reps.addTextChangedListener(new RecordRepsTextWatcher(records, position));
    }

    @Override
    public int getItemCount() {
        return workoutViewModel.getRecordsCount(exercisePosition);
    }

    public Map<Integer, CurrentRecordProto> getRecords() {
        return records;
    }

    public void setFocus() {
        ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(0);
        if (holder != null) {
            holder.weight.requestFocus();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView previous;
        public final EditText weight;
        public final EditText reps;

        public ViewHolder(ItemSaveWorkoutRecordBinding binding) {
            super(binding.getRoot());
            previous = binding.recordTitlePrevious;
            weight = binding.recordCardWeight;
            reps = binding.recordCardReps;
        }
    }

    static class RecordWeightTextWatcher implements TextWatcher {

        private Map<Integer, CurrentRecordProto> records;
        private int position;
        private boolean onTextChanged = false;

        public RecordWeightTextWatcher(Map<Integer, CurrentRecordProto> records, int position) {
            this.records = records;
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onTextChanged = true;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (onTextChanged) {
                onTextChanged = false;

                CurrentRecordProto editedRecord = records.get(position);
                if (editedRecord != null) {
                    editedRecord.setWeight(Float.parseFloat(s.toString()));
                }

            }
        }
    }

    static class RecordRepsTextWatcher implements TextWatcher {

        private Map<Integer, CurrentRecordProto> records;
        private int position;
        private boolean onTextChanged = false;

        public RecordRepsTextWatcher(Map<Integer, CurrentRecordProto> records, int position) {
            this.records = records;
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            onTextChanged = true;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (onTextChanged) {
                onTextChanged = false;
                CurrentRecordProto editedRecord = records.get(position);
                if (editedRecord != null) {
                    editedRecord.setReps(Integer.parseInt(s.toString()));
                }
            }
        }
    }
}
