package com.malishalakshanrosa.foodapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfirmOrderActivity extends AppCompatActivity {

    String user_id, product_id;
    TextView tv_product_id, tv_product_name, tv_product_price;
    EditText et_order_qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        tv_product_id = findViewById(R.id.product_id);
        tv_product_name = findViewById(R.id.product_name);
        tv_product_price = findViewById(R.id.product_price);
        et_order_qty = findViewById(R.id.order_qty);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        product_id = bundle.getString("PRODUCT_ID");



    }

    @Override
    protected void onResume() {
        super.onResume();

        loadProductDetails();

        SharedPreferences USER_SHARED = getSharedPreferences("USER", Context.MODE_PRIVATE);
        user_id = USER_SHARED.getString("USER_ID","00");

    }

    public void backToOrderMenu(View v){
        Intent intent = new Intent(this, OrderMenuActivity.class);
        startActivity(intent);
    }

    public void loadProductDetails(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beezzserver.com/malisha/FoodApp/fa_product/index.php?pid="+product_id;
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        setProduct(response);
                    }
                }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ConfirmOrderActivity.this, "Error="+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);


    }

    public void setProduct(JSONArray response){

        try {

            //get response results and add to hash map(as key values)
            for (int i = 0; i < response.length(); i++){
                JSONObject obj = response.getJSONObject(i);

                String product_name = obj.getString("product_name");
                String product_price = obj.getString("product_price");

                tv_product_name.setText(product_name);
                tv_product_price.setText(product_price);
            }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error + "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void confirmPurchase(View v){

        String product_name = tv_product_name.getText().toString();
        String product_price = tv_product_price.getText().toString();
        String order_qty = et_order_qty.getText().toString();

        RequestQueue queue =  Volley.newRequestQueue(this);

        String url = "http://beezzserver.com/malisha/FoodApp/fa_order/insert.php";

        StringRequest request = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(BookActivity.this, "Thank you for your Booking, "+response , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ConfirmOrderActivity.this, OrderSuccessActivity.class);
                        intent.putExtra("MSG", response);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ConfirmOrderActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("product_id", product_id);
                params.put("order_qty", order_qty);
                params.put("order_status", "P");

                return params;
            }
        };
        queue.add(request);


    }


}