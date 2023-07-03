package com.qualle.shapeup.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.qualle.shapeup.ui.adapter.RecordRecyclerViewAdapter;
import com.qualle.shapeup.client.InMemoryBackendClient;
import com.qualle.shapeup.databinding.FragmentSaveWorkoutBinding;
import com.qualle.shapeup.model.SaveWorkoutViewModel;
import com.qualle.shapeup.client.api.Record;
import com.qualle.shapeup.ui.listener.BottomMenuDismissHandler;
import com.qualle.shapeup.ui.menu.BottomMenuFragment;

import java.util.ArrayList;
import java.util.List;

public class SaveWorkoutFragment extends Fragment implements BottomMenuDismissHandler {

    private SaveWorkoutViewModel viewModel;

    private FragmentSaveWorkoutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSaveWorkoutBinding.inflate(inflater, container, false);

        NavController navController = NavHostFragment.findNavController(this);

        Record record = new Record();
        record.setType("asfd");
        record.setValue(12);
        record.setExercise(InMemoryBackendClient.getExercises().get(0));

        List<Record> records = new ArrayList<>();
        records.add(record);

        RecyclerView recyclerView = binding.saveWorkoutRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecordRecyclerViewAdapter adapter = new RecordRecyclerViewAdapter(records);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SaveWorkoutViewModel.class);

        binding.saveWorkoutAddExercise.setOnClickListener(v -> {
            new BottomMenuFragment().show(getChildFragmentManager(), "dialog");
        });

        return binding.getRoot();
    }

    @Override
    public void handle() {
        Fragment bottomMenu = getChildFragmentManager().findFragmentByTag("dialog");

        if (bottomMenu instanceof BottomSheetDialogFragment) {
            ((BottomSheetDialogFragment) bottomMenu).dismiss();
        }
    }
}