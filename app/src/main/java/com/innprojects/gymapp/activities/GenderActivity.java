package com.innprojects.gymapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.innprojects.gymapp.R;

public class GenderActivity extends AppCompatActivity {

    ImageView male, female;
    LinearLayout next;
    Boolean gmale = false, gfemale = false;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(GenderActivity.this);
        final SharedPreferences.Editor editor = mPreferences.edit();

        if (mPreferences.contains("Gender")) {
            Intent i = new Intent();
            i.setClass(GenderActivity.this, NameActivity.class);
//            if(mPreferences.getString("Gender", "").equals("male"))
//            {
//                i.putExtra("Gender", "male");
//            } else if (mPreferences.getString("Gender", "").equals("female"))
//            {
//                i.putExtra("Gender", "female");
//            }
            startActivity(i);
            finish();
        } else {
            setContentView(R.layout.activity_gender);
            male = (ImageView) findViewById(R.id.maleview);
            female = (ImageView) findViewById(R.id.femaleview);
            next = (LinearLayout) findViewById(R.id.nextlayout);

            male.setAlpha(0.3f);
            female.setAlpha(0.3f);
            next.setAlpha(0.3f);

            male.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    male.setAlpha(1f);
                    female.setAlpha(0.3f);
                    next.setAlpha(1f);
                    next.isClickable();
                    gmale = true;
                    gfemale = false;
                }
            });

            female.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    female.setAlpha(1f);
                    male.setAlpha(0.3f);
                    next.setAlpha(1f);
                    next.isClickable();
                    gmale = false;
                    gfemale = true;
                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gmale || gfemale) {
                        Intent i = new Intent();
                        i.setClass(GenderActivity.this, NameActivity.class);
                        if (gmale) {
//                        i.putExtra("Gender", "male");
                            editor.putString("Gender", "male");
                            editor.apply();
                        } else if (gfemale) {
//                        i.putExtra("Gender", "female");
                            editor.putString("Gender", "female");
                            editor.apply();
                        }
                        startActivity(i);
                        finish();
                    }
                }
            });
        }
    }
}
