package com.creation.haasith.easycoupon.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.creation.haasith.easycoupon.Models.Store;
import com.creation.haasith.easycoupon.MyCouponsActivity;
import com.creation.haasith.easycoupon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by gangasanka on 9/4/16.
 */
public class ProfileFragment extends Fragment
{

    private TextView storeTitle, storeAddress,storePhone;
    private Button myCoupons;
    private DatabaseReference db;
    private FirebaseUser user;
    private ImageView storeImage;

    private DatabaseReference databaseReference;

    public ProfileFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        storeTitle = (TextView) v.findViewById(R.id.profileStoreTitle);
        storeAddress = (TextView) v.findViewById(R.id.profileStoreAddress);
        storePhone = (TextView) v.findViewById(R.id.profileStorePhoneNumber);
        myCoupons = (Button) v.findViewById(R.id.myCoupons);
        storeImage = (ImageView) v.findViewById(R.id.storeImage);

        user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Stores").child(user.getUid()).child("image");

        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String imageurl = dataSnapshot.getValue(String.class);

                Picasso.with(getActivity()).load(imageurl).into(storeImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });




        setHasOptionsMenu(true);




        db = FirebaseDatabase.getInstance().getReference().child("Stores").child(user.getUid());


        db.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Store currentStore = dataSnapshot.getValue(Store.class);


                storeTitle.setText(currentStore.getName());

                storeAddress.setText(currentStore.getAddress());

                storePhone.setText(currentStore.getPhoneNumber());



            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


        myCoupons.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getActivity(), MyCouponsActivity.class));
            }
        });












        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_shop_home, menu);
        super.onCreateOptionsMenu(menu, inflater);



    }





}
