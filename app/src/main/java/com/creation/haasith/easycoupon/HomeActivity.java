package com.creation.haasith.easycoupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
{

    Button LogOut;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        LogOut = (Button) findViewById(R.id.logOut);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
            }
        };

        LogOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mAuth.signOut();

            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
