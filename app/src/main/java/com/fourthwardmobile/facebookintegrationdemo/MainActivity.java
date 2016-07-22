package com.fourthwardmobile.facebookintegrationdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {

    private LoginButton mLoginButton;
    private TextView mLoginTextView;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialzie Facebook SDK. Bust be called before setting the view
        //since it contains a Facebook Login Button
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        mCallbackManager = CallbackManager.Factory.create();

        mLoginTextView = (TextView)findViewById(R.id.login_textview);

        mLoginButton = (LoginButton)findViewById(R.id.login_button);
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                mLoginTextView.setText("Auth Token:" + loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                mLoginTextView.setText("Login Cancelled");

            }

            @Override
            public void onError(FacebookException error) {
                mLoginTextView.setText("Login Error");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }
}
