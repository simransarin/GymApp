package com.innprojects.gymapp.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.innprojects.gymapp.R;

public class DetailsActivity extends AppCompatActivity {

    TextView age, height, name;
    LinearLayout heightlayout;
    LinearLayout agelayout;
    String ageValue, heightValue;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setTitle("User details");

        mPreferences = PreferenceManager.getDefaultSharedPreferences(DetailsActivity.this);
        ageValue = mPreferences.getString("age", "");
        heightValue = mPreferences.getString("height","");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        heightlayout = (LinearLayout) findViewById(R.id.heightlayout);
        agelayout = (LinearLayout) findViewById(R.id.agelayout);
        age = (TextView) findViewById(R.id.age);
        height = (TextView) findViewById(R.id.height);
        name = (TextView) findViewById(R.id.name);

        age.setText(ageValue + " years");
        height.setText(heightValue + " cms");
        name.setText(mPreferences.getString("name",null));
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
