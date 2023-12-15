package com.qualle.truegain.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.client.api.Measure;
import com.qualle.truegain.databinding.ItemUserMeasureBinding;
import com.qualle.truegain.util.DateFormatterUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMeasureRecyclerViewAdapter extends RecyclerView.Adapter<UserMeasureRecyclerViewAdapter.ViewHolder> {

    private final List<Map.Entry<Float, Float>> data;

    public UserMeasureRecyclerViewAdapter(Measure dto) {
        data = new ArrayList<>(dto.getData().entrySet()).stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemUserMeasureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Map.Entry<Float, Float> entry = data.get(position);

        holder.date.setText(DateFormatterUtil.dayNumberToDate(entry.getKey()));
        holder.value.setText(entry.getValue() + " Kg");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(float date, float value) {
        data.add(0, new AbstractMap.SimpleEntry<>(date, value));

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView date;
        public final TextView value;

        public ViewHolder(ItemUserMeasureBinding binding) {
            super(binding.getRoot());
            date = binding.itemMeasureDate;
            value = binding.itemMeasureValue;
        }
    }
}