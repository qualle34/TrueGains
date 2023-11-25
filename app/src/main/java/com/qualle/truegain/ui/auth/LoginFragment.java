package com.qualle.truegain.ui.auth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.LoginPasswordAuthentication;
import com.qualle.truegain.client.api.Token;
import com.qualle.truegain.client.api.TokenClaims;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentLoginBinding;
import com.qualle.truegain.model.local.LocalUser;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.util.DateFormatterUtil;

import java.util.Base64;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Inject
    public LocalService service;

    @Inject
    public BackendClient client;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        Context context = binding.getRoot().getContext();

        NavController navController = NavHostFragment.findNavController(this);
        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);

        binding.loginButtonBack.setOnClickListener(v -> navController.popBackStack());


        binding.buttonLogin.setOnClickListener(v -> {

                    String password = binding.editPassword.getText().toString();
                    String login = binding.editLogin.getText().toString();

                    if (!isLoginValid(login)) {
                        return;
                    }

                    if (!isPasswordValid(password)) {
                        return;
                    }

                    LoginPasswordAuthentication a = new LoginPasswordAuthentication();
                    a.setLogin(login);
                    a.setPassword(password);

                    client.login(a).enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, Response<Token> response) {

                            Token token = response.body();

                            if (!response.isSuccessful() || token == null) {
                                Toast.makeText(context, "Something wrong, try again later...", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            byte[] decodedBytes = Base64.getDecoder().decode(token.getAccessToken().split("\\.")[1]);
                            String decodedString = new String(decodedBytes);
                            Gson gson = new Gson();
                            TokenClaims claims = gson.fromJson(decodedString, TokenClaims.class);


                            LocalUser user = new LocalUser();
                            user.setId(claims.getUid());
                            user.setAccessToken(token.getAccessToken());
                            user.setAccessTokenExpiredAt(DateFormatterUtil.fromApiDate(token.getAccessTokenExpiredAt()));
                            user.setRefreshToken(token.getRefreshToken());
                            user.setRefreshTokenExpiredAt(DateFormatterUtil.fromApiDate(token.getRefreshTokenExpiredAt()));
                            service.saveUser(user);

                            navController.navigate(R.id.action_nav_login_fragment_to_nav_main_fragment);
                        }

                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {
                            Toast.makeText(context, "Something wrong, check your internet connection", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                }
        );

        return binding.getRoot();

    }


    private boolean isLoginValid(String login) {
        Context context = binding.getRoot().getContext();

//        if (!login.isEmpty() && login.length() >= 8) { todo
        if (!login.isEmpty() && login.length() >= 4) {
            return true;

        } else {
            Toast.makeText(context, "Enter valid login", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isPasswordValid(String password) {
        Context context = binding.getRoot().getContext();

//        if (!password.isEmpty() && password.length() >= 8) { todo
        if (!password.isEmpty() && password.length() >= 4) {
            return true;

        } else {
            Toast.makeText(context, "Enter valid password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}

