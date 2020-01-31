package com.example.mubomatch.UserProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mubomatch.R;
import com.example.mubomatch.RegisterActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.xw.repo.BubbleSeekBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase,mMovieDatabase;
    private Button mBack, mConfirm, mAddMovie;
    private FirebaseAuth mAuth;
    private String userId, name, phone, profileImageUrl, userSex, posterUrl,pre;
    private EditText mNameField, mPhoneField;
    private Uri resultUri;
    private String sex;
    private TextView changePhoto;
    private RadioGroup mSexRadio;
    private CheckBox preMale,preFemale;
    private RadioButton mMaleRadio,mFemaleRadio;
    private ImageView mBackButton,mPP;
    private BubbleSeekBar bbMovie,bbBook,bbMusic;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        mBackButton = (ImageView) findViewById(R.id.back_button);

        mPP = (ImageView) findViewById(R.id.settingProfilePhoto);

        changePhoto =(TextView) findViewById(R.id.settingChangePhoto);


        mConfirm = (Button) findViewById(R.id.confirm);
        mSexRadio = (RadioGroup) findViewById(R.id.sex);
        mMaleRadio = (RadioButton) findViewById(R.id.sexMale);
        mFemaleRadio = (RadioButton) findViewById(R.id.sexFemale);
        preMale = (CheckBox) findViewById(R.id.preMale);
        preFemale=(CheckBox) findViewById(R.id.preFemale);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        mNameField = (EditText) findViewById(R.id.name);
        bbMovie = (BubbleSeekBar) findViewById(R.id.bubbleMovie);
        bbMusic = (BubbleSeekBar) findViewById(R.id.bubbleMusic);
        bbBook = (BubbleSeekBar) findViewById(R.id.bubbleBook);


        getUserInfo();





        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
            }
        });


        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                return;
            }
        });

        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });



    }

    private void getUserInfo() {
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("Name")!=null){
                        name = map.get("Name").toString();
                        mNameField.setText(name);
                    }

                    if(map.get("Sex")!=null){
                        userSex = map.get("Sex").toString();
                        if (userSex.equals("Male")){
                            mMaleRadio.setChecked(true);
                        }
                        else {
                            mFemaleRadio.setChecked(true);
                        }
                    }


                    if(map.get("Preference")!=null){
                        pre = map.get("Preference").toString();
                        if (pre.equals("mf")){
                            preMale.setChecked(true);
                            preFemale.setChecked(true);
                        }
                        else if(pre.equals("m")) {
                            preMale.setChecked(true);
                        }
                        else
                            preFemale.setChecked(true);
                    }

                    if(map.get("ProfileImageUrl")!=null){
                        profileImageUrl = map.get("ProfileImageUrl").toString();
                        switch(profileImageUrl){
                            case "Default":
                                Glide.with(getApplication()).load(R.mipmap.ic_launcher).into(mPP);
                                break;
                            default:
                                Glide.with(getApplication()).load(profileImageUrl).into(mPP);
                                break;
                        }
                    }




                }
                if(dataSnapshot.child("MinMatchValues").exists()){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.child("MinMatchValues").getValue();

                    if(map.get("MinMovie")!=null){
                        String minMovie = map.get("MinMovie").toString();
                        int m = Integer.parseInt(minMovie);
                        bbMovie.setProgress(m);
                    }

                    if(map.get("MinMusic")!=null){
                        String minMusic = map.get("MinMusic").toString();
                        int n = Integer.parseInt(minMusic);
                        bbMusic.setProgress(n);
                    }

                    if(map.get("MinBook")!=null){
                        String minMusic = map.get("MinBook").toString();
                        int l = Integer.parseInt(minMusic);
                        bbBook.setProgress(l);
                    }

                    getMinBook();
                    getMinMovie();
                    getMinBook();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void saveUserInformation() {

        int selectedRadioButtonID = mSexRadio.getCheckedRadioButtonId();


         final RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);

        sex = selectedRadioButton.getText().toString();
        name = mNameField.getText().toString();

        String pre="mf";

        if (preMale.isChecked() && preFemale.isChecked()){
            pre = "mf";
        }
        else if (preMale.isChecked()){
            pre= "m";
        }
        else if (preFemale.isChecked()){
            pre = "f";
        }

        else if (!preMale.isChecked()&& !preFemale.isChecked()){
            Toast.makeText(SettingActivity.this,"You must select at least one gender preference!!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(name)){
            Toast.makeText(SettingActivity.this,"Enter Name!!",Toast.LENGTH_SHORT).show();
            return;
        }


        Map userInfo = new HashMap();
        userInfo.put("Name", name);
        userInfo.put("Sex",sex);
        userInfo.put("Preference",pre);
        mUserDatabase.updateChildren(userInfo);

        Map userMin = new HashMap<>();
        userMin.put("MinMovie",String.valueOf(bbMovie.getProgress()));
        userMin.put("MinMusic",String.valueOf(bbMusic.getProgress()));
        userMin.put("MinBook",String.valueOf(bbBook.getProgress()));

        getMinBook();
        getMinMovie();
        getMinBook();

        mUserDatabase.child("MinMatchValues").updateChildren(userMin);
        Toast.makeText(SettingActivity.this,"Successfully",Toast.LENGTH_SHORT).show();




        if(resultUri != null){
            final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("ProfileImages").child(userId);
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
                            Map userInfo = new HashMap();
                            userInfo.put("ProfileImageUrl", uri.toString());
                            mUserDatabase.updateChildren(userInfo);



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
        }else{
            return;
        }





    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mPP.setImageURI(resultUri);
        }
    }

    public String getMinMovie(){
        String minMovie = String.valueOf(bbMovie.getProgress());

        return minMovie;
    }

    public String getMinMusic(){
        String minMusic = String.valueOf(bbMusic.getProgress());

        return minMusic;
    }

    public String getMinBook(){
        String minBook = String.valueOf(bbMovie.getProgress());

        return minBook;
    }
}
