package com.qualle.truegain.ui.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.UserProfile;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentProfileBinding;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.LocalService;
import com.qualle.truegain.ui.chart.ChartLineFragment;
import com.qualle.truegain.util.DateFormatterUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;

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
            navController.navigate(R.id.action_nav_profile_fragment_to_nav_measure_list_fragment);
        });

        binding.profileButtonBack.setOnClickListener(v -> navController.popBackStack());

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(binding.profileChartWeight.getId(), ChartLineFragment.newInstance(null, null), null)
                .commit();

        try {
            File imagePath = new File(requireContext().getFilesDir(), "images");
            File newFile = new File(imagePath, "user_image.jpg");

            Uri contentUri = FileProvider.getUriForFile(requireContext(), "com.qualle.truegain.provider", newFile);

            InputStream inputStream = requireActivity().getContentResolver().openInputStream(contentUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            binding.profileImageView.setImageBitmap(bitmap);

        } catch (IOException e) {
            try {
                InputStream ims = getContext().getAssets().open("base_photo.jpg");
                Drawable d = Drawable.createFromStream(ims, null);
                binding.profileImageView.setImageDrawable(d);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


        client.getUserProfile(service.getAuthorizationHeader()).enqueue(new Callback<>() {

            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

                if (response.isSuccessful()) {

                    UserProfile dto = response.body();

                    binding.profileNameAge.setText(dto.getUser().getName() + ", " + Period.between(DateFormatterUtil.fromApiSimpleDate(dto.getUser().getBirthday()), LocalDate.now()).getYears());
                    binding.profileWorkoutCount.setText("Workout count: " + dto.getWorkoutsCount());
                    binding.profileVolume.setText(dto.getTotalLoad() + " Kg");
                }

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return binding.getRoot();
    }
}