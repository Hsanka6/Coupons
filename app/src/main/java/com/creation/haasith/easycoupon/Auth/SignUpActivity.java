package com.creation.haasith.easycoupon.Auth;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.creation.haasith.easycoupon.HomeActivity;
import com.creation.haasith.easycoupon.Models.Store;
import com.creation.haasith.easycoupon.R;
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

public class SignUpActivity extends AppCompatActivity
{

    private static final String TAG = "My appppp";
    private EditText emailET, passwordET, phoneNumberET, addressET, nameET;
    private Button signUpButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;
    private ImageButton storeImage;
    private Uri imageUri;

    private Uri downloadUri;

    private StorageReference storage;
    private static final int GET_STORE_IMAGE = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailET = (EditText) findViewById(R.id.signUpEmailET);
        passwordET = (EditText) findViewById(R.id.signUpPasswordET);
        phoneNumberET = (EditText) findViewById(R.id.storePhoneNumberET);
        addressET = (EditText) findViewById(R.id.addressET);
        nameET = (EditText) findViewById(R.id.storeNameET);
        storeImage = (ImageButton) findViewById(R.id.storeImage);

        signUpButton = (Button) findViewById(R.id.signUpButton);


        progressDialog = new ProgressDialog(this);

        //getSupportActionBar().setTitle("Sign Up");


        mAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance().getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    // User is signed in
                    Log.d(TAG, "Signed IN:" + user.getUid());


                    StorageReference filePath = storage.child("Coupons").child(user.getUid()).child(imageUri.getLastPathSegment());

                    filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            downloadUri = taskSnapshot.getDownloadUrl();

                            writeNewStore(user.getUid(),nameET.getText().toString(),emailET.getText().toString(),addressET.getText().toString(),phoneNumberET.getText().toString(), downloadUri.toString());

                        }
                    });





                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
                else
                {
                    // User is signed out
                    Log.d(TAG, "signed Out");
                }
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference();


        storeImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                getImage.setType("image/*");
                startActivityForResult(getImage, GET_STORE_IMAGE);

            }
        });





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_STORE_IMAGE && resultCode == RESULT_OK)
        {
            imageUri = data.getData();

            storeImage.setImageURI(imageUri);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void writeNewStore(String userId, String name, String email, String address, String phoneNumber, String image) {
        Store store = new Store(name, address, phoneNumber,email,image);

        mDatabase.child("Stores").child(userId).setValue(store);
    }

    public void signUpButton(View view)
    {



        if(emailET.getText().toString().length() == 0 || passwordET.getText().toString().length() == 0 || phoneNumberET.getText().toString().length() == 0 || addressET.getText().toString().length() == 0 || nameET.getText().toString().length() == 0)
        {

            String alertMessage = "One or more of the fields are empty.";
            AlertDialog.Builder emptyFieldDialog = new AlertDialog.Builder(this);
            emptyFieldDialog.setTitle("Sign Up Error");
            emptyFieldDialog.setCancelable(true);
            emptyFieldDialog.setIcon(R.mipmap.ic_warning_black_24dp);


            if(passwordET.getText().length() < 6)
            {
                alertMessage += "Password is not 6 characters long";
            }

            emptyFieldDialog.setMessage(alertMessage);
            emptyFieldDialog.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            emptyFieldDialog.show();


        }
        else
        {
            progressDialog.setMessage("Signing Up");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(emailET.getText().toString().trim(), passwordET.getText().toString().trim()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                    task.addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            e.printStackTrace();

                        }
                    });

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),"Sign Up failed",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                    }


                    // ...
                }

            });
        }


    }
}
