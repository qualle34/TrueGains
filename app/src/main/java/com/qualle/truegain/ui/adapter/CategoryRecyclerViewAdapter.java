package com.qualle.truegain.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.client.api.Category;
import com.qualle.truegain.client.api.Image;
import com.qualle.truegain.databinding.ItemBottomMenuCategoryBinding;
import com.qualle.truegain.ui.listener.MenuClickListener;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    private final List<Category> values;
    private final MenuClickListener menuClickListener;

    public CategoryRecyclerViewAdapter(List<Category> values, MenuClickListener menuClickListener) {
        this.values = values;
        this.menuClickListener = menuClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBottomMenuCategoryBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Category category = values.get(position);

        holder.item = category;
        holder.button.setText(category.getName());
        holder.button.setCompoundDrawablesWithIntrinsicBounds(null, getImage(holder.button.getContext(), category.getImage()), null, null);

        holder.button.setOnClickListener(v -> {
            menuClickListener.onCategorySelect(category.getId());
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final Button button;
        public Category item;

        public ViewHolder(ItemBottomMenuCategoryBinding binding) {
            super(binding.getRoot());
            button = binding.categoryButton;
        }
    }

    private Drawable getImage(Context context, Image image) {
        Resources resources = context.getResources();

        int imageId = resources.getIdentifier(image.getLink(), "drawable", context.getPackageName());
        Drawable drawable = resources.getDrawable(imageId);

        if (drawable == null) {
            throw new NullPointerException("Image not found");
        }

        return drawable;
    }
}