package com.example.dizzy.touch;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpReadTask extends AsyncTask<Integer, Void, String> {
    private String uri = null;
    private String uid;
    private ArrayList<UserProfile> userProfiles;

    HttpReadTask(String uid) {
        this.uid = uid;
        this.userProfiles = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        String DEFAULTURL = "http://jachivi.sakura.ne.jp/read.php?";
        Uri.Builder builder = Uri.parse(DEFAULTURL).buildUpon();
        builder.appendQueryParameter("uid", uid);
        uri = builder.toString();
    }

    @Override
    protected String doInBackground(Integer... args) {
        return exec_get();
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.length() > 0) {
            // Split the result in to user's profile strings
            String users[] = result.split(";");
            for (String user : users) {
                userProfiles.add(new UserProfile(user));
            }
            ListViewActivity.setUserProfiles(userProfiles);
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