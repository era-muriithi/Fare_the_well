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


    private void proceedToNext() {

        if (mainImageUri == null) {
            Toast.makeText(this, "Please select a main image", Toast.LENGTH_SHORT).show();
            return;
        }

        if (galleryUris.isEmpty()) {
            Toast.makeText(this, "Please select gallery images", Toast.LENGTH_SHORT).show();
            return;
        }

        // Collect all data from EditTexts
        String firstName = edtFirstName.getText().toString().trim();
        String secondName = edtSecondName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();
        String burialLocation = edtBurialLocation.getText().toString().trim();
        String birthYear = edtBirthYear.getText().toString().trim();
        String passingYear = edtPassingYear.getText().toString().trim();
        String eulogyText = edtEulogy.getText().toString().trim();
        String authorName = edtAuthorName.getText().toString().trim();
        String authorPhone = edtAuthorPhone.getText().toString().trim();

        // Convert Uri list to Strings for the Intent
        ArrayList<String> galleryImageStrings = new ArrayList<>();
        for (Uri uri : galleryUris) {
            galleryImageStrings.add(uri.toString());
        }

        // Send to next activity
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
