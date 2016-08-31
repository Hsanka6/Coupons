package com.creation.haasith.easycoupon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity
{
    FirebaseAuth auth;
    Button logout;
    private static final int SIGN_IN = 1;
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logout = (Button) findViewById(R.id.logout);

        c = getApplicationContext();

         auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null)
        {
            Log.d("Auth", auth.getCurrentUser().getEmail() );

        }
        else
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setProviders(AuthUI.FACEBOOK_PROVIDER).build(), SIGN_IN);


        }




        logout.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                Context c = getApplicationContext();
                AuthUI.getInstance().signOut((Activity) c).addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        Log.d("auth", "logged out");
                        finish();

                    }
                });
            }

        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN && resultCode == RESULT_OK)
        {
            Log.d("Auth", auth.getCurrentUser().getEmail() );

        }
    }

}
