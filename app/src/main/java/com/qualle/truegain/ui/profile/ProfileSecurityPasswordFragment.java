package com.qualle.truegain.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.truegain.client.InMemoryBackendClient;
import com.qualle.truegain.client.api.User;
import com.qualle.truegain.databinding.FragmentProfileSecurityPasswordBinding;

public class ProfileSecurityPasswordFragment extends Fragment {

    private FragmentProfileSecurityPasswordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileSecurityPasswordBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        binding.securityPasswordButtonBack.setOnClickListener(v -> navController.popBackStack());

        User user = InMemoryBackendClient.getUser();


        binding.securityPasswordCode.setVisibility(View.GONE);
        binding.securityButtonConfirm.setVisibility(View.GONE);

        binding.securityButtonCode.setOnClickListener((t) -> {

            if (isPasswordsValid()) {
                binding.securityPasswordCode.setVisibility(View.VISIBLE);
                binding.securityButtonConfirm.setVisibility(View.VISIBLE);
            }


        });

        binding.securityButtonConfirm.setOnClickListener( (t) -> {

            Toast.makeText(getContext(), "Password Successfully changed", Toast.LENGTH_SHORT).show();
            navController.popBackStack();
        });



        return binding.getRoot();
    }

    private boolean isPasswordsValid() {

        boolean valid = true;

        if (binding.securityCurrentPassword.getText() == null
                || binding.securityCurrentPassword.getText().toString().isEmpty()) {
            valid = false;
        }

        if (binding.securityNewPassword.getText() == null
                || binding.securityNewPassword.getText().toString().isEmpty()) {
            valid = false;
        }
        if (binding.securityRepeatNewPassword.getText() == null
                || binding.securityRepeatNewPassword.getText().toString().isEmpty()) {
            valid = false;
        }

        return valid;
    }
}