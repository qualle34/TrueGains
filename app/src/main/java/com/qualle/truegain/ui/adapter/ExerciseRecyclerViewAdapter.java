package com.qualle.truegain.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.databinding.ItemBottomMenuExerciseBinding;
import com.qualle.truegain.ui.listener.MenuExerciseClickListener;
import com.qualle.truegain.util.AssetManagerUtil;

import java.util.List;

public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ViewHolder> {

    private final List<Exercise> values;
    private final MenuExerciseClickListener menuClickListener;

    public ExerciseRecyclerViewAdapter(List<Exercise> items, MenuExerciseClickListener menuClickListener) {
        this.values = items;
        this.menuClickListener = menuClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBottomMenuExerciseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Exercise exercise = values.get(position);
        holder.item = exercise;
        holder.title.setText(exercise.getName());
        holder.equipment.setText(exercise.getEquipment());
        holder.image.setImageDrawable(AssetManagerUtil.getImage(holder.image.getContext(), exercise.getIconLink()));

        View.OnClickListener clickListener = v -> menuClickListener.onExerciseSelect(exercise.getId());

        holder.layout.setOnClickListener(clickListener); // todo
        holder.title.setOnClickListener(clickListener);
        holder.equipment.setOnClickListener(clickListener);
        holder.image.setOnClickListener(clickListener);

        holder.star.setOnClickListener( v ->
                holder.star.setImageDrawable(getImage(holder.star.getContext(), "ic_star_solid"))
        );
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout layout;
        public final TextView title;
        public final TextView equipment;
        public final ImageView image;
        public final ImageView star;

        public Exercise item;

        public ViewHolder(ItemBottomMenuExerciseBinding binding) {
            super(binding.getRoot());
            layout = binding.exerciseItem;
            title = binding.exerciseItemTitle;
            equipment = binding.exerciseItemEquipment;
            image = binding.exerciseItemImage;
            star = binding.exerciseItemStar;
        }

    }


    private Drawable getImage(Context context, String link) {
        Resources resources = context.getResources();

        int imageId = resources.getIdentifier(link, "drawable", context.getPackageName());
        Drawable drawable = resources.getDrawable(imageId);

        if (drawable == null) {
            throw new NullPointerException("Image not found");
        }

        return drawable;
    }
}