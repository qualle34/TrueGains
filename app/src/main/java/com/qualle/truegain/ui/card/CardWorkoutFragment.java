package com.qualle.truegain.ui.card;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.qualle.truegain.R;
import com.qualle.truegain.client.api.SimpleWorkout;
import com.qualle.truegain.databinding.FragmentCardWorkoutBinding;
import com.qualle.truegain.util.DateFormatterUtil;

public class CardWorkoutFragment extends Fragment {

    private static final String ARG_ID = "id";
    private static final String ARG_WORKOUT = "workout";

    private FragmentCardWorkoutBinding binding;

    private SimpleWorkout workout;

    public CardWorkoutFragment() {
    }

    public static CardWorkoutFragment newInstance(SimpleWorkout workout) {
        CardWorkoutFragment fragment = new CardWorkoutFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WORKOUT, workout);
        fragment.setArguments(args);
        return fragment;
    }

    public static CardWorkoutFragment oldInstance(CardWorkoutFragment fragment, SimpleWorkout newWorkout) {
        fragment.workout = newWorkout;
        return fragment;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            workout = getArguments().getSerializable(ARG_WORKOUT, SimpleWorkout.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCardWorkoutBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        binding.workoutCardUpperText.setText(DateFormatterUtil.formatApiDate(workout.getDate()));
        binding.workoutCardMiddleText.setText(workout.getExerciseCount() + " Exercises");

        Bundle bundle = new Bundle();
        bundle.putLong(ARG_ID, workout.getId());

        binding.mainWorkoutCard.setOnClickListener(v ->
                navController.navigate(R.id.action_nav_main_fragment_to_nav_workout_details_fragment, bundle));

        return binding.getRoot();
    }

}