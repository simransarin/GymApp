package com.innprojects.gymapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innprojects.gymapp.AsyncTask.FlagPUTreset;
import com.innprojects.gymapp.AsyncTask.LogoutPOST;
import com.innprojects.gymapp.AsyncTask.ResetDELETE;
import com.innprojects.gymapp.R;

public class SettingsActivity extends AppCompatActivity {

    LinearLayout profile, reset, goal;
    TextView signOut;
    TextView name;
    SharedPreferences mPreferences;
    String access_token, userID;
    ImageView profile_photo;

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        access_token = mPreferences.getString("key", null);
        userID = mPreferences.getString("userID", null);

        setTitle("Me");

        profile_photo = (ImageView) findViewById(R.id.profile);
        profile = (LinearLayout) findViewById(R.id.profilelayout);
        goal = (LinearLayout) findViewById(R.id.Goallayout);
        reset = (LinearLayout) findViewById(R.id.resetlayout);
        signOut = (TextView) findViewById(R.id.signout);
        name = (TextView) findViewById(R.id.dummy_button);
        name.setText(mPreferences.getString("name", ""));

        if (mPreferences.getString("Gender", "").equals("female"))
            profile_photo.setImageResource(R.drawable.user_female);
        else if (mPreferences.getString("Gender", "").equals("male"))
            profile_photo.setImageResource(R.drawable.user_male);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(SettingsActivity.this, DetailsActivity.class);
                startActivity(i);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected(SettingsActivity.this))
                    openDialog();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected(SettingsActivity.this))
                    openDialogReset();
            }
        });

        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogGoal();
            }
        });
    }

    public void openDialogGoal() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_layout_goal, null);
        TextView goal = (TextView) promptView.findViewById(R.id.goal);
        TextView remaining = (TextView) promptView.findViewById(R.id.remaining);

        if (mPreferences.contains("target_weight") && mPreferences.contains("last_weight")) {
        goal.setText(mPreferences.getString("target_weight", null));
            double lastW = Double.parseDouble(mPreferences.getString("last_weight", null));
            double targetW = Double.parseDouble(mPreferences.getString("target_weight", null));
            if (lastW > targetW)
                remaining.setText(Double.toString(lastW - targetW));
            else if (lastW < targetW)
                remaining.setText(Double.toString(targetW - lastW));


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getButton(alertD.BUTTON_NEGATIVE).setTextColor(Color.RED);
        } else
        {
            Toast.makeText(SettingsActivity.this, "Set your goal first", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDialogReset() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_layout_reset, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new ResetDELETE(SettingsActivity.this).execute("http://gymfitnessfirst.herokuapp.com/api/gym_users/" + userID + "/healthAnalytics?access_token=" + access_token);
                    }
                })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getButton(alertD.BUTTON_NEGATIVE).setTextColor(Color.GREEN);
        alertD.getButton(alertD.BUTTON_POSITIVE).setTextColor(Color.RED);

    }

    public void openDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialog_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String user = "";
                        String pass = "";
                        if (mPreferences.contains("usernamerem") && mPreferences.contains("passwordrem")) {
                            user = mPreferences.getString("usernamerem", "");
                            pass = mPreferences.getString("passwordrem", "");
                        }
                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.clear();   // This will delete all your preferences, check how to delete just one
                        editor.commit();
                        editor.apply();
                        if (!user.equals("") && !pass.equals("")) {
                            editor.putString("usernamerem", user);
                            editor.putString("passwordrem", pass);
                            editor.apply();
                        }
                        new LogoutPOST(SettingsActivity.this).execute("http://gymfitnessfirst.herokuapp.com/api/gym_users/logout?access_token=" + access_token);
                        Intent i = new Intent();
                        i.setClass(SettingsActivity.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertD = alertDialogBuilder.create();
        alertD.show();
        alertD.getButton(alertD.BUTTON_NEGATIVE).setTextColor(Color.RED);
        alertD.getButton(alertD.BUTTON_POSITIVE).setTextColor(Color.GREEN);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void deleteRESET() {
        if (isConnected(SettingsActivity.this))
            new FlagPUTreset(SettingsActivity.this).execute("http://gymfitnessfirst.herokuapp.com/api/gym_users/" + userID + "?access_token=" + access_token);
        else
            Toast.makeText(SettingsActivity.this, "No internet", Toast.LENGTH_SHORT).show();

    }

    public void processResultsFlagPUTreset() {
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();   // This will delete all your preferences, check how to delete just one
        editor.commit();
        new LogoutPOST(SettingsActivity.this).execute("http://gymfitnessfirst.herokuapp.com/api/gym_users/logout?access_token=" + access_token);
        Intent i = new Intent();
        i.setClass(SettingsActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}

