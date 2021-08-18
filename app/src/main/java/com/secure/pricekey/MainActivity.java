package com.secure.pricekey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    MaterialTextView m1_price, m2_price, m3_price, m4_price, m5_price, m6_price, m7_price, m8_price;
    ArrayList<Rate> rateArrayList = new ArrayList<Rate>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        fetch_data();
    }

    void init(){
        m1_price = findViewById(R.id.m1_price);
        m2_price = findViewById(R.id.m2_price);
        m3_price = findViewById(R.id.m3_price);
        m4_price = findViewById(R.id.m4_price);
        m5_price = findViewById(R.id.m5_price);
        m6_price = findViewById(R.id.m6_price);
        m7_price = findViewById(R.id.m7_price);
        m8_price = findViewById(R.id.m8_price);
    }

    private void fetch_data(){
        String url = "https://metals-api.com/api/latest?access_key=794k87o990z9210evqhy6r4v2yy677nvkornw45byy1k4dptptobrnrt4267&base=INR&symbols=XAU%2CXAG%2CXPT%2CXPD%2CXCU%2CALU%2CNI%2CZNC";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //JSONArray rateJsonArray = response.getJSONArray("rates");
                            //JSONObject rateJsonObject = rateJsonArray.getJSONObject();
                            JSONObject rateJsonObject = response.getJSONObject("rates");
                            Rate rate = new Rate(
                                    rateJsonObject.getString("XAU"),
                                    rateJsonObject.getString("XAG"),
                                    rateJsonObject.getString("XPT"),
                                    rateJsonObject.getString("XPD"),
                                    rateJsonObject.getString("XCU"),
                                    rateJsonObject.getString("ALU"),
                                    rateJsonObject.getString("NI"),
                                    rateJsonObject.getString("ZNC")
                            );
                            rateArrayList.add(rate);
                            setValue();
                            Log.d("abc", "successs");
                        } catch (JSONException e) {
                            Log.d("abc", e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("abc", error.toString());
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void setValue(){
        Rate rate = rateArrayList.get(0);
        Log.d("ss", Integer.toString(rateArrayList.size()));
        m1_price.setText(rate.gold + "\nGold");
        m2_price.setText(rate.silver + "\nSilver");
        m3_price.setText(rate.platinum + "\nPlatinum");
        m4_price.setText(rate.palladium + "\nPalladium");
        m5_price.setText(rate.copper + "\nCopper");
        m6_price.setText(rate.aluminum + "\nAluminium");
        m7_price.setText(rate.nickel + "\nNickel");
        m8_price.setText(rate.zinc + "\nZinc");
    }

    private void calculation(){
        
    }
}