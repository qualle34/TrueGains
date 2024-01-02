package com.qualle.truegain.ui.profile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.qualle.truegain.R;
import com.qualle.truegain.client.BackendClient;
import com.qualle.truegain.client.ClientModule;
import com.qualle.truegain.client.api.User;
import com.qualle.truegain.config.DaggerApplicationComponent;
import com.qualle.truegain.databinding.FragmentProfileEditBinding;
import com.qualle.truegain.service.AuthenticationHandler;
import com.qualle.truegain.service.LocalService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditFragment extends Fragment {

    private FragmentProfileEditBinding binding;

    @Inject
    public BackendClient client;

    @Inject
    public LocalService service;

    @Inject
    public AuthenticationHandler authenticationHandler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileEditBinding.inflate(inflater, container, false);
        NavController navController = NavHostFragment.findNavController(this);

        DaggerApplicationComponent.builder()
                .clientModule(ClientModule.getInstance(getContext())).build()
                .inject(this);

        try {
            authenticationHandler.holdAuthentication();
        } catch (Exception e) {
            navController.navigate(R.id.action_nav_profile_edit_fragment_to_nav_greeting_fragment);
            return binding.getRoot();
        }


        binding.profileEditButtonBack.setOnClickListener(v -> navController.popBackStack());

        initializeFields();

        try {
            File imagePath = new File(requireContext().getFilesDir(), "images");
            File newFile = new File(imagePath, "user_image.jpg");

            Uri contentUri = FileProvider.getUriForFile(requireContext(), "com.qualle.truegain.provider", newFile);

            InputStream inputStream = requireActivity().getContentResolver().openInputStream(contentUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            binding.profileEditImageView.setImageBitmap(bitmap);

        } catch (IOException e) {
            try {
                InputStream ims = getContext().getAssets().open("base_photo.jpg");
                Drawable d = Drawable.createFromStream(ims, null);
                binding.profileEditImageView.setImageDrawable(d);

            } catch (IOException ex) {
            }
        }

        client.getUser(service.getAuthorizationHeader()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User user = response.body();

                binding.profileEditName.setText(user.getName());
                binding.profileSpinnerGender.setText(user.getGender(), false);
                binding.profileEditBirthday.setText(user.getBirthday());
                binding.profileEditHeight.setText(user.getHeight() + " cm");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


        binding.profileEditBottomSave.setOnClickListener(v -> {

            User user = new User();
            user.setName(binding.profileEditName.getText().toString());
            user.setGender(binding.profileSpinnerGender.getText().toString());
            user.setBirthday(binding.profileEditBirthday.getText().toString());
            user.setHeight(binding.profileEditHeight.getHeight());

            client.saveUser(service.getAuthorizationHeader(), user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                }
            });

            navController.navigate(R.id.action_nav_profile_edit_fragment_to_nav_profile_fragment);
        });

        return binding.getRoot();
    }


    private void initializeFields() {
        String[] types = {"Male", "Female", "Other"};
        AutoCompleteTextView spinner = binding.profileSpinnerGender;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_dropdown_menu, types);
        spinner.setAdapter(adapter);

        Calendar set = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            set.set(Calendar.YEAR, year);
            set.set(Calendar.MONTH, monthOfYear);
            set.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

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

        ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri selectedImageUri = data.getData();
                            displayImage(selectedImageUri);
                            saveImageToInternalStorage(selectedImageUri);
                        }
                    }
                }
        );

        binding.profileEditImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });
    }

    private void saveImageToInternalStorage(Uri imageUri) {
        try {
            InputStream inputStream = requireActivity().getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            File imagePath = new File(requireContext().getFilesDir(), "images");

            if (!imagePath.exists()) {
                if (!imagePath.mkdirs()) {
                    Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            File newFile = new File(imagePath, "user_image.jpg");

            Uri contentUri = FileProvider.getUriForFile(requireContext(), "com.qualle.truegain.provider", newFile);

            OutputStream outputStream = requireContext().getContentResolver().openOutputStream(contentUri, "w");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();

            Toast.makeText(requireContext(), "Image Saved", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayImage(Uri imageUri) {
        try {
            InputStream inputStream = requireActivity().getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            binding.profileEditImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
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