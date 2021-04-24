package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MainActivity3 extends AppCompatActivity {

    EditText nameInput;
    EditText passwordInput;
    EditText passwordRepeatInput;
    EditText heightInput;
    EditText weightInput;
    TextView dateOfBirthInput;

    private Spinner spinnerGender;
    private Spinner spinnerSmoking;
    private Spinner spinnerDiet;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    Button continueButton;
    Button changePictureButton;

    ImageView userPicture;

    String name;
    String password;
    String passwordRepeat;
    String dateOfBirth;
    float height=0;
    float weight=0;
    String dietaryInfo;
    String smokingInfo;
    String gender;
    Bitmap profilePicture;

    private int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //region WidgetInit
        nameInput = findViewById(R.id.editTextPreferredName);

        passwordInput = findViewById(R.id.editTextTextPasswordRegister);
        passwordRepeatInput = findViewById(R.id.editTextTextPasswordRegisterRepeat);
        dateOfBirthInput = findViewById(R.id.textViewDOB);
        heightInput = findViewById(R.id.editTextHeight);
        weightInput = findViewById(R.id.editTextWeight);

        continueButton = findViewById(R.id.buttonContinue);
        changePictureButton = findViewById(R.id.buttonChangePicture);

        userPicture = findViewById(R.id.userPicture);

        spinnerGender = findViewById(R.id.spinner);
        spinnerSmoking = findViewById(R.id.spinner2);
        spinnerDiet = findViewById(R.id.spinner3);
        //endregion
        String[] genders = new String[]{"-Please choose gender-","Female", "Male", "Other", "Prefer not to say"};
        String[] smoking = new String[]{"-Select nicotine consumption-","I don't use nicotine", "I occasionally use nicotine", "I use nicotine almost daily", "I use nicotine daily"};
        String[] diet = new String[]{"-Please choose your diet-","Vegan","Vegetarian","Mostly plant based diet","Regular diet"};

        ArrayAdapter<String> adapterGender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genders);
        ArrayAdapter<String> adapterSmoking = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, smoking);
        ArrayAdapter<String> adapterDiet = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, diet);

        spinnerGender.setAdapter(adapterGender);
        spinnerSmoking.setAdapter(adapterSmoking);
        spinnerDiet.setAdapter(adapterDiet);

        dateOfBirthInput.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity3.this,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    onDateSetListener,
                    year,month,day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

        onDateSetListener = (view, year, month, dayOfMonth) -> {
            month = month + 1;
            String date = dayOfMonth + "." + month + "." + year;
            dateOfBirthInput.setText("Date of birth: " + date);
            dateOfBirth = date;
        };

        changePictureButton.setOnClickListener(v -> {
            final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Change picture");

            builder.setItems(options, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (options[item].equals("Take Photo")) {
                        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);

                    } else if (options[item].equals("Choose from Gallery")) {
                        if (ContextCompat.checkSelfPermission(MainActivity3.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                            requestStoragePermission();
                        }
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 1);

                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        });

        continueButton.setOnClickListener(v -> {

            if(checkFields()){
                name = nameInput.getText().toString();
                password = passwordInput.getText().toString();
                passwordRepeat = passwordRepeatInput.getText().toString();
                height = Integer.parseInt(heightInput.getText().toString());
                weight = Integer.parseInt(weightInput.getText().toString());
                gender = spinnerGender.getSelectedItem().toString();
                dietaryInfo = spinnerDiet.getSelectedItem().toString();
                smokingInfo = spinnerSmoking.getSelectedItem().toString();
                if(!password.equals(passwordRepeat)){
                    showToast("Passwords do not match!");
                }
                else if(password.equals(passwordRepeat)) {
                    User newUser = new User(name, encryptPassword(password));
                    newUser.setDateOfBirth(dateOfBirth);
                    newUser.setHeight(height);
                    newUser.setWeight(weight);
                    newUser.setGender(gender);
                    newUser.setDietaryInfo(dietaryInfo);
                    newUser.setSmokingInfo(smokingInfo);
                    newUser.setProfilePicture(profilePicture);
                    SecondActivity();
                }
            }
        });
    }
    //region PermissionRelated
    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to access the gallery")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity3.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showToast("Permission Granted");
            }else {
                showToast("Permission Denied");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        profilePicture = selectedImage;
                        userPicture.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                profilePicture = BitmapFactory.decodeFile(picturePath);
                                userPicture.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }
    //endregion
    // Checks if the input fields are empty
    private boolean checkFields() {
        if(nameInput.getText().toString().equals("") ||
                passwordInput.getText().toString().equals("") ||
                passwordRepeatInput.getText().toString().equals("") ||
                dateOfBirthInput.getText().toString().equals("")||
                heightInput.getText().toString().equals("") ||
                weightInput.getText().toString().equals("")||
                spinnerDiet.getSelectedItemPosition() == 0 ||
                spinnerSmoking.getSelectedItemPosition() == 0 ||
                spinnerGender.getSelectedItemPosition() == 0){
            showToast("Check that the fields are not empty!");
            return false;
        }

        if(checkIfNameExists()){
            return true;
        }

        if (checkPassword(passwordInput.getText().toString())){
            return true;
        }else return false;
    }

    private boolean checkIfNameExists() {
        //To-Do after saving has been implemented.
        //returns true if name has not been taken, else returns false.
        return true;
    }

    private void SecondActivity(){
        Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
        this.startActivity(intent);
    }

    private void showToast(String text){
        Toast.makeText(MainActivity3.this, text, Toast.LENGTH_SHORT).show();
    }

    // Checks if the given password follows principals of a strong password
    private boolean checkPassword(String text){
        boolean hasUpperCase = false, hasSpecial = false, hasNumber = false;
        Set<Character> set = new HashSet<>(Arrays.asList('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+'));

        char[] arr = text.toCharArray();
        if (arr.length < 12) {
            showToast("Password should be at least 12 characters long!");
            return false;
        }

        for (char c : arr) {
            if (Character.isUpperCase(c)) hasUpperCase = true;
            if (set.contains(c)) hasSpecial = true;
            if (Character.isDigit(c)) hasNumber = true;
        }
        if (hasUpperCase && hasSpecial && hasNumber) return true;
        else {showToast("Password should contain at least one of each: upper case letter, special character and number"); return false;}


    }
    // Encrypts the given password using SHA-512 + Salt.
    private byte[] encryptPassword(String text){
        String salt = "Ö/=&@$¤ä";

        byte[] inputData = (text + salt).getBytes();
        byte[] outputData = new byte[0];

        try {
            outputData = PasswordEncryption.encryptSHA(inputData, "SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return  outputData;
    }
}