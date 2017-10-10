package com.example.terence.internsmartassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

/**
 * Created by Terence on 10/9/2017.
 */

public class EditProfile extends AppCompatActivity {

    private ImageButton imBut;
    private EditText univer;
    private EditText prog;
    private Button submit;
    private Uri imageUri = null;
    private DatabaseReference reference;
    private FirebaseAuth fAuth;
    private ProgressDialog mPd;

    private StorageReference storage;

    private static final int GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);

        imBut = (ImageButton) findViewById(R.id.profileImage);
        univer = (EditText) findViewById(R.id.univer);
        prog = (EditText) findViewById(R.id.prog);
        submit = (Button) findViewById(R.id.submit);

        mPd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference().child("Profile_images");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSetup();
            }
        });

        imBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT).setType("image/*");
                startActivityForResult(gallery, GALLERY);


            }
        });


    }

    private void startSetup() {
       final String university = univer.getText().toString().trim();
       final String program = prog.getText().toString().trim();
        final String user_id = fAuth.getCurrentUser().getUid();

        if(!TextUtils.isEmpty(university) && !TextUtils.isEmpty(program)){

            mPd.setMessage("Finishing...");
            mPd.show();
            StorageReference path = storage.child(imageUri.getLastPathSegment());

            path.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String download = taskSnapshot.getDownloadUrl().toString();

                    reference.child(user_id).child("program").setValue(program);
                    reference.child(user_id).child("university").setValue(university);
                    reference.child(user_id).child("image").setValue(download);

                    Intent toLogin = new Intent(EditProfile.this,MainActivity.class);
                    toLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toLogin);
                }
            });


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(requestCode == RESULT_OK){
                imageUri = result.getUri();

                imBut.setImageURI(imageUri);
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e = result.getError();
            }
        }
    }
}
