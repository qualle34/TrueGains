package com.qualle.shapeup.ui.profile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qualle.shapeup.R;
import com.qualle.shapeup.config.ApplicationComponent;
import com.qualle.shapeup.client.BackendClient;
import com.qualle.shapeup.client.api.User;
import com.qualle.shapeup.config.DaggerApplicationComponent;
import com.qualle.shapeup.databinding.FragmentProfileBinding;
import com.qualle.shapeup.model.enums.ChartType;
import com.qualle.shapeup.ui.chart.ChartLineFragment;
import com.qualle.shapeup.ui.menu.BottomMenuFragment;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    @Inject
    public BackendClient client;

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);
        ApplicationComponent component = DaggerApplicationComponent.create();
        component.inject(this);

        binding.profileCardEditProfile.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_profile_fragment_to_nav_profile_data_fragment));

        binding.profileCardSettings.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_profile_fragment_to_nav_settings_fragment));

        binding.profileCardSecurity.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_profile_fragment_to_nav_profile_security_fragment));

        binding.profileCardMeasure.setOnClickListener(v -> {
            new BottomMenuFragment().show(getChildFragmentManager(), null);
        });

        binding.profileButtonBack.setOnClickListener(v -> navController.popBackStack());

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(binding.profileChartWeight.getId(), ChartLineFragment.newInstance("Weight", ChartType.NUMBER, null), null)
                .commit();

        InputStream ims = null; // todo
        try {
            ims = getContext().getAssets().open("philipp.jpg");
            Drawable d = Drawable.createFromStream(ims, null);
            binding.profileImageView.setImageDrawable(d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        client.getUser().enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User dto = response.body();

                binding.profileNameAge.setText(dto.getName() + ", " + dto.getAge());
                binding.profileWorkoutCount.setText("Количество тренировок: " + dto.getWorkoutCount());


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

              t.printStackTrace();
            }
        });

        return binding.getRoot();
    }
}