package com.creation.haasith.easycoupon;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity
{

    private static final String TAG = "My appppp";
    private EditText emailET, passwordET, phoneNumberET, addressET, nameET;
    private Button signUpButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;




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

        signUpButton = (Button) findViewById(R.id.signUpButton);


        //getSupportActionBar().setTitle("Sign Up");


        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    // User is signed in
                    Log.d(TAG, "Signed IN:" + user.getUid());
                    //writeNewStore(user.getUid(),nameET.getText().toString(),emailET.getText().toString(),addressET.getText().toString(),phoneNumberET.getText().toString());
                    writeNewStore(user.getUid(),nameET.getText().toString(),emailET.getText().toString(),addressET.getText().toString(),phoneNumberET.getText().toString());




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


    private void writeNewStore(String userId, String name, String email, String address, String phoneNumber) {
        Store store = new Store(name, email, address,phoneNumber);

        mDatabase.child("Stores").child(userId).setValue(store);
    }

    public void signUpButton(View view)
    {
        Log.e("lenght", String.valueOf(emailET.getText().toString().length()));
        Log.e("lenght", String.valueOf(passwordET.getText().toString().length()));
        Log.e("lenght", String.valueOf(phoneNumberET.getText().toString().length()));
        Log.e("lenght", String.valueOf(addressET.getText().toString().length()));


        Log.e("lenght", String.valueOf(nameET.getText().toString().length()));



        if(emailET.getText().toString().length() == 0 || passwordET.getText().toString().length() == 0 || phoneNumberET.getText().toString().length() == 0 || addressET.getText().toString().length() == 0 || nameET.getText().toString().length() == 0)
        {

            AlertDialog.Builder emptyFieldDialog = new AlertDialog.Builder(this);
            emptyFieldDialog.setTitle("Sign Up Error");
            emptyFieldDialog.setMessage("One or more of the fields are empty.");
            emptyFieldDialog.setCancelable(true);
            emptyFieldDialog.setIcon(android.R.drawable.ic_dialog_alert);

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

            mAuth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

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
                        Toast.makeText(getApplicationContext(),"Password needs to be greater than 6 letters",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"success", Toast.LENGTH_SHORT).show();

                    }

                    // ...
                }

            });
        }


    }
}
