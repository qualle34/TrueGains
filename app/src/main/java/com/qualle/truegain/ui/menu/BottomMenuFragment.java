package com.qualle.truegain.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.api.Category;
import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentBottomMenuBinding;
import com.qualle.truegain.model.BottomMenuViewModel;
import com.qualle.truegain.model.CurrentWorkoutViewModel;
import com.qualle.truegain.ui.adapter.CategoryRecyclerViewAdapter;
import com.qualle.truegain.ui.adapter.ExerciseRecyclerViewAdapter;
import com.qualle.truegain.ui.listener.MenuClickListener;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomMenuFragment extends BottomSheetDialogFragment implements MenuClickListener {

    private CurrentWorkoutViewModel workoutViewModel;
    private BottomMenuViewModel menuViewModel;

    private FragmentBottomMenuBinding binding;

    @Inject
    public BackendClient client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBottomMenuBinding.inflate(inflater, container, false);
        workoutViewModel = new ViewModelProvider(getParentFragment()).get(CurrentWorkoutViewModel.class);
        menuViewModel = new ViewModelProvider(getParentFragment()).get(BottomMenuViewModel.class);

        DaggerApplicationComponent.create().inject(this);

        prepareCategoryMenu();

        return binding.getRoot();
    }

    private void prepareCategoryMenu() {
        BottomMenuFragment fragment = this;

        binding.bottomTitle.setText("Categories");
        binding.bottomBack.setVisibility(View.GONE);
        binding.bottomMenuExercise.setVisibility(View.GONE);
        binding.bottomMenuCategory.setVisibility(View.VISIBLE);

        binding.bottomBack.setOnClickListener(v -> onBackClick());

        RecyclerView recyclerView = binding.bottomMenuCategory;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        if (menuViewModel.isAvailable()) {

            recyclerView.setAdapter(new CategoryRecyclerViewAdapter(menuViewModel.getCategories(), fragment));

        } else {
            client.getCategories("exercise").enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                    List<Category> categories = response.body();
                    menuViewModel.setCategories(categories);
                    recyclerView.setAdapter(new CategoryRecyclerViewAdapter(categories, fragment));
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }


    }

    @Override
    public void onCategorySelect(long categoryId) {
        BottomMenuFragment fragment = this;

        binding.bottomTitle.setText("Choose exercise");
        binding.bottomBack.setVisibility(View.VISIBLE);
        binding.bottomMenuExercise.setVisibility(View.VISIBLE);
        binding.bottomMenuCategory.setVisibility(View.GONE);

        int height = binding.bottomMenu.getHeight();

        RecyclerView recyclerView = binding.bottomMenuExercise;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (menuViewModel.isAvailable()) {
            recyclerView.setAdapter(new ExerciseRecyclerViewAdapter(menuViewModel.getExercises(categoryId), fragment));

        } else {
            client.getCategories("exercise").enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                    List<Category> categories = response.body();
                    menuViewModel.setCategories(categories);
                    recyclerView.setAdapter(new ExerciseRecyclerViewAdapter(menuViewModel.getExercises(categoryId), fragment));
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        binding.bottomMenu.setMinimumHeight(height);
    }

    @Override
    public void onExerciseSelect(long exerciseId) {

            client.getExerciseByIdForUserId(exerciseId, 1).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Exercise> call, Response<Exercise> response) {

                    workoutViewModel.createEmptyExercise(response.body());
                }

                @Override
                public void onFailure(Call<Exercise> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        this.dismiss();
    }

    private void onBackClick() {
        binding.bottomTitle.setText("Categories");
        binding.bottomBack.setVisibility(View.GONE);
        binding.bottomMenuExercise.setVisibility(View.GONE);
        binding.bottomMenuCategory.setVisibility(View.VISIBLE);
    }
}