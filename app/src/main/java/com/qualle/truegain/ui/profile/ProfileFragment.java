package com.qualle.truegain.ui.profile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.User;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentProfileBinding;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.chart.ChartLineFragment;
import com.qualle.truegain.ui.menu.BottomMenuFragment;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    @Inject
    public BackendClient client;

    @Inject
    public LocalService service;

    @Inject
    public AuthenticationHandler authenticationHandler;

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);

        try {
            authenticationHandler.holdAuthentication();
        } catch (Exception e) {
            navController.navigate(R.id.action_nav_profile_fragment_to_nav_greeting_fragment);
            return binding.getRoot();
        }

        binding.profileCardEditProfile.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_profile_fragment_to_nav_profile_data_fragment));

        binding.profileCardSettings.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_profile_fragment_to_nav_settings_fragment));

        binding.profileCardSecurity.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_profile_fragment_to_nav_profile_security_fragment));

        binding.profileCardMeasure.setOnClickListener(v -> {
            BottomMenuFragment.newInstance().show(getChildFragmentManager(), null); // todo add click logic
        });

        binding.profileButtonBack.setOnClickListener(v -> navController.popBackStack());

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(binding.profileChartWeight.getId(), ChartLineFragment.newInstance(null, null), null)
                .commit();

        InputStream ims = null; // todo
        try {
            ims = getContext().getAssets().open("philipp.jpg");
            Drawable d = Drawable.createFromStream(ims, null);
            binding.profileImageView.setImageDrawable(d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        client.getUser(service.getAuthorizationHeader()).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {

                    User dto = response.body();

                    binding.profileNameAge.setText(dto.getName() + ", " + 22);
                    binding.profileWorkoutCount.setText("Workout count: " + 4);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return binding.getRoot();
    }
}