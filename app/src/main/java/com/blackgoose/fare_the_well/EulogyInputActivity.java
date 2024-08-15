//package com.blackgoose.fare_the_well;
//
//import android.app.DatePickerDialog;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.blackgoose.fare_the_well.Models.EulogyModel;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.util.Calendar;
//import java.util.Objects;
//
//public class EulogyInputActivity extends AppCompatActivity {
//    EditText dateofbirth_date, passon_date, firstName, secondName, lastName, burialLocation, earlyLife, education, work, family, finalMoment, authorName, authorPhone;
//    ImageView imageView;
//    int SELECT_PICTURE = 200;
//    ProgressDialog progressDialog;
//    Uri ImageUri;
//    Button upload_button;
//    FirebaseDatabase database;
//    FirebaseStorage firebaseStorage;
//    boolean isAllFieldsChecked = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.eulogy_input_activity);
//
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Please wait...");
//        progressDialog.setCancelable(false);
//        progressDialog.setTitle("Uploading");
//        progressDialog.setCanceledOnTouchOutside(false);
//
//        imageView = findViewById(R.id.deceased_image);
//        dateofbirth_date = findViewById(R.id.DateofBirthEditText);
//        passon_date = findViewById(R.id.passOnEditText);
//        firstName = findViewById(R.id.firstNameEditText);
//        secondName = findViewById(R.id.secondNameEditText);
//        lastName = findViewById(R.id.lastNameEditText);
//        burialLocation = findViewById(R.id.burialLocationEditText);
//        earlyLife = findViewById(R.id.earlylife_biographyEditText);
//        education = findViewById(R.id.education_biographyEditText);
//        work = findViewById(R.id.work_biographyEditText);
//        family = findViewById(R.id.family_biographyEditText);
//        finalMoment = findViewById(R.id.final_biographyEditText);
//        authorName = findViewById(R.id.authorNameEditText);
//        authorPhone = findViewById(R.id.phoneEditText);
//        upload_button = findViewById(R.id.upload_btn);
//
//        database = FirebaseDatabase.getInstance();
//        firebaseStorage = FirebaseStorage.getInstance();
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        dateofbirth_date.setOnClickListener(v -> {
//            final Calendar mCalendar = Calendar.getInstance();
//            int day = mCalendar.get(Calendar.DAY_OF_MONTH);
//            int month = mCalendar.get(Calendar.MONTH);
//            int year = mCalendar.get(Calendar.YEAR);
//            DatePickerDialog picker = new DatePickerDialog(EulogyInputActivity.this,
//                    (view, year1, monthOfYear, dayOfMonth) -> dateofbirth_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
//            picker.getDatePicker();
//            picker.show();
//        });
//
//        passon_date.setOnClickListener(v -> {
//            final Calendar mCalendar = Calendar.getInstance();
//            int day = mCalendar.get(Calendar.DAY_OF_MONTH);
//            int month = mCalendar.get(Calendar.MONTH);
//            int year = mCalendar.get(Calendar.YEAR);
//            DatePickerDialog picker = new DatePickerDialog(EulogyInputActivity.this,
//                    (view, year1, monthOfYear, dayOfMonth) -> passon_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
//            picker.getDatePicker();
//            picker.show();
//        });
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseImage();
//            }
//        });
//
//        upload_button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                isAllFieldsChecked = CheckAllFields();
//
//                if (isAllFieldsChecked) {
//
//                    progressDialog.show();
//                    final StorageReference reference = firebaseStorage.getReference().child("eulogyPictures")
//                            .child(System.currentTimeMillis() + "");
//
//                    reference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    EulogyModel model = new EulogyModel();
//                                    model.setDeceasedPicture(uri.toString());
//                                    model.setDeceasedFname(Objects.requireNonNull(firstName.getText()).toString());
//                                    model.setDeceasedSname(Objects.requireNonNull(secondName.getText()).toString());
//                                    model.setDeceasedLname(Objects.requireNonNull(lastName.getText()).toString());
//                                    model.setBurialLocation(Objects.requireNonNull(burialLocation.getText()).toString());
//                                    model.setDateOfBirth(Objects.requireNonNull(dateofbirth_date.getText()).toString());
//                                    model.setDateOfDeath(Objects.requireNonNull(passon_date.getText()).toString());
//                                    model.setEarlylifeBiography(earlyLife.getText().toString());
//                                    model.setEducationBiography(education.getText().toString());
//                                    model.setWorkBiography(work.getText().toString());
//                                    model.setFamilyBiography(family.getText().toString());
//                                    model.setFinalMoments(finalMoment.getText().toString());
//                                    model.setAuthorContact(Integer.parseInt(String.valueOf(authorPhone.getText().toString())));
//                                    model.setAuthorName(authorName.getText().toString());
//                                    model.setUserUid(uid);
//
//                                    database.getReference().child("Eulogies").push().setValue(model)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void unused) {
//                                                    Toast.makeText(EulogyInputActivity.this, "Eulogies Uploaded Successfully", Toast.LENGTH_LONG).show();
//                                                    progressDialog.dismiss();
//                                                    resetInputstoNull();
//
//                                                    openUserProfile();
//                                                }
//                                            }).addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Toast.makeText(EulogyInputActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
//
//                                                }
//
//                                            });
//                                }
//
//                            });
//
//                        }
//                    });
//                }
//                }
//
//        });
//
//
//    }
//
//    private boolean CheckAllFields() {
//        if (ImageUri == null) {
//            Toast.makeText(EulogyInputActivity.this, "Select Image", Toast.LENGTH_LONG).show();
//            return false;
//        }
//
//        if (dateofbirth_date.length() == 0) {
//            Toast.makeText(EulogyInputActivity.this, "Input date of birth", Toast.LENGTH_LONG).show();
//            return false;
//        }
//
//        if (passon_date.length() == 0) {
//            Toast.makeText(EulogyInputActivity.this, "Input pass on date", Toast.LENGTH_LONG).show();
//            return false;
//        }
//
//        if (firstName.length() == 0) {
//            Toast.makeText(EulogyInputActivity.this, "Enter First name", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        if (secondName.length() == 0) {
//            Toast.makeText(EulogyInputActivity.this, "Enter second name", Toast.LENGTH_LONG).show();
//            return false;
//        }
//
//            if (lastName.length() == 0) {
//                Toast.makeText(EulogyInputActivity.this, "Enter last name", Toast.LENGTH_LONG).show();
//                return false;
//            }
//
//                if (burialLocation.length() == 0) {
//                    Toast.makeText(EulogyInputActivity.this, "Enter burial location", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//                if (earlyLife.length() == 0) {
//                    Toast.makeText(EulogyInputActivity.this, "Enter early life biography", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                if (education.length() == 0) {
//                    Toast.makeText(EulogyInputActivity.this, "Enter education biography", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                if (family.length() == 0) {
//                    Toast.makeText(EulogyInputActivity.this, "Enter family biography", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//
//                if (work.length() == 0) {
//                    Toast.makeText(EulogyInputActivity.this, "Enter work biography", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//                if (finalMoment.length() == 0) {
//                    Toast.makeText(EulogyInputActivity.this, "Enter final moments biography", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//                if (authorName.length() == 0) {
//                    Toast.makeText(EulogyInputActivity.this, "Enter author name", Toast.LENGTH_LONG).show();
//                    return false;
//
//        } else if (authorPhone.length() < 10 || authorPhone.length() > 10) {
//            Toast.makeText(EulogyInputActivity.this, "Enter valid phone number", Toast.LENGTH_LONG).show();
//            return false;
//        }
//
//        // after all validation return true.
//        return true;
//    }
//
//
//    private void resetInputstoNull() {
//        imageView.setImageURI(null);
//        dateofbirth_date.setText(null);
//        passon_date.setText(null);
//        firstName.setText(null);
//        secondName.setText(null);
//        lastName.setText(null);
//        burialLocation.setText(null);
//        earlyLife.setText(null);
//        work.setText(null);
//        family.setText(null);
//        education.setText(null);
//        finalMoment.setText(null);
//        authorName.setText(null);
//        authorPhone.setText(null);
//    }
//
//    private void openUserProfile() {
//
//            Intent intent = new Intent(this, UserPageActivity.class);
//            startActivity(intent);
//    }
//
//    private void chooseImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//
//        startActivityForResult(Intent.createChooser(intent, "Select picture"), SELECT_PICTURE);
//    }
//    public void onActivityResult ( int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
//
//            ImageUri = data.getData();
//
//            // update the preview image in the layout
//            imageView.setImageURI(ImageUri);
//        }
//    }
//}
package com.blackgoose.fare_the_well;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.blackgoose.fare_the_well.Adapters.ViewPagerAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

public class EulogyInputActivity extends AppCompatActivity {
    EditText dateofbirth_date, passon_date, firstName, secondName, lastName, burialLocation, earlyLife, education, work, family, finalMoment, authorName, authorPhone;
    ImageView imageView;
    int SELECT_PICTURE = 200;
    ProgressDialog progressDialog;
    Uri ImageUri;
    Button upload_button;
    FirebaseDatabase database;
    ArrayList<Uri> uriArrayList;
    FirebaseStorage firebaseStorage;
    boolean isAllFieldsChecked = false;
    String uid;
    private ViewPager2 viewPager2;

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

        uriArrayList = new ArrayList<>();
        imageView = findViewById(R.id.deceased_image);
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
        upload_button = findViewById(R.id.upload_btn);

        viewPager2 = findViewById(R.id.viewpager);
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

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

        imageView.setOnClickListener(new View.OnClickListener() {
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
                    ArrayList<String> uploadedImageUrls = new ArrayList<>();

                    for (Uri imageUri : uriArrayList) {
                        final StorageReference reference = firebaseStorage.getReference()
                                .child("eulogyPictures")
                                .child(System.currentTimeMillis() + "_" + imageUri.getLastPathSegment());

                        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        uploadedImageUrls.add(uri.toString());

                                        // Check if all images are uploaded
                                        if (uploadedImageUrls.size() == uriArrayList.size()) {
                                            saveEulogyData(uploadedImageUrls);
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(EulogyInputActivity.this, "Failed to get image URL", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(EulogyInputActivity.this, "Image upload failed", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });


    }

    private void saveEulogyData(ArrayList<String> uploadedImageUrls) {
        String randomUid = UUID.randomUUID().toString();
        EulogyModel model = new EulogyModel();
        model.setDeceasedPictures(uploadedImageUrls);  // Save the list of image URLs
        model.setDeceasedFname(Objects.requireNonNull(firstName.getText()).toString());
        model.setDeceasedSname(Objects.requireNonNull(secondName.getText()).toString());
        model.setDeceasedLname(Objects.requireNonNull(lastName.getText()).toString());
        model.setBurialLocation(Objects.requireNonNull(burialLocation.getText()).toString());
        model.setDateOfBirth(Objects.requireNonNull(dateofbirth_date.getText()).toString());
        model.setDateOfDeath(Objects.requireNonNull(passon_date.getText()).toString());
        model.setEarlylifeBiography(earlyLife.getText().toString());
        model.setEducationBiography(education.getText().toString());
        model.setWorkBiography(work.getText().toString());
        model.setFamilyBiography(family.getText().toString());
        model.setFinalMoments(finalMoment.getText().toString());
        model.setAuthorContact(authorPhone.getText().toString());
        model.setAuthorName(authorName.getText().toString());
        model.setUserUid(randomUid);

        database.getReference().child("Eulogies").child(randomUid).setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EulogyInputActivity.this, "Eulogy Uploaded Successfully", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        resetInputstoNull();

                        openUserProfile();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(EulogyInputActivity.this, "Upload Failed"+e.getMessage(), Toast.LENGTH_LONG).show();


                        Log.d("mrd" ,e.getMessage());
                    }
                });
    }


    private boolean CheckAllFields() {
        if (uriArrayList.isEmpty()) {
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


        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                // Multiple items selected
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri mediaUri = data.getClipData().getItemAt(i).getUri();

                    uriArrayList.add(mediaUri);

                    setAdapter();
                    // Handle each mediaUri (either image or video)
                }
            } else if (data.getData() != null) {
                // Single item selected
                Uri mediaUri = data.getData();
                // Handle the mediaUri (either image or video)
            }
        }
    }

    private void setAdapter() {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(EulogyInputActivity.this, uriArrayList);

        viewPager2.setAdapter(viewPagerAdapter);

    }


}
