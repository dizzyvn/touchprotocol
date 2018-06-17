package com.example.dizzy.touch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpLoginTask extends AsyncTask<Integer, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private Activity parentActivity;
    private String uri = null;
    private String uid;
    private String password;

    HttpLoginTask(Activity parentActivity, String uid, String password) {
        this.parentActivity = parentActivity;
        this.uid = uid;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        android.net.Uri.Builder builder;
        String DEFAULTURL = "http://jachivi.sakura.ne.jp/login.php?";
        builder = Uri.parse(DEFAULTURL).buildUpon();
        builder.appendQueryParameter("id", uid);
        builder.appendQueryParameter("password", password);
        uri = builder.toString();
    }

    @Override
    protected String doInBackground(Integer... args) {
        return exec_get();
    }

    @Override
    protected void onPostExecute(String result) {
        switch (result) {
            case "0":
                Toast.makeText(parentActivity, "Wrong ID or Password, try agian.", Toast.LENGTH_LONG).show();
                break;
            case "1": {
                MainActivity.myProfile = UserProfile.createWithID(uid);
                Intent intent = new Intent(parentActivity, RegisterActivity.class);
                parentActivity.startActivity(intent);
                parentActivity.finish();
                break;
            }
            default: {
                Intent intent = new Intent(parentActivity, ListViewActivity.class);
                MainActivity.myProfile = new UserProfile(result);
                Log.v("LOGIN", MainActivity.myProfile.getFullParamStr());
                parentActivity.startActivity(intent);
                parentActivity.finish();
                break;
            }
        }
    }

    private String exec_get() {
        HttpURLConnection http = null;
        InputStream in = null;
        StringBuilder src = new StringBuilder();

        try {
            Log.v("PiLed", uri);
            URL url = new URL(uri);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.connect();

            int code = http.getResponseCode();
            Log.v("UPDATE", Integer.toString(code));

            in = http.getInputStream();
            byte[] line = new byte[1];
            int size;
            while (true) {
                size = in.read(line);
                if (size <= 0)
                    break;
                src.append(new String(line));
            }
            Log.v("UPDATE", "OUTPUT = " + src);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (http != null)
                    http.disconnect();
                if (in != null)
                    in.close();
            } catch (Exception ignored) {
            }
        }

        return src.toString();
    }
}
