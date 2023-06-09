package com.qualle.shapeup;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.qualle.shapeup.databinding.FragmentProfileBinding;
import com.qualle.shapeup.model.enums.ChartType;

import java.io.IOException;
import java.io.InputStream;

public class ProfileFragment extends Fragment {
    private static final String USER_PARAM = "user";
    private String user;

    private FragmentProfileBinding binding;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(USER_PARAM, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getString(USER_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        binding.profileCardUserData.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_profile_fragment_to_nav_profile_data_fragment));

        binding.profileCardSettings.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_profile_fragment_to_nav_settings_fragment));

        binding.profileCardSecurity.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_profile_fragment_to_nav_profile_security_fragment));

        ImageView imageView = binding.profileImageView;

        InputStream ims = null;
        try {
            ims = getContext().getAssets().open("philipp.jpg");
            Drawable d = Drawable.createFromStream(ims, null);
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(binding.profileChartWeight.getId(), ChartFragment.newInstance("Weight", ChartType.NUMBER, null), null)
                .commit();

        return binding.getRoot();
    }
}