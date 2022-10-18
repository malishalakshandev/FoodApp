package com.malishalakshanrosa.foodapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText et_userName, et_userMobile, et_userEmail, et_userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_userName = findViewById(R.id.user_name);
        et_userMobile = findViewById(R.id.user_mobile);
        et_userEmail = findViewById(R.id.user_email);
        et_userPassword = findViewById(R.id.user_password);

    }

    public void backToMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clearFields(){

        et_userName.setText("");
        et_userMobile.setText("");
        et_userEmail.setText("");
        et_userPassword.setText("");

    }

    public void insertUser(View v){

        String user_name = et_userName.getText().toString();
        String user_mobile = et_userMobile.getText().toString();
        String user_email = et_userEmail.getText().toString();
        String user_password = et_userPassword.getText().toString();

        RequestQueue queue =  Volley.newRequestQueue(this);

        String url = "http://beezzserver.com/malisha/FoodApp/fa_user/insert.php";
        System.out.println("URL>>>> "+ url);
        StringRequest request = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject respObj = new JSONObject(response);
                            String status = respObj.getString("status");
                            System.out.println("status>>>>"+status);
                            if(status.equals("1")){
                                Toast.makeText(RegisterActivity.this, "Error, Email Already Exist", Toast.LENGTH_LONG).show();
                            }else if(status.equals("2")){
                                Toast.makeText(RegisterActivity.this, "User Created Successfully", Toast.LENGTH_LONG).show();
                                clearFields();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        System.out.println("RESPONSE>>>="+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        System.out.println("ERROR>>>="+error);
                        Toast.makeText(RegisterActivity.this, "Error "+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_name", user_name);
                params.put("user_mobile", user_mobile);
                params.put("user_email", user_email);
                params.put("user_password", user_password);

                return params;
            }
        };

        queue.add(request);

    }

}