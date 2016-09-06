package com.creation.haasith.easycoupon;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by gangasanka on 9/5/16.
 */
public class Global extends Application
{
    private Firebase mRef;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(getApplicationContext());
        mRef = new Firebase("https://coupons-app-5fd14.firebaseio.com/");


    }
}
