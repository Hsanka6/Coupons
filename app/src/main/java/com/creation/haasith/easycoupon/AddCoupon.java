package com.creation.haasith.easycoupon;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class AddCoupon extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{

    private static final String TAG = "coupon";
    private Spinner couponTypeSpinner;
    private EditText couponNameET, couponDescriptonET;
    private Button startTimeButton, endTimeButton, startDayButton, endDayButton;
    private TextView startTimeTV, endTimeTV, startDayTV, endDayTV;

    boolean switchDateDialog;

    FirebaseDatabase database;

    boolean switchTimeDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);
        couponTypeSpinner = (Spinner) findViewById(R.id.couponTypeSpinner);

        couponNameET = (EditText) findViewById(R.id.couponNameET);
        couponDescriptonET = (EditText) findViewById(R.id.couponDescriptionET);

        startTimeButton = (Button) findViewById(R.id.startTimeButton);
        endTimeButton = (Button) findViewById(R.id.endTimeButton);
        startDayButton = (Button) findViewById(R.id.startDateButton);
        endDayButton = (Button) findViewById(R.id.endDateButton);
        startTimeTV = (TextView) findViewById(R.id.startTimeTV);
        endTimeTV = (TextView) findViewById(R.id.endTimeTV);
        startDayTV = (TextView) findViewById(R.id.startDateTV);
        endDayTV = (TextView) findViewById(R.id.endDateTV);




        startTimeButton.setVisibility(View.GONE);
        endTimeButton.setVisibility(View.GONE);
        startDayButton.setVisibility(View.GONE);
        endDayButton.setVisibility(View.GONE);
        startTimeTV.setVisibility(View.GONE);
        endTimeTV.setVisibility(View.GONE);
        startDayTV.setVisibility(View.GONE);
        endDayTV.setVisibility(View.GONE);



        setUpSpinner();

        //set up action bar
        getSupportActionBar().setTitle("Add coupon");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //Add calender to select startDate and endDate
        startDayButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switchDateDialog = false;
                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        AddCoupon.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");


            }
        });

        endDayButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switchDateDialog = true;
                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        AddCoupon.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");


            }
        });

        startTimeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switchTimeDialog = false;
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                  AddCoupon.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");


            }
        });

        endTimeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switchTimeDialog = true;
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddCoupon.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");


            }
        });


        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("mes");


        myRef.setValue("Hello, World!");


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });







    }

    private void setUpSpinner()
    {

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.coupon_type_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        couponTypeSpinner.setAdapter(staticAdapter);


        couponTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                String ab = (String) parent.getItemAtPosition(position);
                if(ab.equals("One Day"))
                {
                    Toast.makeText(getApplicationContext(), "one day selected", Toast.LENGTH_SHORT).show();

                    startTimeButton.setVisibility(View.VISIBLE);
                    endTimeButton.setVisibility(View.VISIBLE);
                    startTimeTV.setVisibility(View.VISIBLE);
                    endTimeTV.setVisibility(View.VISIBLE);
                    startDayButton.setVisibility(View.GONE);
                    endDayButton.setVisibility(View.GONE);
                    startDayTV.setVisibility(View.GONE);
                    endDayTV.setVisibility(View.GONE);




                }
                else if (ab.equals("Multiple Day"))
                {
                    Log.e("item", ab);
                    startDayButton.setVisibility(View.VISIBLE);
                    endDayButton.setVisibility(View.VISIBLE);
                    startDayTV.setVisibility(View.VISIBLE);
                    endDayTV.setVisibility(View.VISIBLE);
                    startTimeButton.setVisibility(View.GONE);
                    endTimeButton.setVisibility(View.GONE);
                    startTimeTV.setVisibility(View.GONE);
                    endTimeTV.setVisibility(View.GONE);



                }
                else if(ab.equals("Pick Type of Coupon"))
                {
                    startTimeButton.setVisibility(View.GONE);
                    endTimeButton.setVisibility(View.GONE);
                    startDayButton.setVisibility(View.GONE);
                    endDayButton.setVisibility(View.GONE);
                    startTimeTV.setVisibility(View.GONE);
                    endTimeTV.setVisibility(View.GONE);
                    startDayTV.setVisibility(View.GONE);
                    endDayTV.setVisibility(View.GONE);


                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }

    //Set up menu resource file
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_coupon, menu);
        return true;
    }


    //Set up action bar button clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle presses on the action bar items
        switch (item.getItemId())
        {
            case R.id.saveCoupon:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));


                return true;
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
    {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Log.e("date", date);
        if(!switchDateDialog)
        {
            //gets startdate
            startDayTV.setText((monthOfYear + 1) + "/" + dayOfMonth  + "/" + year);

        }
        else
        {
            //gets endDate
            endDayTV.setText((monthOfYear + 1) + "/" + dayOfMonth  + "/" + year);

        }

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second)
    {
        String hourString;
        String am_pm;
        if(hourOfDay < 12)
        {
            hourOfDay = hourOfDay;
            hourString = String.valueOf(hourOfDay);
            am_pm = "AM";

        }
        else
        {
            am_pm = "PM";
            hourOfDay = hourOfDay - 12;
            hourString = String.valueOf(hourOfDay);

        }


        String minuteString = minute < 10 ? "0"+minute : ""+minute;


        String time = hourString+":"+minuteString + " " + am_pm;

        Log.e("Hour", String.valueOf(hourOfDay));

        if(!switchTimeDialog)
        {
            //gets startdate



            startTimeTV.setText(time);

        }
        else
        {
            //gets endDate
            endTimeTV.setText(time);

        }







    }
}

