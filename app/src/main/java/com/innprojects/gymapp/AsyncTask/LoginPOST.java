package com.innprojects.gymapp.AsyncTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.innprojects.gymapp.activities.LoginActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by simransarin on 27/07/17.
 */

public class LoginPOST extends AsyncTask<String, Void, String> {

    LoginActivity mActivity;
    private ProgressDialog progressDialog;
    private String password;
    private String username;

    public LoginPOST(LoginActivity loginActivity, String un, String pw){
        mActivity = loginActivity;
        username = un;
        password = pw;
    }

    protected void onPreExecute() {
        progressDialog= new ProgressDialog(mActivity);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();
    }

    protected String doInBackground(String... params) {

        try {
            URL url = new URL(params[0]); // here is your URL path

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("username", username);
            postDataParams.put("password", password);
            Log.i("params", postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { //HTTP_OK : 201 http_201_created
                BufferedReader in=new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line="";
                while((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }
                in.close();
                Log.e("response string: ", sb.toString() );
                return sb.toString();
            }
            else {
                Log.e("doInBackground: ", new String("false : "+responseCode) ); // : 400 http_400_bad_request
                return new String("false : ");
            }
        } catch (Exception e) {
            Log.e("doInBackground: ", new String("Exception: " + e.getMessage()) );
            return new String("Exception: ");
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        if (s.equals("Exception: "))
            Toast.makeText(mActivity, "Network error", Toast.LENGTH_SHORT).show();
        else
            try {
                mActivity.processResultsLogin(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}
