package com.creation.haasith.easycoupon.Views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.creation.haasith.easycoupon.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by gangasanka on 9/4/16.
 */
public class ProfileFragment extends Fragment
{
    private Button getImage;
    private StorageReference mStorage;

    private ProgressDialog progressDialog;

    private static final int UG = 1;
    public ProfileFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v =  inflater.inflate(R.layout.fragment_two, container, false);
        // Inflate the layout for this fragment

        mStorage = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(getActivity());

        getImage = (Button) v.findViewById(R.id.getImage);


        getImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Intent.ACTION_PICK);

                i.setType("image/*");

                startActivityForResult(i,UG);
            }
        });








        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == UG && resultCode == Activity.RESULT_OK)
        {
            Uri uri = data.getData();

            progressDialog.setMessage("Uploading");
            progressDialog.show();

            StorageReference filePath = mStorage.child("photos").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });



        }
    }



}
