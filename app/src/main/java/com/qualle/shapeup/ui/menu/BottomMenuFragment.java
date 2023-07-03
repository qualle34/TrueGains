package com.qualle.shapeup.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.qualle.shapeup.client.InMemoryBackendClient;
import com.qualle.shapeup.databinding.FragmentBottomMenuBinding;
import com.qualle.shapeup.ui.adapter.CategoryRecyclerViewAdapter;
import com.qualle.shapeup.ui.adapter.ExerciseRecyclerViewAdapter;
import com.qualle.shapeup.ui.listener.MenuClickListener;

public class BottomMenuFragment extends BottomSheetDialogFragment implements MenuClickListener {

    private FragmentBottomMenuBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBottomMenuBinding.inflate(inflater, container, false);

        prepareCategoryMenu();

        binding.bottomBack.setOnClickListener(v -> onBackClick());

        return binding.getRoot();
    }

    private void prepareCategoryMenu() {
        binding.bottomTitle.setText("Categories");
        binding.bottomBack.setVisibility(View.GONE);
        binding.bottomMenuExercise.setVisibility(View.GONE);
        binding.bottomMenuCategory.setVisibility(View.VISIBLE);

        RecyclerView recyclerView = binding.bottomMenuCategory;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(new CategoryRecyclerViewAdapter(InMemoryBackendClient.getCategories(), this));
    }

    @Override
    public void onCategorySelect(long categoryId) {
        binding.bottomTitle.setText("Choose exercise");
        binding.bottomBack.setVisibility(View.VISIBLE);
        binding.bottomMenuExercise.setVisibility(View.VISIBLE);
        binding.bottomMenuCategory.setVisibility(View.GONE);

        int height = binding.bottomMenu.getHeight();

        RecyclerView recyclerView = binding.bottomMenuExercise;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ExerciseRecyclerViewAdapter(InMemoryBackendClient.getExercisesByCategory(categoryId), this));

        binding.bottomMenu.setMinimumHeight(height);
    }

    @Override
    public void onExerciseSelect(long exerciseId) {
        this.dismiss();
    }

    private void onBackClick() {
        binding.bottomTitle.setText("Categories");
        binding.bottomBack.setVisibility(View.GONE);
        binding.bottomMenuExercise.setVisibility(View.GONE);
        binding.bottomMenuCategory.setVisibility(View.VISIBLE);
    }
}