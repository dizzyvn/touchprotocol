package com.example.dizzy.touch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.util.Map;

public class SingleViewActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);

        TextView viewName = findViewById(R.id.outputViewName);
        TextView viewEmail = findViewById(R.id.outputViewEmail);
        TextView viewPhone = findViewById(R.id.outputViewPhone);
        TextView viewBirthday = findViewById(R.id.outputViewBirthday);
        TextView viewFacebook = findViewById(R.id.outputViewFacebook);
        TextView viewTwitter = findViewById(R.id.outputViewTwitter);
        TextView viewGithub = findViewById(R.id.outputViewGithub);

        Intent intent = getIntent();
        final UserProfile userProfile = (UserProfile) intent.getSerializableExtra("profile");
        Map<String, String> featureMap = userProfile.getFeatureMap();

        viewName.setText(featureMap.get("name"));
        viewEmail.setText(featureMap.get("email"));
        viewPhone.setText(featureMap.get("phone"));
        viewBirthday.setText(featureMap.get("birthday"));

        String facebook = featureMap.get("facebook");
        if (!facebook.equals("")) facebook = "https://www.facebook.com/" + facebook;
        else facebook = "";
        viewFacebook.setText(facebook);

        String twitter = featureMap.get("twitter");
        if (!twitter.equals("")) twitter = "https://twitter.com/" + twitter;
        else facebook = "";
        viewTwitter.setText(twitter);

        String github = featureMap.get("github");
        if (!github.equals("")) github = "https://github.com/" + github;
        else github = "";
        viewGithub.setText(github);
    }
}
