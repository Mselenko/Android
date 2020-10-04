package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int attemptCount = 3;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private TextView errorTextView;
    private TextView validTextView;
    private TextView attemptTextView;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(onClickLoginButton);

        userNameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        errorTextView = findViewById(R.id.errorTextView);
        validTextView = findViewById(R.id.validTextView);
        attemptTextView = findViewById(R.id.attemptsTextView);

    }

    private View.OnClickListener onClickLoginButton = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            String userName = userNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            LoginManager loginManager = new LoginManager(userName, password);

            if(loginManager.hasValidCredentials())
            {

                validTextView.setText(R.string.valid_credentials);
                validTextView.setVisibility(View.VISIBLE);

                errorTextView.setVisibility(View.INVISIBLE);

                attemptCount = 3;
                attemptTextView.setVisibility(View.INVISIBLE);

            }else if(attemptCount == 1)
            {

                errorTextView.setText(R.string.account_lockedError);

                validTextView.setVisibility(View.INVISIBLE);

                attemptTextView.setVisibility(View.INVISIBLE);

                loginButton.setBackgroundColor(Color.parseColor("#AD303F9F"));
                loginButton.setEnabled(false);

            }else {

                attemptCount--;
                attemptTextView.setVisibility(View.VISIBLE);
                attemptTextView.setText(getString(R.string.attempts_left) +" "+ attemptCount);

                errorTextView.setVisibility(View.VISIBLE);
                errorTextView.setText(getString(R.string.error_text));

                validTextView.setVisibility(View.INVISIBLE);

            }
        }
    };
}
