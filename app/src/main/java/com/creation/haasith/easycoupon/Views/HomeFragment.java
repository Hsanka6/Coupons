package com.creation.haasith.easycoupon.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.creation.haasith.easycoupon.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;


public class HomeFragment extends Fragment
{

    public HomeFragment()
    {
        // Required empty public constructor
    }

    private Firebase mRef;
    private ListView lv;
    private ArrayList<String> usernames = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        lv = (ListView) v.findViewById(R.id.listView);



        mRef = new Firebase("https://coupons-app-5fd14.firebaseio.com/Users");


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,usernames);

        lv.setAdapter(adapter);

        mRef.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                String value = dataSnapshot.getValue(String.class);

                usernames.add(value);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {

            }
        });



        //put data in
//        send.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Firebase mRefChild = mRef.child("stuff");
//
//                mRefChild.setValue("shit");
//
//                //mRefChild.setValue("haas stuff");
//            }
//        });




        return v;
    }



}
