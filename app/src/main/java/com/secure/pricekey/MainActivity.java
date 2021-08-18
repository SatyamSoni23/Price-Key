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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
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
        String url = "https://metals-api.com/api/latest?access_key=oi60gz8dv4ew13co42d41xw7ny5ivowkvub99s23j73bdi0dmu8mh5qaeiig&base=INR&symbols=XAU%2CXAG%2CXPT%2CXPD%2CXCU%2CALU%2CNI%2CZNC";
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
        Log.d("ss", Integer.toString(rateArrayList.size()));
        Rate rate = rateArrayList.get(0);

        Double gold = Double.parseDouble(rate.gold);
        Double silver = Double.parseDouble(rate.silver);
        Double platinum = Double.parseDouble(rate.platinum);
        Double palladium = Double.parseDouble(rate.palladium);
        Double copper = Double.parseDouble(rate.copper);
        Double aluminium = Double.parseDouble(rate.aluminum);
        Double nickel = Double.parseDouble(rate.nickel);
        Double zinc = Double.parseDouble(rate.zinc);

        double c = 28.3495;

        gold = (gold*10)/c;
        silver = (silver*1000)/c;
        platinum = (platinum*10)/c;
        palladium = (palladium*1000)/c;
        copper = (copper*1000)/c;
        aluminium = (aluminium*1000)/c;
        nickel = (nickel*1000)/c;
        zinc = (zinc*1000)/c;

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        nf.setMaximumFractionDigits(2);

        m1_price.setText(nf.format(gold));
        m2_price.setText(nf.format(silver));
        m3_price.setText(nf.format(platinum));
        m4_price.setText(nf.format(palladium));
        m5_price.setText(nf.format(copper));
        m6_price.setText(nf.format(aluminium));
        m7_price.setText(nf.format(nickel));
        m8_price.setText(nf.format(zinc ));
    }

}