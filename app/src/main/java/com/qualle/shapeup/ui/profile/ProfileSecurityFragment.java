package com.qualle.shapeup.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.shapeup.R;
import com.qualle.shapeup.client.InMemoryBackendClient;
import com.qualle.shapeup.client.api.User;
import com.qualle.shapeup.databinding.FragmentProfileSecurityBinding;

public class ProfileSecurityFragment extends Fragment {

    private FragmentProfileSecurityBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileSecurityBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        binding.profileSecurityButtonBack.setOnClickListener(v -> navController.popBackStack());

        User user = InMemoryBackendClient.getUser();

        binding.profileSecurityEmail.setText(user.getEmail());
        binding.profileSecurityPassword.setText("* * * * * * * * * *");

        binding.profileSecurityEmail.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_profile_security_fragment_to_nav_profile_security_email_fragment));

        binding.profileSecurityPassword.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_profile_security_fragment_to_nav_profile_security_password_fragment));

        binding.profileSecurityLogout.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_settings_fragment_to_nav_greeting_fragment));


        return binding.getRoot();
    }
}