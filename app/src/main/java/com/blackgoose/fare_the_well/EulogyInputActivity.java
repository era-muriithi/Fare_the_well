package com.blackgoose.fare_the_well;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class EulogyInputActivity extends AppCompatActivity {
    EditText dateofbirth_date, passon_date, firstName, secondName, lastName, burialLocation, earlyLife, education, work, family, finalMoment, authorName, authorPhone;
    ImageView imageView;
    private ArrayList<Uri> imageUris = new ArrayList<>();
    private List<String> imageUrls;
    private ImageSlider imageSlider;
    ProgressDialog progressDialog;
    Uri ImageUri;
    Button upload_button, select_images_btn, add_program_button, proceed_button;
    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    private static final int PICK_IMAGES_REQUEST = 1;
    private List<SlideModel> slideModels = new ArrayList<>();
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eulogy_input_activity);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Uploading");
        progressDialog.setCanceledOnTouchOutside(false);

        imageUris = new ArrayList<>();
        imageUrls = new ArrayList<>();

        imageSlider = findViewById(R.id.add_images);
        dateofbirth_date = findViewById(R.id.DateofBirthEditText);
        passon_date = findViewById(R.id.passOnEditText);
        firstName = findViewById(R.id.firstNameEditText);
        secondName = findViewById(R.id.secondNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        burialLocation = findViewById(R.id.burialLocationEditText);
        earlyLife = findViewById(R.id.earlylife_biographyEditText);
        education = findViewById(R.id.education_biographyEditText);
        work = findViewById(R.id.work_biographyEditText);
        family = findViewById(R.id.family_biographyEditText);
        finalMoment = findViewById(R.id.final_biographyEditText);
        authorName = findViewById(R.id.authorNameEditText);
        authorPhone = findViewById(R.id.phoneEditText);
        select_images_btn = findViewById(R.id.select_images_button);
        proceed_button = findViewById(R.id.proceed_btn);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dateofbirth_date.setOnClickListener(v -> {
            final Calendar mCalendar = Calendar.getInstance();
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);
            int month = mCalendar.get(Calendar.MONTH);
            int year = mCalendar.get(Calendar.YEAR);
            DatePickerDialog picker = new DatePickerDialog(EulogyInputActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> dateofbirth_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            picker.getDatePicker();
            picker.show();
        });

        passon_date.setOnClickListener(v -> {
            final Calendar mCalendar = Calendar.getInstance();
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);
            int month = mCalendar.get(Calendar.MONTH);
            int year = mCalendar.get(Calendar.YEAR);
            DatePickerDialog picker = new DatePickerDialog(EulogyInputActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> passon_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            picker.getDatePicker();
            picker.show();
        });

        select_images_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        upload_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                isAllFieldsChecked = CheckAllFields();

                if (isAllFieldsChecked) {

                    progressDialog.show();
                    final StorageReference reference = firebaseStorage.getReference().child("eulogyPictures")
                            .child(System.currentTimeMillis() + "");

                    reference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    EulogyModel model = new EulogyModel();

                                    model.setImageUrls(imageUrls);
                                    model.setFirstName(Objects.requireNonNull(firstName.getText()).toString());
                                    model.setSecondName(Objects.requireNonNull(secondName.getText()).toString());
                                    model.setLastName(Objects.requireNonNull(lastName.getText()).toString());
                                    model.setBurialLocation(Objects.requireNonNull(burialLocation.getText()).toString());
                                    model.setDateOfBirth(Objects.requireNonNull(dateofbirth_date.getText()).toString());
                                    model.setPassingOnDate(Objects.requireNonNull(passon_date.getText()).toString());
                                    model.setEarly(earlyLife.getText().toString());
                                    model.setEducation(education.getText().toString());
                                    model.setWork(work.getText().toString());
                                    model.setFamily(family.getText().toString());
                                    model.setFinalMoment(finalMoment.getText().toString());
                                    model.setAuthorContact(Integer.parseInt(String.valueOf(authorPhone.getText().toString())));
                                    model.setAuthorName(authorName.getText().toString());
                                    model.setUserUid(uid);

                                    database.getReference().child("Eulogies").push().setValue(model)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(EulogyInputActivity.this, "Eulogies Uploaded Successfully", Toast.LENGTH_LONG).show();
                                                    progressDialog.dismiss();
                                                    resetInputstoNull();

                                                    openUserProfile();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(EulogyInputActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();

                                                }

                                            });
                                }

                            });

                        }
                    });
                }
                }

        });

        add_program_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProgramActivity();
            }
        });

        proceed_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    gotoProgramActivity();
                }

            }
        });


    }

    private void gotoProgramActivity() {
        Intent intent = new Intent(this, ProgramSetActivity.class);
        intent.putExtra("firstName", firstName.getText().toString());
        intent.putExtra("secondName", secondName.getText().toString());
        intent.putExtra("lastName", lastName.getText().toString());
        intent.putExtra("dob", dateofbirth_date.getText().toString());
        intent.putExtra("passingOn", passon_date.getText().toString());
        intent.putExtra("burialLocation", burialLocation.getText().toString());
        intent.putExtra("earlyLife", earlyLife.getText().toString());
        intent.putExtra("education", education.getText().toString());
        intent.putExtra("family", family.getText().toString());
        intent.putExtra("work", work.getText().toString());
        intent.putExtra("finalMoments", finalMoment.getText().toString());
        intent.putExtra("userUid", FirebaseAuth.getInstance().getCurrentUser().getUid());

        intent.putExtra("authorName", authorName.getText().toString());
        intent.putExtra("authorContact", authorPhone.getText().toString());
        intent.putParcelableArrayListExtra("images", imageUris);
        startActivity(intent);
    }

    private void openProgramActivity() {
        Intent intent = new Intent(this, ProgramSetActivity.class);
        startActivity(intent);
    }

    private boolean CheckAllFields() {
        if (imageUris == null) {
            Toast.makeText(EulogyInputActivity.this, "Select Image", Toast.LENGTH_LONG).show();
            return false;
        }

        if (dateofbirth_date.length() == 0) {
            Toast.makeText(EulogyInputActivity.this, "Input date of birth", Toast.LENGTH_LONG).show();
            return false;
        }

        if (passon_date.length() == 0) {
            Toast.makeText(EulogyInputActivity.this, "Input pass on date", Toast.LENGTH_LONG).show();
            return false;
        }

        if (firstName.length() == 0) {
            Toast.makeText(EulogyInputActivity.this, "Enter First name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (secondName.length() == 0) {
            Toast.makeText(EulogyInputActivity.this, "Enter second name", Toast.LENGTH_LONG).show();
            return false;
        }

            if (lastName.length() == 0) {
                Toast.makeText(EulogyInputActivity.this, "Enter last name", Toast.LENGTH_LONG).show();
                return false;
            }

                if (burialLocation.length() == 0) {
                    Toast.makeText(EulogyInputActivity.this, "Enter burial location", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (earlyLife.length() == 0) {
                    Toast.makeText(EulogyInputActivity.this, "Enter early life biography", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (education.length() == 0) {
                    Toast.makeText(EulogyInputActivity.this, "Enter education biography", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (family.length() == 0) {
                    Toast.makeText(EulogyInputActivity.this, "Enter family biography", Toast.LENGTH_LONG).show();
                    return false;
                }

                if (work.length() == 0) {
                    Toast.makeText(EulogyInputActivity.this, "Enter work biography", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (finalMoment.length() == 0) {
                    Toast.makeText(EulogyInputActivity.this, "Enter final moments biography", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (authorName.length() == 0) {
                    Toast.makeText(EulogyInputActivity.this, "Enter author name", Toast.LENGTH_LONG).show();
                    return false;

        } else if (authorPhone.length() < 10 || authorPhone.length() > 10) {
            Toast.makeText(EulogyInputActivity.this, "Enter valid phone number", Toast.LENGTH_LONG).show();
            return false;
        }

        // after all validation return true.
        return true;
    }


    private void resetInputstoNull() {
        imageView.setImageURI(null);
        dateofbirth_date.setText(null);
        passon_date.setText(null);
        firstName.setText(null);
        secondName.setText(null);
        lastName.setText(null);
        burialLocation.setText(null);
        earlyLife.setText(null);
        work.setText(null);
        family.setText(null);
        education.setText(null);
        finalMoment.setText(null);
        authorName.setText(null);
        authorPhone.setText(null);
    }

    private void openUserProfile() {

            Intent intent = new Intent(this, UserPageActivity.class);
            startActivity(intent);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGES_REQUEST);
    }
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUris.clear();
            slideModels.clear();

            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUris.add(imageUri);
                    slideModels.add(new SlideModel(imageUri.toString(), ScaleTypes.CENTER_CROP));
                }
            } else {
                Uri imageUri = data.getData();
                imageUris.add(imageUri);
                slideModels.add(new SlideModel(imageUri.toString(), ScaleTypes.CENTER_CROP));
            }
            imageSlider.setImageList(slideModels);
        }
    }
}
