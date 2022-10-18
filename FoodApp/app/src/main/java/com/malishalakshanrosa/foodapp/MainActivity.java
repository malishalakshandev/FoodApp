package com.malishalakshanrosa.foodapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText et_user_email, et_user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_user_email = findViewById(R.id.user_email);
        et_user_password = findViewById(R.id.user_password);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences settings = getSharedPreferences("USER", Context.MODE_PRIVATE);
        settings.edit().clear().commit();

    }

    public void register(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(View v){

        String user_email = et_user_email.getText().toString();
        String user_password = et_user_password.getText().toString();

        RequestQueue queue =  Volley.newRequestQueue(this);

        String url = "http://beezzserver.com/malisha/FoodApp/fa_user/index.php?userlog=log";

        StringRequest request = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("RESPONSE>>>>>>>>>>>>>>>>>>>>"+response);
                        try {

                            JSONObject respObj = new JSONObject(response);
                            String status = respObj.getString("status");
                            System.out.println("status>>>>"+status);

                            if(status.equals("1")){
                                String user_id = respObj.getString("msg");
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();

                            SharedPreferences profile = getSharedPreferences("USER", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = profile.edit();
                            editor.putString("USER_ID", user_id);
                            editor.commit();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);

                            }else if(status.equals("2")){
                                Toast.makeText(MainActivity.this, "Invalid Login", Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_email", user_email);
                params.put("user_password", user_password);

                return params;
            }
        };
        queue.add(request);




    }
}