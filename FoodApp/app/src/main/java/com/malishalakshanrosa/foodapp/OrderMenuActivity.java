package com.malishalakshanrosa.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class OrderMenuActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_menu);

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadOrderMenu();
    }

    public void loadOrderMenu(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beezzserver.com/malisha/FoodApp/fa_product/index.php";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("RESPONSE>>>"+response);
                        setOrderMenu(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(OrderMenuActivity.this, "Error="+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);
    }

    public void setOrderMenu(JSONArray response){

        List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();

        try {

            //get response results and add to hash map(as key values)
            for (int i = 0; i < response.length(); i++){
                JSONObject obj = response.getJSONObject(i);

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("product_id", obj.getString("product_id"));
                map.put("product_name", obj.getString("product_name"));
                map.put("product_price", obj.getString("product_price"));
                list.add(map);
            }

            ListView lv = findViewById(R.id.LV_order_menu);

            //2. Layout file
            int layout = R.layout.single_menu;

            //3. TextView ids
            int[] views = {R.id.product_id, R.id.product_name, R.id.product_price};

            //4.SQL Coloumns name
            String[] columns = {"product_id", "product_name", "product_price"};

            //5. set data to textviews by matching them
            SimpleAdapter adapter = new SimpleAdapter(this, list, layout, columns, views);

            //6. data set to ListView
            lv.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error + "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void backToHome(View v){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void confirmOrder(View v){

         LinearLayout ll = (LinearLayout) v.getParent();
         TextView tv_product_id = ll.findViewById(R.id.product_id);
         String product_id = tv_product_id.getText().toString();

         TextView tv_product_name = findViewById(R.id.product_name);
         String product_name = tv_product_name.getText().toString();

         TextView tv_product_price = findViewById(R.id.product_price);
         String product_price = tv_product_price.getText().toString();

        Intent intent = new Intent(this, ConfirmOrderActivity.class);
        intent.putExtra("PRODUCT_ID", product_id);

        startActivity(intent);
    }

}