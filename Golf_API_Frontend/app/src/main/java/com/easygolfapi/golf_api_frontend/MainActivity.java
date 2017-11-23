package com.easygolfapi.golf_api_frontend;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Context;


import com.android.volley.RequestQueue;

public class MainActivity extends AppCompatActivity {
    private static final int INTERNET_PERMISSION_RESULT = 17;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.INTERNET}, INTERNET_PERMISSION_RESULT);
        }

        Button button1 =  (Button) findViewById(R.id.home_browse_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayersMainActivity.class);
                startActivity(intent);
            }
        });

        Button button2 =  (Button) findViewById(R.id.home_signup_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, PlayersSignUpActivity.class);
                startActivity(intent2);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == INTERNET_PERMISSION_RESULT){
            if(grantResults.length > 0){
            }
        }
    }

}



