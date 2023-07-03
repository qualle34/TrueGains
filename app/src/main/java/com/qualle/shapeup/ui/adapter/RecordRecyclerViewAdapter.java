package com.qualle.shapeup.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.shapeup.R;
import com.qualle.shapeup.client.api.Record;

import java.util.List;

public class RecordRecyclerViewAdapter extends RecyclerView.Adapter<RecordRecyclerViewAdapter.RecordViewHolder> {

    private final List<Record> records;

    public RecordRecyclerViewAdapter(List<Record> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_card_record, parent, false);

        return new RecordViewHolder(view).withAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        holder.title.setText(records.get(position).getExercise().getName());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private EditText weight;
        private EditText reps;
        private RecordRecyclerViewAdapter adapter;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.record_card_title);
            weight = itemView.findViewById(R.id.record_card_weight);
            reps = itemView.findViewById(R.id.record_card_reps);

        }

        public RecordViewHolder withAdapter(RecordRecyclerViewAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

}
