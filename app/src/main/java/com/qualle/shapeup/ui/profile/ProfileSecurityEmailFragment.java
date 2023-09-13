package com.qualle.shapeup.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qualle.shapeup.R;
import com.qualle.shapeup.client.InMemoryBackendClient;
import com.qualle.shapeup.client.api.User;
import com.qualle.shapeup.databinding.FragmentProfileSecurityBinding;
import com.qualle.shapeup.databinding.FragmentProfileSecurityEmailBinding;


public class ProfileSecurityEmailFragment extends Fragment {

    private FragmentProfileSecurityEmailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileSecurityEmailBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        binding.securityEmailButtonBack.setOnClickListener(v -> navController.popBackStack());

        User user = InMemoryBackendClient.getUser();



        binding.securityCurrentEmail.setText(user.getEmail());
        binding.securityEmailCode.setVisibility(View.GONE);
        binding.securityButtonConfirm.setVisibility(View.GONE);

        binding.securityButtonCode.setOnClickListener( (t) -> {

            binding.securityEmailCode.setVisibility(View.VISIBLE);
            binding.securityButtonConfirm.setVisibility(View.VISIBLE);

        });



        binding.securityButtonConfirm.setOnClickListener( (t) -> {

            Toast.makeText(getContext(), "Email Successfully changed", Toast.LENGTH_SHORT).show();
            navController.popBackStack();
        });


        return binding.getRoot();
    }
}