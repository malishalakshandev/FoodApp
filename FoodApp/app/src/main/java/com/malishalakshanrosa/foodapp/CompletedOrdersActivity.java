package com.malishalakshanrosa.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class CompletedOrdersActivity extends AppCompatActivity {

    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_orders);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences USER_SHARED = getSharedPreferences("USER", Context.MODE_PRIVATE);
        user_id = USER_SHARED.getString("USER_ID","00");

        loadCompletedOrders();

    }

    public void loadCompletedOrders(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beezzserver.com/malisha/FoodApp/fa_order/index.php?comorder=C&uid="+user_id;
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("RESPONSE>>>"+response);
                        setCompletedOrders(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(CompletedOrdersActivity.this, "Error="+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);
    }

    public void backToHome(View v){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void setCompletedOrders(JSONArray response){

        List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();

        try {

            //get response results and add to hash map(as key values)
            for (int i = 0; i < response.length(); i++){
                JSONObject obj = response.getJSONObject(i);

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("order_id", obj.getString("order_id"));
                map.put("product_name", obj.getString("product_name"));
                list.add(map);
            }

            ListView lv = findViewById(R.id.LV_completed_orders);

            //2. Layout file
            int layout = R.layout.single_completed_order;

            //3. TextView ids
            int[] views = {R.id.order_id, R.id.product_name};

            //4.SQL Coloumns name
            String[] columns = {"order_id", "product_name",};

            //5. set data to textviews by matching them
            SimpleAdapter adapter = new SimpleAdapter(this, list, layout, columns, views);

            //6. data set to ListView
            lv.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error + "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}