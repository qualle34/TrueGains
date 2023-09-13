package com.qualle.shapeup.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.shapeup.R;
import com.qualle.shapeup.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        binding.settingsButtonBack.setOnClickListener(v -> navController.popBackStack());
        binding.settingsSave.setOnClickListener(v -> navController.popBackStack());

        initializeFields();

        return binding.getRoot();
    }


    private void initializeFields() {
        String[] types = {"Русский", "English"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_dropdown_menu, types);
        binding.settingsSpinnerLanguage.setAdapter(adapter);

        String[] types1 = {"kg", "lb"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), R.layout.item_dropdown_menu, types1);
        binding.settingsSpinnerWeightUnits.setAdapter(adapter1);

        String[] types2 = {"cm", "ft"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), R.layout.item_dropdown_menu, types2);
        binding.settingsSpinnerLengthUnits.setAdapter(adapter2);
    }

}