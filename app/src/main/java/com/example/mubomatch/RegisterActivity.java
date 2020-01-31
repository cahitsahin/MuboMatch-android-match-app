package com.example.mubomatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mubomatch.MainPage.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private Button mRegister;
    private EditText mEmail, mPassword, mName, mBirthDay;
    private TextView mLogin,mPhotoAdd;
    private ImageView mPp;
    private RadioGroup mRadioGroup;
    private Uri resultUri;

    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mPhotoAdd = (TextView) findViewById(R.id.add_photo);
        mPp = (ImageView) findViewById(R.id.ProfilePhoto);
        mRegister = (Button) findViewById(R.id.register_button);
        mEmail = (EditText) findViewById(R.id.user_email);
        mPassword = (EditText) findViewById(R.id.user_password);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioSex);
        mName = (EditText) findViewById(R.id.user_name);
        mLogin = (TextView) findViewById(R.id.loginnn);
        mBirthDay = (EditText) findViewById(R.id.user_birthday);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Calendar userAge = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                Calendar minAdultAge = new GregorianCalendar();
                minAdultAge.add(Calendar.YEAR, -18);

                if (!minAdultAge.before(userAge)) {
                    updateLabel();
                } else
                    Toast.makeText(RegisterActivity.this, "Your age must be at least 18!!", Toast.LENGTH_SHORT).show();
            }

        };


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToLogin();
            }
        });
        mPhotoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });


        mBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String name = mName.getText().toString();
                final String date = mBirthDay.getText().toString();
                Boolean checkSex = false;

                int selectID = mRadioGroup.getCheckedRadioButtonId();
                if (mRadioGroup.getCheckedRadioButtonId() == -1) {
                    checkSex = true;
                }
                final RadioButton radioButton = (RadioButton) findViewById(selectID);

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(RegisterActivity.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmailId(email)) {
                    Toast.makeText(RegisterActivity.this, "Please enter valid Email!!", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password)) {
                    Toast.makeText(RegisterActivity.this, "Your Password must be between 6-24 character!  ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(date)) {
                    Toast.makeText(RegisterActivity.this, "Please enter BirthDate", Toast.LENGTH_SHORT).show();
                } else if (resultUri == null) {
                    Toast.makeText(RegisterActivity.this, "Please add Profile Photo", Toast.LENGTH_SHORT).show();
                } else if (checkSex) {
                    Toast.makeText(RegisterActivity.this, "Please enter Gender", Toast.LENGTH_SHORT).show();
                } else
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Sign Up Error", Toast.LENGTH_SHORT).show();
                            } else {

                                String userID = mAuth.getCurrentUser().getUid();
                                final DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                                final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("ProfileImages").child(userID);
                                Bitmap bitmap = null;

                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                                byte[] data = baos.toByteArray();
                                UploadTask uploadTask = filepath.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        return;
                                    }
                                });
                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                Map userInfo = new HashMap<>();
                                                userInfo.put("Name", name);
                                                userInfo.put("Sex", radioButton.getText().toString());
                                                userInfo.put("ProfileImageUrl", uri.toString());
                                                userInfo.put("Preference", "mf");
                                                userInfo.put("BirthDate", date);
                                                currentUserDb.updateChildren(userInfo);
                                                Map userMin = new HashMap<>();
                                                userMin.put("MinMovie", "0");
                                                userMin.put("MinMusic", "0");
                                                userMin.put("MinBook", "0");
                                                currentUserDb.child("MinMatchValues").updateChildren(userMin);

                                                return;
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                return;
                                            }
                                        });

                                    }
                                });


                            }
                        }
                    });
            }
        });
    }

    private void GoToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mBirthDay.setText(sdf.format(myCalendar.getTime()));
    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{6,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mPp.setImageURI(resultUri);
        }
    }


}

