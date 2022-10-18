package com.malishalakshanrosa.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String msg = bundle.getString("MSG");
        TextView tv = findViewById(R.id.success_message);
        tv.setText(msg.split("\"")[5]);

    }

    public void backToHome(View v){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}