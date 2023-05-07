package com.example.ebayapiproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView forgotPassTextView;
    private TextView createAccTextView;
    private TextView useOfflineTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forgotPassTextView = findViewById(R.id.forgot_pass);
        createAccTextView = findViewById(R.id.create_acc);
        useOfflineTextView = findViewById(R.id.offline_mode);

        forgotPassTextView.setOnClickListener(v -> {
            String url = "https://accounts.ebay.com/acctxs/user";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        createAccTextView.setOnClickListener(v -> {
            String url = "https://signup.ebay.com/pa/crte?siteid=0&co_partnerId=0&UsingSSL=1&rv4=1&ru=https%3A%2F%2Fwww.ebay.com%2Fmys%2Fhome%3Fsource%3DGBH&signInUrl=https%3A%2F%2Fwww.ebay.com%2Fsignin%3Fsgn%3Dreg%26siteid%3D0%26co_partnerId%3D0%26UsingSSL%3D1%26rv4%3D1%26ru%3Dhttps%253A%252F%252Fwww.ebay.com%252Fmys%252Fhome%253Fsource%253DGBH";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        useOfflineTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Offline.class);
                startActivity(intent);
            }
        });
    }
}