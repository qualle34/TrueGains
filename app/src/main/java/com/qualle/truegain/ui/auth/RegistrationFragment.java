package com.qualle.truegain.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.NewRegistration;
import com.qualle.truegain.client.api.TemporaryToken;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentRegistrationBinding;
import com.qualle.truegain.service.ErrorHandler;
import com.qualle.truegain.service.LocalService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;

    @Inject
    public LocalService service;

    @Inject
    public BackendClient client;

    @Inject
    public ErrorHandler errorHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);

        NavController navController = NavHostFragment.findNavController(this);
        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);


        binding.registrationButtonNext.setOnClickListener(v -> {

                    NewRegistration registration = new NewRegistration();
                    registration.setName(binding.registrationEditName.getText().toString());
                    registration.setLogin(binding.registrationEditName.getText().toString());
                    registration.setEmail(binding.registrationEditEmail.getText().toString());
                    registration.setPassword(binding.registrationEditPassword.getText().toString());

                    client.registerNewUser(registration).enqueue(new Callback<TemporaryToken>() {
                        @Override
                        public void onResponse(Call<TemporaryToken> call, Response<TemporaryToken> response) {

                            if (!response.isSuccessful()) {
                                errorHandler.handle(getContext(), response.errorBody());
                                return;
                            }

                            service.saveTemporaryToken(response.body().getToken());

                            navController.navigate(R.id.action_nav_registration_fragment_to_nav_verify_registration_fragment);
                        }

                        @Override
                        public void onFailure(Call<TemporaryToken> call, Throwable t) {
                            Toast.makeText(getContext(), "Something wrong, check your internet connection", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                }
        );

        return binding.getRoot();
    }
}