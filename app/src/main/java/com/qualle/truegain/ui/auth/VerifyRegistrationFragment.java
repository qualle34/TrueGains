package com.qualle.truegain.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.ConfirmRegistration;
import com.qualle.truegain.client.api.Token;
import com.qualle.truegain.client.api.TokenClaims;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentVerifyRegistrationBinding;
import com.qualle.truegain.model.local.LocalUser;
import com.qualle.truegain.service.ErrorHandler;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.util.DateFormatterUtil;

import java.util.Base64;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyRegistrationFragment extends Fragment {

    private FragmentVerifyRegistrationBinding binding;

    @Inject
    public LocalService service;

    @Inject
    public BackendClient client;

    @Inject
    public ErrorHandler errorHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerifyRegistrationBinding.inflate(inflater, container, false);

        NavController navController = NavHostFragment.findNavController(this);
        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);
        binding.verifyButtonBack.setOnClickListener(v -> navController.popBackStack());


        binding.buttonVerify.setOnClickListener(v -> {

                    ConfirmRegistration registration = new ConfirmRegistration();
                    registration.setCode(Integer.parseInt(binding.verifyEditCode.getText().toString()));
                    registration.setToken(service.getTemporaryToken());

                    client.confirmRegistration(registration).enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, Response<Token> response) {

                            Token token = response.body();

                            if (!response.isSuccessful() || token == null) {
                                errorHandler.handle(getContext(), response.errorBody());
                                return;
                            }

                            service.saveAuthToken(token);

                            navController.navigate(R.id.action_nav_verify_registration_fragment_to_nav_main_fragment);
                        }

                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {
                            Toast.makeText(getContext(), "Something wrong, check your internet connection", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                }
        );

        return binding.getRoot();
    }
}