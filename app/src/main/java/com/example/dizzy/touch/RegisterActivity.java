package com.example.dizzy.touch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {
    private EditText inputName;
    private EditText inputEmail;
    private EditText inputPhone;
    private EditText inputBirthday;
    private EditText inputFacebook;
    private EditText inputTwitter;
    private EditText inputGithub;
    private Activity context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.edit_view);

        inputName = findViewById(R.id.inputViewName);
        inputEmail = findViewById(R.id.inputViewEmail);
        inputPhone = findViewById(R.id.inputViewPhone);
        inputBirthday = findViewById(R.id.inputViewBirthday);
        inputFacebook = findViewById(R.id.inputViewFacebook);
        inputTwitter = findViewById(R.id.inputViewTwitter);
        inputGithub = findViewById(R.id.inputViewGithub);

        Map<String, String> featureMap = MainActivity.myProfile.getFeatureMap();

        inputName.setText(featureMap.get("name"));
        inputEmail.setText(featureMap.get("email"));
        inputPhone.setText(featureMap.get("phone"));
        inputBirthday.setText(featureMap.get("birthday"));
        inputFacebook.setText(featureMap.get("facebook"));
        inputTwitter.setText(featureMap.get("twitter"));
        inputGithub.setText(featureMap.get("github"));

        Button uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Map<String, String> uploadMap = new HashMap<>();
                uploadMap.put("uid", MainActivity.myProfile.getUid());
                uploadMap.put("name", inputName.getText().toString());
                uploadMap.put("email", inputEmail.getText().toString());
                uploadMap.put("phone", inputPhone.getText().toString());
                uploadMap.put("birthday", inputBirthday.getText().toString());
                uploadMap.put("facebook", inputFacebook.getText().toString());
                uploadMap.put("twitter", inputTwitter.getText().toString());
                uploadMap.put("github", inputGithub.getText().toString());

                MainActivity.myProfile.setFeatureMap(uploadMap);
                HttpRegisterTask task = new HttpRegisterTask(context, uploadMap);
                task.execute();
            }
        });
    }
}
