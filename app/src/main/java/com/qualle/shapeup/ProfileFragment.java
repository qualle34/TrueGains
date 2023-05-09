package com.qualle.shapeup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {
    private static final String USER_PARAM = "user";
    private String user;

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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        Fragment fragment = ChartFragment.newInstance(45, 100);

//        ft.add(R.id.profile_weight_chart, fragment, null);
        ft.commit();

        return view;
    }
}