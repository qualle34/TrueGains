package com.qualle.truegain.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.client.api.Measure;
import com.qualle.truegain.databinding.ItemMeasureListBinding;
import com.qualle.truegain.ui.listener.MeasureListClickListener;

import java.util.List;

public class MeasureRecyclerViewAdapter extends RecyclerView.Adapter<MeasureRecyclerViewAdapter.ViewHolder> {

    private final List<Measure> data;
    private final MeasureListClickListener listener;

    public MeasureRecyclerViewAdapter(List<Measure> dto, MeasureListClickListener listener) {
        data = dto;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMeasureListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Measure measure = data.get(position);
        holder.name.setText(measure.getName());

        holder.name.setOnClickListener(v ->
                listener.onMeasureClick(measure.getId())
        );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView name;

        public ViewHolder(ItemMeasureListBinding binding) {
            super(binding.getRoot());
            name = binding.itemMeasureName;
        }
    }
}