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
import java.util.Map;

public class HttpRegisterTask extends AsyncTask<Integer, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private Activity parentActivity;

    private String uri = null;
    private Map<String, String> featureMap;

    HttpRegisterTask(Activity parentActivity, Map<String, String> featureMap) {
        this.parentActivity = parentActivity;
        this.featureMap = featureMap;
    }

    @Override
    protected void onPreExecute() {
        String DEFAULTURL = "http://jachivi.sakura.ne.jp/write.php?";
        Uri.Builder builder = Uri.parse(DEFAULTURL).buildUpon();
        for (String key : featureMap.keySet()) {
            builder.appendQueryParameter(key, featureMap.get(key));
        }
        uri = builder.toString();
    }

    @Override
    protected String doInBackground(Integer... args) {
        return exec_get();
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("1")) {
            Toast.makeText(parentActivity, "Upload successed", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(parentActivity, ListViewActivity.class);
            parentActivity.startActivity(intent);
            parentActivity.finish();
        } else {
            Toast.makeText(parentActivity, "Failed to upload your profile, " +
                    "please try agian later", Toast.LENGTH_LONG).show();
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