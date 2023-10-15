package com.qualle.truegain.ui.profile;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.qualle.truegain.R;
import com.qualle.truegain.client.InMemoryBackendClient;
import com.qualle.truegain.client.api.User;
import com.qualle.truegain.databinding.FragmentProfileEditBinding;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ProfileEditFragment extends Fragment {

    private FragmentProfileEditBinding binding;

    private User user = InMemoryBackendClient.getUser();
    private Calendar set = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileEditBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        binding.profileEditButtonBack.setOnClickListener(v -> navController.popBackStack());

        initializeFields();
        initializeData();


        return binding.getRoot();
    }


    private void initializeFields() {
        String[] types = {"Male", "Female"};
        AutoCompleteTextView spinner = binding.profileSpinnerGender;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_dropdown_menu, types);
        spinner.setAdapter(adapter);

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            set.set(Calendar.YEAR, year);
            set.set(Calendar.MONTH, monthOfYear);
            set.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);

            String formatter = formmat1.format(toLocalDateTime(set));

            binding.profileEditBirthday.setText(formatter);
        };

        Calendar now = Calendar.getInstance();
        TextInputEditText birthdateEdit = binding.profileEditBirthday;

        birthdateEdit.setOnClickListener((t) -> {
            new DatePickerDialog(getContext(), dateSetListener,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH))
                    .show();
        });
    }

    private void initializeData() {
        binding.profileEditName.setText(user.getName());
        binding.profileSpinnerGender.setListSelection(1);
        binding.profileEditBirthday.setText(user.getBirthday());
        binding.profileEditHeight.setText(user.getHeight() + " cm");

        InputStream ims = null; // todo
        try {
            ims = getContext().getAssets().open("philipp.jpg");
            Drawable d = Drawable.createFromStream(ims, null);
            binding.profileEditImageView.setImageDrawable(d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }


}