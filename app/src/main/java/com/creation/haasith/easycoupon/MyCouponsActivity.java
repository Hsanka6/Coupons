package com.creation.haasith.easycoupon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.creation.haasith.easycoupon.Models.Coupon;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MyCouponsActivity extends AppCompatActivity
{

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupons);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Stores").child(user.getUid()).child("coupons");

        recyclerView = (RecyclerView) findViewById(R.id.myCouponsList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setTitle("My Coupons");

    }


    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<Coupon, CouponViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Coupon, CouponViewHolder>(
                Coupon.class,
                R.layout.coupon_card,
                CouponViewHolder.class,
                databaseReference
        )
        {
            @Override
            protected void populateViewHolder(CouponViewHolder viewHolder, Coupon model, int position)
            {
                viewHolder.setTitle(model.getName());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setDuration(model.getStart(),model.getEnd());
                viewHolder.setImage(getApplicationContext(),model.getImage());

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }




    public static class CouponViewHolder extends RecyclerView.ViewHolder
    {

        View v;

        public CouponViewHolder(View itemView)
        {
            super(itemView);
            v = itemView;
        }


        public void setTitle(String title)
        {
            TextView couponTitle = (TextView) v.findViewById(R.id.couponTitle);
            couponTitle.setText(title);

        }



        public void setDescription(String description)
        {
            TextView couponDescription = (TextView) v.findViewById(R.id.couponDescription);
            couponDescription.setText(description);

        }

        public void setImage(Context context, String imageUrl)
        {
            ImageView couponImage = (ImageView) v.findViewById(R.id.couponImage);
            Picasso.with(context).load(imageUrl).into(couponImage);

        }

        public void setDuration(String start, String end)
        {
            TextView couponDuration = (TextView) v.findViewById(R.id.couponDuration);
            couponDuration.setText(start + " - " + end);
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_shop_home,menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.addCoupon:

                startActivity(new Intent(getApplicationContext(),AddCoupon.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
