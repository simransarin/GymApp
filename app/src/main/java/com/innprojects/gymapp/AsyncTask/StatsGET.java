package com.innprojects.gymapp.AsyncTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.innprojects.gymapp.activities.MainActivity;
import com.innprojects.gymapp.javaModels.Stat;
import org.json.JSONArray;
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
 * Created by simransarin on 23/10/17.
 */

public class StatsGET extends AsyncTask<String, Void, Stat[]> {

    private MainActivity mainActivity;
    private ProgressDialog progressDialog;

    public StatsGET(MainActivity mActivity) {
        mainActivity = mActivity;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setMessage("Getting Stats...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();
    }
    private Stat[] parseJson(String json) {
        try {
            JSONArray stats = new JSONArray(json);
            if(stats.length() <= 7) {
                Stat[] output = new Stat[stats.length()];
                for (int i = 0; i < stats.length(); i++) {
                    JSONObject statsJSONObject = stats.getJSONObject(i);
                    String weight = statsJSONObject.getString("weight");
                    String impedance = statsJSONObject.getString("impedance");
                    String BMI = statsJSONObject.getString("BMI");
                    String body_fat = statsJSONObject.getString("body_fat");
                    String muscle = statsJSONObject.getString("muscle");
                    String water = statsJSONObject.getString("water");
                    String protein = statsJSONObject.getString("protein");
                    String bone_mass = statsJSONObject.getString("bone_mass");
                    String BMR = statsJSONObject.getString("BMR");
                    String visceral_fat = statsJSONObject.getString("visceral_fat");
                    String fitness_age = statsJSONObject.getString("fitness_age");
                    output[i] = new Stat(weight, impedance, BMI, body_fat, muscle, water, protein, bone_mass, BMR, visceral_fat, fitness_age);
                }
                return output;
            } else {
                Stat[] output = new Stat[7];
                int j =0;
                for (int i = stats.length() - 7; i < stats.length(); i++) {
                    JSONObject statsJSONObject = stats.getJSONObject(i);
                    String weight = statsJSONObject.getString("weight");
                    String impedance = statsJSONObject.getString("impedance");
                    String BMI = statsJSONObject.getString("BMI");
                    String body_fat = statsJSONObject.getString("body_fat");
                    String muscle = statsJSONObject.getString("muscle");
                    String water = statsJSONObject.getString("water");
                    String protein = statsJSONObject.getString("protein");
                    String bone_mass = statsJSONObject.getString("bone_mass");
                    String BMR = statsJSONObject.getString("BMR");
                    String visceral_fat = statsJSONObject.getString("visceral_fat");
                    String fitness_age = statsJSONObject.getString("fitness_age");
                    output[j] = new Stat(weight, impedance, BMI, body_fat, muscle, water, protein, bone_mass, BMR, visceral_fat, fitness_age);
                    j++;
                }
                return output;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected Stat[] doInBackground(String... params) {
        if (params.length == 0)
            return null;
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection =
                    (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream =
                    urlConnection.getInputStream();
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
    protected void onPostExecute(Stat[] stats) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        if (stats != null) {
            mainActivity.processResults(stats);
        } else {
            Toast.makeText(mainActivity, "Network error!", Toast.LENGTH_SHORT).show();
        }
    }
}

