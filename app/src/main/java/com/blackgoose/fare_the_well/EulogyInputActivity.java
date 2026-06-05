package com.blackgoose.fare_the_well;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.blackgoose.fare_the_well.Adapters.ImagesPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EulogyInputActivity extends AppCompatActivity {

    ImageView imgMain;
    EditText edtFirstName, edtSecondName, edtLastName, edtBurialLocation;
    EditText edtBirthYear, edtPassingYear, edtEulogy;
    EditText edtAuthorName, edtAuthorPhone;
    ViewPager2 viewPagerImages;
    Button btnProceed;

    Uri mainImageUri;
    List<Uri> galleryUris = new ArrayList<>();
    ImagesPagerAdapter pagerAdapter;

    Button btnPickMainImage, btnPickGalleryImages;

    ActivityResultLauncher<Intent> mainImagePicker;
    ActivityResultLauncher<Intent> galleryImagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eulogy_input_activity);

        imgMain = findViewById(R.id.add_image);
        edtFirstName = findViewById(R.id.firstNameEditText);
        edtSecondName = findViewById(R.id.secondNameEditText);
        edtLastName = findViewById(R.id.lastNameEditText);
        edtBurialLocation = findViewById(R.id.burialLocationEditText);
        edtBirthYear = findViewById(R.id.DateofBirthEditText);
        edtPassingYear = findViewById(R.id.passOnEditText);
        edtEulogy = findViewById(R.id.lifeBiography);
        edtAuthorName = findViewById(R.id.authorNameEditText);
        edtAuthorPhone = findViewById(R.id.phoneEditText);
        viewPagerImages = findViewById(R.id.viewPager);
        btnProceed = findViewById(R.id.proceed_btn);
        btnPickMainImage = findViewById(R.id.select_image_button);
        btnPickGalleryImages = findViewById(R.id.select_images_button);

        // ViewPager Adapter
        pagerAdapter = new ImagesPagerAdapter(this, galleryUris);
        viewPagerImages.setAdapter(pagerAdapter);

        // ---- Pick MAIN Image ----
        mainImagePicker = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        mainImageUri = result.getData().getData();
                        imgMain.setImageURI(mainImageUri);
                    }
                });

        btnPickMainImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            mainImagePicker.launch(intent);
        });

        // ---- Pick MULTIPLE Images ----
        galleryImagePicker = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        galleryUris.clear();

                        if (result.getData().getClipData() != null) {
                            int count = result.getData().getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri uri = result.getData().getClipData().getItemAt(i).getUri();
                                galleryUris.add(uri);
                            }
                        } else {
                            galleryUris.add(result.getData().getData());
                        }
                        pagerAdapter.notifyDataSetChanged();
                    }
                });

        btnPickGalleryImages.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setType("image/*");
            galleryImagePicker.launch(intent);
        });

        // ---- YEAR PICKER for Birth Year ----
        edtBirthYear.setOnClickListener(v -> showYearPicker(edtBirthYear));

        // ---- YEAR PICKER for Passing Year ----
        edtPassingYear.setOnClickListener(v -> showYearPicker(edtPassingYear));

        btnProceed.setOnClickListener(v -> proceedToNext());

    }

    private void showYearPicker(EditText target) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        NumberPicker yearPicker = new NumberPicker(this);
        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(currentYear);
        yearPicker.setValue(currentYear);
        yearPicker.setWrapSelectorWheel(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Year");
        builder.setView(yearPicker);

        builder.setPositiveButton("OK", (dialog, which) -> {
            target.setText(String.valueOf(yearPicker.getValue()));
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private boolean isValidPhone(String phone) {

        phone = phone.trim();

        // Kenya formats:
        // 07XXXXXXXX
        // +2547XXXXXXXX
        // 2547XXXXXXXX

        String pattern = "^(\\+254|254|0)[7][0-9]{8}$";

        return phone.matches(pattern);
    }
    private void proceedToNext() {

        // ---- IMAGE VALIDATION ----
        if (mainImageUri == null) {
            Toast.makeText(this, "Please select a main image", Toast.LENGTH_SHORT).show();
            return;
        }

        if (galleryUris.isEmpty()) {
            Toast.makeText(this, "Please select gallery images", Toast.LENGTH_SHORT).show();
            return;
        }

        // ---- GET INPUTS ----
        String firstName = edtFirstName.getText().toString().trim();
        String secondName = edtSecondName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();
        String burialLocation = edtBurialLocation.getText().toString().trim();
        String birthYear = edtBirthYear.getText().toString().trim();
        String passingYear = edtPassingYear.getText().toString().trim();
        String eulogyText = edtEulogy.getText().toString().trim();
        String authorName = edtAuthorName.getText().toString().trim();
        String authorPhone = edtAuthorPhone.getText().toString().trim();

        // ---- FIELD VALIDATION ----
        if (firstName.isEmpty()) {
            edtFirstName.setError("First name required");
            edtFirstName.requestFocus();
            return;
        }

        if (secondName.isEmpty()) {
            edtSecondName.setError("Second name required");
            edtSecondName.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            edtLastName.setError("Last name required");
            edtLastName.requestFocus();
            return;
        }

        if (burialLocation.isEmpty()) {
            edtBurialLocation.setError("Burial location required");
            edtBurialLocation.requestFocus();
            return;
        }

        if (birthYear.isEmpty()) {
            edtBirthYear.setError("Select birth year");
            return;
        }

        if (passingYear.isEmpty()) {
            edtPassingYear.setError("Select passing year");
            return;
        }

        // ---- YEAR LOGIC VALIDATION ----
        try {
            int birth = Integer.parseInt(birthYear);
            int passing = Integer.parseInt(passingYear);

            if (passing < birth) {
                Toast.makeText(this,
                        "Passing year cannot be before birth year",
                        Toast.LENGTH_LONG).show();
                return;
            }

        } catch (Exception e) {
            Toast.makeText(this,
                    "Invalid year format",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // ---- EULOGY TEXT ----
        if (eulogyText.isEmpty() || eulogyText.length() < 20) {
            edtEulogy.setError("Eulogy must be at least 20 characters");
            edtEulogy.requestFocus();
            return;
        }

        // ---- AUTHOR VALIDATION ----
        if (authorName.isEmpty()) {
            edtAuthorName.setError("Author name required");
            edtAuthorName.requestFocus();
            return;
        }

        if (authorPhone.isEmpty()) {
            edtAuthorPhone.setError("Phone required");
            edtAuthorPhone.requestFocus();
            return;
        }

        // ---- PHONE VALIDATION (Kenya format friendly) ----
        if (!isValidPhone(authorPhone)) {
            edtAuthorPhone.setError("Enter valid phone (e.g. 07XXXXXXXX or +2547XXXXXXXX)");
            edtAuthorPhone.requestFocus();
            return;
        }

        // ---- CONVERT IMAGES ----
        ArrayList<String> galleryImageStrings = new ArrayList<>();
        for (Uri uri : galleryUris) {
            galleryImageStrings.add(uri.toString());
        }

        // ---- MOVE TO NEXT SCREEN ----
        Intent intent = new Intent(EulogyInputActivity.this, ProgramSetActivity.class);

        intent.putExtra("firstName", firstName);
        intent.putExtra("secondName", secondName);
        intent.putExtra("lastName", lastName);
        intent.putExtra("burialLocation", burialLocation);
        intent.putExtra("birthYear", birthYear);
        intent.putExtra("passingYear", passingYear);
        intent.putExtra("eulogyText", eulogyText);
        intent.putExtra("authorName", authorName);
        intent.putExtra("authorPhone", authorPhone);

        intent.putExtra("mainImageUri", mainImageUri.toString());
        intent.putStringArrayListExtra("galleryImages", galleryImageStrings);

        startActivity(intent);
    }

}
