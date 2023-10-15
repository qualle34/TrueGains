package com.qualle.truegain.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.databinding.ItemSaveWorkoutRecordBinding;
import com.qualle.truegain.model.CurrentWorkoutViewModel;
import com.qualle.truegain.model.local.CurrentRecordProto;

import java.util.HashMap;
import java.util.Map;

public class WorkoutRecordRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRecordRecyclerViewAdapter.ViewHolder> {

    private final int exercisePosition;
    private final boolean needFocus;
    private final CurrentWorkoutViewModel workoutViewModel;
    private final Map<Integer, CurrentRecordProto> records;

    public WorkoutRecordRecyclerViewAdapter(CurrentWorkoutViewModel workoutViewModel, int exercisePosition, boolean needFocus) {
        this.workoutViewModel = workoutViewModel;
        this.exercisePosition = exercisePosition;
        this.needFocus = needFocus;
        this.records = new HashMap<>();

        int i = 0;
        for (CurrentRecordProto record : workoutViewModel.getExercise(exercisePosition).getRecords()) {
            records.put(i, record);
            i++;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkoutRecordRecyclerViewAdapter
                .ViewHolder(ItemSaveWorkoutRecordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CurrentRecordProto record = records.get(position);

        holder.previous.setText(record.getPrevious() != null && !record.getPrevious().equals("")
                ? record.getPrevious() : " - ");

        holder.weight.setText(record.getWeight() == 0
                ? "" : record.getWeight() + "");

        holder.reps.setText(record.getReps() == 0
                ? "" : record.getReps() + "");

        if (needFocus & position == getItemCount() - 1) {
            holder.weight.requestFocus();
        }

        holder.weight.addTextChangedListener(new RecordTextWatcher(records, position, RecordEditTextType.WEIGHT));
        holder.reps.addTextChangedListener(new RecordTextWatcher(records, position, RecordEditTextType.REPS));
    }

    public Map<Integer, CurrentRecordProto> getRecords() {
        return records;
    }

    @Override
    public int getItemCount() {
        return workoutViewModel.getRecordsCount(exercisePosition);
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

    static class RecordTextWatcher implements TextWatcher {

        private Map<Integer, CurrentRecordProto> records;
        private int position;
        private RecordEditTextType type;
        private boolean onTextChanged = false;

        public RecordTextWatcher(Map<Integer, CurrentRecordProto> records, int position, RecordEditTextType type) {
            this.records = records;
            this.position = position;
            this.type = type;
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
            if (onTextChanged && records.get(position) != null && s != null && !s.toString().isEmpty()) {
                onTextChanged = false;

                if (RecordEditTextType.WEIGHT == type) {
                    records.get(position).setWeight(Float.parseFloat(s.toString()));
                }

                if (RecordEditTextType.REPS == type) {
                    records.get(position).setReps(Integer.parseInt(s.toString()));
                }
            }
        }
    }

    enum RecordEditTextType {
        WEIGHT,
        REPS
    }
}
