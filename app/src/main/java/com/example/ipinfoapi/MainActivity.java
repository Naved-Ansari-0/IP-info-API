package com.example.ipinfoapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String getIpApiLink = "https://api.ipify.org/?format=json";

    String getIpInfoApiLink = "https://ipinfo.io/";
//    https://ipinfo.io/xx.xx.xx.xx/geo

    Button getIp, getIpInfo;
    TextView showIp, showIpInfo;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIp = findViewById(R.id.getIp);
        getIpInfo = findViewById(R.id.getIpInfo);
        showIp = findViewById(R.id.showIp);
        showIpInfo = findViewById(R.id.showIpInfo);
        progressBar = findViewById(R.id.progressBar);

        getIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

//                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getIpApiLink, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String ip = response.getString("ip");
                                    showIp.setText(ip);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "ERROR 1", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                );

//                queue.add(request);
                MySingleton.getInstance(MainActivity.this).addToRequestQueue(request);

            }
        });

        getIpInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ip = showIp.getText().toString();
                if(ip.equals(""))
                    return;

                progressBar.setVisibility(View.VISIBLE);

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getIpInfoApiLink + ip + "/geo", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String ip = response.getString("ip");
                                    String city = response.getString("city");
                                    String region = response.getString("region");
                                    String country = response.getString("country");
                                    String loc = response.getString("loc");
                                    String org = response.getString("org");
                                    String postal = response.getString("postal");
                                    String timezone = response.getString("timezone");
                                    showIpInfo.setText("IP : " + ip + "\n" +
                                            "City : " + city + "\n" +
                                            "Region : " + region + "\n" +
                                            "Country : " + country + "\n" +
                                            "Location : " + loc + "\n" +
                                            "Organization : " + org + "\n" +
                                            "Postal : " + postal + "\n" +
                                            "Timezone : " + timezone + "\n"
                                            );
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "ERROR 2", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                );
                queue.add(request);
            }
        });
    }

}