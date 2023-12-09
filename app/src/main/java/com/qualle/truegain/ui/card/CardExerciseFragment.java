package com.qualle.truegain.ui.card;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.qualle.truegain.R;
import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.client.api.Record;
import com.qualle.truegain.databinding.FragmentCardExerciseBinding;
import com.qualle.truegain.model.local.ExerciseDetailsProto;
import com.qualle.truegain.util.AssetManagerUtil;
import com.qualle.truegain.util.SizeConverter;

import java.util.List;

public class CardExerciseFragment extends Fragment {

    private static final String ARG_EXERCISE = "exercise";

    private FragmentCardExerciseBinding binding;

    private Exercise exercise;

    private CardExerciseFragment() {
    }

    public static CardExerciseFragment newInstance(Exercise ex) {
        CardExerciseFragment fragment = new CardExerciseFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXERCISE, ex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU) // todo min api 33
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exercise = getArguments().getSerializable(ARG_EXERCISE, Exercise.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCardExerciseBinding.inflate(inflater, container, false);

        binding.recordCardEquipmentTitle.setText(exercise.getEquipment());
        binding.recordCardExerciseTitle.setText(exercise.getName());
        binding.recordCardIcon.setImageDrawable(AssetManagerUtil.getImage(getContext(), exercise.getIconLink()));

        List<Record> records = exercise.getRecords();

        LinearLayout weights = binding.recordCardWeights;
        LinearLayout reps = binding.recordCardReps;

        for (int i = 0; i < records.size(); i++) {
            Record record = records.get(i);

            TextView weight = new TextView(getContext());
            weight.setWidth(SizeConverter.pxToDp(getContext(), 65));
            weight.setText(record.getWeight() + " kg");
            weight.setTextAppearance(R.style.TextAppearance_TrueGain_SecondaryLight);
            weight.setTextSize(14);
            weight.setGravity(Gravity.CENTER);

            TextView rep = new TextView(getContext());
            rep.setWidth(SizeConverter.pxToDp(getContext(), 65));
            rep.setText("x" + record.getReps());
            rep.setTextSize(12);
            rep.setTextAppearance(R.style.TextAppearance_TrueGain_GrayLight);
            rep.setGravity(Gravity.CENTER);

            weights.addView(weight);
            reps.addView(rep);
        }


        return binding.getRoot();
    }
}