package com.innprojects.gymapp.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.innprojects.gymapp.AsyncTask.FlagPUT;
import com.innprojects.gymapp.R;
import java.util.Calendar;

public class NameActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    TextView dob, height;
    ImageView profile;
    String key, userID;
    String Gender;
    LinearLayout proceed;
    String heightflag = "false", ageflag = "false";
    SharedPreferences mPreferences;
    String heightvalue, dobvalue, agevalue;

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(NameActivity.this);
        final SharedPreferences.Editor editor = mPreferences.edit();

        if (mPreferences.contains("dob") && mPreferences.contains("height") && mPreferences.contains("age")) {
            Intent i = new Intent();
            i.setClass(NameActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            setContentView(R.layout.activity_name);
            Gender = mPreferences.getString("Gender", "");

            key = mPreferences.getString("key", "");
            userID = mPreferences.getString("userID", "");
            proceed = (LinearLayout) findViewById(R.id.proceedlayout);
            dob = (TextView) findViewById(R.id.age);
            height = (TextView) findViewById(R.id.height);
            profile = (ImageView) findViewById(R.id.profileview);

            proceed.setAlpha(0.3f);

            if (Gender.equals("female"))
                profile.setImageResource(R.drawable.user_female);
            else if (Gender.equals("male"))
                profile.setImageResource(R.drawable.user_male);

            dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    final int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(NameActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    dob.setText(year + "-" + (monthOfYear + 1) + "-" + + dayOfMonth);
                                    dobvalue = year + "-" + (monthOfYear + 1) + "-" + + dayOfMonth;
                                    agevalue = Integer.toString(mYear - year);
                                    ageflag = "true";
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                }
            });

            height.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setClass(NameActivity.this, HeightSelectorActivity.class);
                    startActivityForResult(i, 2);
                }
            });

            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ageflag.equals("true") && heightflag.equals("true")) {
                        editor.putString("dob", dobvalue);
                        editor.putString("age", agevalue);
                        editor.putString("height", heightvalue);
                        editor.apply();
                        if (isConnected(NameActivity.this))
                            new FlagPUT(NameActivity.this).execute("http://gymfitnessfirst.herokuapp.com/api/gym_users/" + userID + "?access_token=" + key);
                        else
                            Toast.makeText(NameActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                heightvalue = data.getStringExtra("heightvalue");
                heightflag = data.getStringExtra("heightflag");
                if (heightflag.equals("true")) {
                    height.setText(heightvalue + " cms");
                }
                if (ageflag.equals("true") && heightflag.equals("true")) {
                    proceed.setAlpha(1f);
                }
            }
        }
    }

    public void processResultsFlagPUT() {
        Intent i = new Intent();
        i.setClass(NameActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}