package com.example.dizzy.touch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListViewActivity extends Activity {
    static public ArrayAdapter<String> adapter;
    @SuppressLint("StaticFieldLeak")
    static public ListView listView;
    static public ArrayList<UserProfile> userProfiles;
    static public ArrayList<String> userNames;
    private Context context;
    private String uri;

    public static void setUserProfiles(ArrayList<UserProfile> newUserProfiles) {
        userNames.clear();
        userProfiles.clear();

        for (int i = 0; i < newUserProfiles.size(); i++) {
            UserProfile profile = newUserProfiles.get(i);
            userProfiles.add(profile);
            userNames.add(Integer.toString(i + 1) + ". " + profile.getName());
            Log.v("UPDATE", profile.getName());
        }

        adapter.notifyDataSetChanged();
        listView.invalidateViews();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        context = this;

        userProfiles = new ArrayList<>();

        // GET THE FIRST USER LIST
        android.net.Uri.Builder builder;
        String DEFAULTURL = "http://jachivi.sakura.ne.jp/read.php?";
        builder = Uri.parse(DEFAULTURL).buildUpon();
        builder.appendQueryParameter("uid", MainActivity.myProfile.featureMap.get("uid"));
        uri = builder.toString();
        String output = exec_get();
        userNames = new ArrayList<>();

        if (output.length() > 0) {
            Log.v("LOGIN", output);
            String users[] = output.split(";");
            Log.v("LIST", Integer.toString(users.length));
            for (String user : users) {
                userProfiles.add(new UserProfile(user));
            }

            for (int i = 0; i < userProfiles.size(); i++) {
                UserProfile profile = userProfiles.get(i);
                userNames.add(Integer.toString(i + 1) + ". " + profile.getName());
                Log.v("ListView", profile.getName());
            }
        }

        listView = findViewById(R.id.listViewContainer);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, userNames);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        // Set OnClick Listener for ListView item
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When click, show detail of each user
                Intent intent = new Intent(context, SingleViewActivity.class);
                intent.putExtra("profile", userProfiles.get(position));
                context.startActivity(intent);
            }
        });

        // Set OnClick Listener for SYNC button
        Button syncButton = findViewById(R.id.syncButton);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = MainActivity.myProfile.featureMap.get("uid");
                HttpReadTask task = new HttpReadTask(uid);
                task.execute();
            }
        });

        // Set OnClick Listener for EDIT button
        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditActivity.class);
                context.startActivity(intent);
                finish();
            }
        });
    }

    private String exec_get() {
        HttpURLConnection http = null;
        InputStream in = null;
        StringBuilder src = new StringBuilder();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Log.v("LOGIN", uri);
            URL url = new URL(uri);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.connect();

            int code = http.getResponseCode();
            Log.v("LOGIN", Integer.toString(code));

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

        Log.v("LOGIN", src.toString());
        return src.toString();
    }
}

