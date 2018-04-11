package com.innprojects.gymapp.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.innprojects.gymapp.activities.LoginActivity;
import com.innprojects.gymapp.javaModels.User;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by simransarin on 21/10/17.
 */

public class FlagGET extends AsyncTask<String, Void, User> {
    private LoginActivity loginActivity;

    public FlagGET(LoginActivity activity) {
        loginActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    private User parseJson(String json) {
        User outputUser;
        try {
            JSONObject finalObject = new JSONObject(json);
            int registration_number = finalObject.getInt("registration_number");
            String name = finalObject.getString("name");
            String address = finalObject.getString("address");
            String phone_number = finalObject.getString("phone_number");
            Boolean flag = finalObject.getBoolean("flag");
            String username = finalObject.getString("username");
            String email = finalObject.getString("email");
            String id = finalObject.getString("id");
            outputUser = new User(registration_number, name, address, phone_number, flag, username, email, id);
            Log.i("Log_BUSDownloadTask", outputUser.getFlag().toString());
            return outputUser;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected User doInBackground(String... params) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            Scanner s = new Scanner(inputStream);
            while (s.hasNext()) {
                buffer.append(s.nextLine());
            }
            Log.i("jsondata", buffer.toString());
        } catch (MalformedURLException e) {
            return null;
        } catch (ProtocolException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return parseJson(buffer.toString());
    }

    @Override
    protected void onPostExecute(User user) {
        if (user != null) {
            loginActivity.processResultsFlag(user);
        } else {
            Toast.makeText(loginActivity, "Network error", Toast.LENGTH_SHORT).show();
        }
    }

}
