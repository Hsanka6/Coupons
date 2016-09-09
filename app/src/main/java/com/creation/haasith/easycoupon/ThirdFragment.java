package com.creation.haasith.easycoupon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by gangasanka on 9/4/16.
 */
public class ThirdFragment extends Fragment
{
    public ThirdFragment()
    {
        // Required empty public constructor
    }

    private EditText someET;
    private TextView someTV;
    private DatabaseReference mRef;
    private Button storeCoupon;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_three, container, false);

        someTV = (TextView) v.findViewById(R.id.textView3);
        storeCoupon = (Button) v.findViewById(R.id.sendData);
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");



        mRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Map<String, Object> firebaseMap = (Map<String, Object>) dataSnapshot.getValue();

                String one = (String) firebaseMap.get("one");
                String two = (String) firebaseMap.get("two");

                someTV.setText(one + two + " it works");
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });




        return v;
    }
}
