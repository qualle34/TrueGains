package com.qualle.truegain.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.UserProfile;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentProfileSecurityPasswordBinding;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.LocalService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSecurityPasswordFragment extends Fragment {

    @Inject
    public BackendClient client;

    @Inject
    public LocalService service;

    @Inject
    public AuthenticationHandler authenticationHandler;

    private FragmentProfileSecurityPasswordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileSecurityPasswordBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);


        binding.securityPasswordButtonBack.setOnClickListener(v -> navController.popBackStack());

        client.getUserProfile(service.getAuthorizationHeader()).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

                if (response.isSuccessful()) {

                    UserProfile dto = response.body();

                }

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                t.printStackTrace();
            }
        });

        binding.securityPasswordCode.setVisibility(View.GONE);
        binding.securityButtonConfirm.setVisibility(View.GONE);

        binding.securityButtonCode.setOnClickListener((t) -> {

            if (isPasswordsValid()) {
                binding.securityPasswordCode.setVisibility(View.VISIBLE);
                binding.securityButtonConfirm.setVisibility(View.VISIBLE);
            }


        });

        binding.securityButtonConfirm.setOnClickListener( (t) -> {

            Toast.makeText(getContext(), "Password Not Successfully changed", Toast.LENGTH_SHORT).show();
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