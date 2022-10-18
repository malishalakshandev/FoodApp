package com.malishalakshanrosa.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    String user_id;
    EditText et_user_name, et_user_mobile, et_user_email, et_user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        et_user_name = findViewById(R.id.user_name);
        et_user_mobile = findViewById(R.id.user_mobile);
        et_user_email = findViewById(R.id.user_email);
        et_user_password = findViewById(R.id.user_password);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences USER_SHARED = getSharedPreferences("USER", Context.MODE_PRIVATE);
        user_id = USER_SHARED.getString("USER_ID","00");

        loadLoggedUserProfile();

    }

    public void loadLoggedUserProfile(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beezzserver.com/malisha/FoodApp/fa_user/index.php?uid="+user_id;
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("RESPONSE>>>"+response);
                        setUserProfile(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(UserProfileActivity.this, "Error="+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);

    }

    public void setUserProfile(JSONArray response){

        try {

            //get response results and add to hash map(as key values)
            for (int i = 0; i < response.length(); i++){
                JSONObject obj = response.getJSONObject(i);

                String user_name = obj.getString("user_name");
                String user_mobile = obj.getString("user_mobile");
                String user_email = obj.getString("user_email");
                String user_password = obj.getString("user_password");

                et_user_name.setText(user_name);
                et_user_mobile.setText(user_mobile);
                et_user_email.setText(user_email);
                et_user_password.setText(user_password);
            }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error + "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void backToHome(View v){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}