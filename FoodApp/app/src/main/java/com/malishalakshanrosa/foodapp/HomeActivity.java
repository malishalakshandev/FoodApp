package com.malishalakshanrosa.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView tv_user_id = findViewById(R.id.user_id);

        SharedPreferences USER_SHARED = getSharedPreferences("USER", Context.MODE_PRIVATE);
        String name = USER_SHARED.getString("USER_ID","00");
        tv_user_id.setText(name);



    }

    public void backToLogin(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toOrderMenu(View v){
        Intent intent = new Intent(this, OrderMenuActivity.class);
        startActivity(intent);
    }

    public void toAllOrders(View v){
        Intent intent = new Intent(this, AllOrdersActivity.class);
        startActivity(intent);
    }

    public void toCompletedOrders(View v){
        Intent intent = new Intent(this, CompletedOrdersActivity.class);
        startActivity(intent);
    }

    public void toPendingOrders(View v){
        Intent intent = new Intent(this, PendingOrdersActivity.class);
        startActivity(intent);
    }
    public void toUserProfile(View v){
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}