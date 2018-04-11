package com.innprojects.gymapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.innprojects.gymapp.AsyncTask.FlagGET;
import com.innprojects.gymapp.AsyncTask.LoginPOST;
import com.innprojects.gymapp.AsyncTask.UserdetailsGET;
import com.innprojects.gymapp.R;
import com.innprojects.gymapp.javaModels.User;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences mPreferences;
    TextView forgotpass;
    EditText userNameEditText;
    EditText passwordEditText;
    String userName;
    String password;
    CheckBox check, rem;
    Boolean flagrem = false;

    String key, userID;

    String m_Text;

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (mPreferences.contains("username")) {
            // start Main activity

            Intent i = new Intent();
            i.setClass(LoginActivity.this, GenderActivity.class);
            startActivity(i);
        } else {
            // ask him to enter his credentials
            setContentView(R.layout.activity_login);
            final Button loginButton = (Button) findViewById(R.id.login_button);
            userNameEditText = (EditText) findViewById(R.id.editText);
            passwordEditText = (EditText) findViewById(R.id.editText2);
//            forgotpass = (TextView) findViewById(R.id.forgotpass);
            check = (CheckBox) findViewById(R.id.checkbox);
            check.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (buttonView.isChecked()) {
                                passwordEditText.setTransformationMethod(null);
                            } else {
                                passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                            }
                        }
                    }
            );
            rem = (CheckBox) findViewById(R.id.checkboxmachine2);
            rem.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (buttonView.isChecked()) {
                                flagrem=true;
                            } else {
                                flagrem=false;
                                SharedPreferences.Editor editor = mPreferences.edit();
                                editor.remove("usernamerem");
                                editor.remove("passwordrem");
                                editor.apply();
                            }
                        }
                    }
            );

            if (mPreferences.contains("usernamerem") && mPreferences.contains("passwordrem"))
            {
                userNameEditText.setText(mPreferences.getString("usernamerem", ""));
                passwordEditText.setText(mPreferences.getString("passwordrem", ""));
                rem.setChecked(true);
            }

//            forgotpass.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.MyAlertDialogStyle);
//                    builder.setTitle("Forgot password");
//
////                  Set up the input
//                    final EditText input = new EditText(LoginActivity.this);
////                  Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//                    input.setInputType(InputType.TYPE_CLASS_TEXT);
//                    input.setHint("Enter username");
//                    builder.setView(input);
//                    builder.setCancelable(false);
//
////                  Set up the buttons
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            m_Text = input.getText().toString();
//                            new ForgotPassGETtask(LoginActivity.this).execute("https://biometric-app.herokuapp.com/reset-pass/" + m_Text);
//                            Toast.makeText(LoginActivity.this,"If username valid, Password reset email sent",Toast.LENGTH_LONG).show();
//                        }
//                    });
//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//                    builder.show();
//                }
//            });
//
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userName = userNameEditText.getText().toString();
                    password = passwordEditText.getText().toString();
                    if (isConnected(LoginActivity.this))
                        new LoginPOST(LoginActivity.this, userName, password).execute("http://gymfitnessfirst.herokuapp.com/api/gym_users/login");
                    else
                        Toast.makeText(LoginActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void processResultsLogin(String s) throws JSONException {
        if (s.equals("false : ")) {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject jsonResponse = new JSONObject(s);
            key = jsonResponse.getString("id");
            userID = jsonResponse.getString("userId");

            if (isConnected(LoginActivity.this))
                new FlagGET(LoginActivity.this).execute("http://gymfitnessfirst.herokuapp.com/api/gym_users/" + userID + "?access_token=" + key);
            else
                Toast.makeText(LoginActivity.this, "No internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void processResultsFlag(User user) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("username", userName);
        editor.putString("password", password);
        editor.putString("key", key);
        editor.putString("userID", userID);
        editor.putString("name", user.getName());
        editor.putString("address", user.getAddress());
        editor.putString("phone_number", user.getPhone_number());
        editor.putString("email", user.getEmail());
        editor.apply();
        Boolean flag = user.getFlag();
        if(flag) {
            editor.putString("username", userName);
            editor.putString("password", password);
            editor.putString("key", key);
            editor.putString("userID", userID);
            editor.apply();

            if (flagrem)
            {
                editor.putString("usernamerem", userName);
                editor.putString("passwordrem", password);
                editor.apply();
            }

            if (isConnected(LoginActivity.this))
                new UserdetailsGET(LoginActivity.this).execute("http://gymfitnessfirst.herokuapp.com/api/gym_users/" + userID + "?access_token=" + key);
            else
                Toast.makeText(LoginActivity.this, "No internet", Toast.LENGTH_SHORT).show();
        } else{
            Intent i = new Intent();
            i.setClass(LoginActivity.this, GenderActivity.class);
            startActivity(i);
        }

    }

    public void processResults(User user) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("Gender", user.getSex());
        editor.putString("age", Integer.toString(user.getAge()));
        editor.putString("height", Integer.toString(user.getHeight()));
        editor.putString("dob", user.getDate_of_birth());
        editor.putString("name", user.getName());
        editor.putString("address", user.getAddress());
        editor.putString("phone_number", user.getPhone_number());
        editor.putString("email", user.getEmail());
        editor.apply();
        Intent i = new Intent();
        i.setClass(LoginActivity.this, GenderActivity.class);
        startActivity(i);
    }
}
