package com.innprojects.gymapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.innprojects.gymapp.AsyncTask.StatsGET;
import com.innprojects.gymapp.R;
import com.innprojects.gymapp.fragments.CalcFragment;
import com.innprojects.gymapp.fragments.HomeFragment;
import com.innprojects.gymapp.fragments.StatsFragment;
import com.innprojects.gymapp.javaModels.Stat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView profile;
    SharedPreferences mPreferences;
    String key, userID;
    public ArrayList<Stat> statsgiven;
    MainActivity mainActivity;
    HomeFragment homeFragment;

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    setTitle("Gym Details");

                    HomeFragment homeFragment = new HomeFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                            .replace(R.id.content,
                                    homeFragment,
                                    homeFragment.getTag())
                            .commit();
                    return true;
                }
                case R.id.navigation_dashboard: {
                    setTitle("Attendant Details");

                    CalcFragment calcFragment = new CalcFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                            .replace(R.id.content,
                                    calcFragment,
                                    calcFragment.getTag())
                            .commit();
                    return true;
                }
                case R.id.navigation_stats: {
                    setTitle("Statistics");

                    StatsFragment statsFragment = new StatsFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction()
                            .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                            .replace(R.id.content,
                                    statsFragment,
                                    statsFragment.getTag())
                            .commit();
                    return true;
                }
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_main);
        setTitle("Gym Details");

        mPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        key = mPreferences.getString("key","");
        userID = mPreferences.getString("userID", "");
        statsgiven = new ArrayList<Stat>();

        profile = (ImageView) findViewById(R.id.imageView);

        if (mPreferences.getString("Gender", "").equals("female"))
            profile.setImageResource(R.drawable.user_female);
        else if (mPreferences.getString("Gender", "").equals("male"))
            profile.setImageResource(R.drawable.user_male);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

        homeFragment = new HomeFragment();

        if (isConnected(MainActivity.this))
            new StatsGET(MainActivity.this).execute("http://gymfitnessfirst.herokuapp.com/api/health_analytics?filter[where][gymUserId]=" + userID + "&access_token=" + key);
        else
            Toast.makeText(MainActivity.this, "No internet", Toast.LENGTH_SHORT).show();
    }

    public void processResults(Stat[] stats){
        for (Stat s : stats) {
            statsgiven.add(s);
        }

        if(statsgiven.isEmpty()) {
            Toast.makeText(MainActivity.this, "No stats yet", Toast.LENGTH_SHORT).show();
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.content,
                        homeFragment,
                        homeFragment.getTag())
                .commit();
    }

}
