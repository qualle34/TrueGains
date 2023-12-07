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

import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.LoginPasswordAuthentication;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentLoginBinding;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.LocalService;

import javax.inject.Inject;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    @Inject
    public LocalService service;

    @Inject
    public BackendClient client;

    @Inject
    public AuthenticationHandler handler;

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

                    try {
                        handler.login(a);
                        navController.navigate(R.id.action_nav_login_fragment_to_nav_main_fragment);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Something wrong, try again later...", Toast.LENGTH_SHORT).show();
                    }
                }
        );
//
//        try {
//            handler.holdAuthentication();
//            navController.navigate(R.id.action_nav_login_fragment_to_nav_main_fragment);
//
//        } catch (Exception ignored) {
//            // need login
//        }

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

