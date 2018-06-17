package com.example.dizzy.touch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
        viewFacebook.setText(featureMap.get("facebook"));
        viewTwitter.setText(featureMap.get("twitter"));
        viewGithub.setText(featureMap.get("github"));
    }
}
