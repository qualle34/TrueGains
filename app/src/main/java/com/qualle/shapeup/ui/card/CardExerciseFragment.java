package com.qualle.shapeup.ui.card;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.qualle.shapeup.R;
import com.qualle.shapeup.databinding.FragmentCardExerciseBinding;
import com.qualle.shapeup.model.local.ExerciseDetailsProto;
import com.qualle.shapeup.util.SizeConverter;

import java.util.List;

public class CardExerciseFragment extends Fragment {

    private static final String ARG_EXERCISE = "exercise";

    private FragmentCardExerciseBinding binding;

    private ExerciseDetailsProto exercise;

    private CardExerciseFragment() {
    }

    public static CardExerciseFragment newInstance(ExerciseDetailsProto exercise) {
        CardExerciseFragment fragment = new CardExerciseFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXERCISE, exercise);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU) // todo min api 33
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exercise = getArguments().getSerializable(ARG_EXERCISE, ExerciseDetailsProto.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCardExerciseBinding.inflate(inflater, container, false);

        binding.recordCardTitle.setText(exercise.getTitle());


        List<ExerciseDetailsProto.RecordDetailsProto> records = exercise.getRecords();

        LinearLayout weights = binding.recordCardWeights;
        LinearLayout reps = binding.recordCardReps;

        for (int i = 0; i < records.size(); i++) {
            ExerciseDetailsProto.RecordDetailsProto record = records.get(i);

            TextView weight = new TextView(getContext());
            weight.setWidth(SizeConverter.pxToDp(getContext(), 65));
            weight.setText(record.getWeight() + " kg");
            weight.setTextAppearance(R.style.TextAppearance_ShapeUp_SecondaryLight);
            weight.setTextSize(14);
            weight.setGravity(Gravity.CENTER);

            TextView rep = new TextView(getContext());
            rep.setWidth(SizeConverter.pxToDp(getContext(), 65));
            rep.setText("x" + record.getReps());
            rep.setTextSize(12);
            rep.setTextAppearance(R.style.TextAppearance_ShapeUp_GrayLight);
            rep.setGravity(Gravity.CENTER);

            weights.addView(weight);
            reps.addView(rep);
        }


        return binding.getRoot();
    }
}