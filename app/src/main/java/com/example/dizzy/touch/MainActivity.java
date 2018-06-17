package com.example.dizzy.touch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    static public UserProfile myProfile;
    private EditText idEditText;
    private EditText passwordEditText;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);

        idEditText = findViewById(R.id.idInput);
        passwordEditText = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uid = idEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                HttpLoginTask task = new HttpLoginTask(context, uid, password);
                task.execute();
            }
        });
    }
}
