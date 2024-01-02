package com.qualle.truegain.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.UserProfile;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentProfileSecurityEmailBinding;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.LocalService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileSecurityEmailFragment extends Fragment {

    @Inject
    public BackendClient client;

    @Inject
    public LocalService service;

    @Inject
    public AuthenticationHandler authenticationHandler;


    private FragmentProfileSecurityEmailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileSecurityEmailBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);


        binding.securityEmailButtonBack.setOnClickListener(v -> navController.popBackStack());

        client.getUserProfile(service.getAuthorizationHeader()).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

                if (response.isSuccessful()) {

                    UserProfile dto = response.body();
                    binding.securityCurrentEmail.setText(dto.getUser().getEmail());

                }

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                t.printStackTrace();
            }
        });


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