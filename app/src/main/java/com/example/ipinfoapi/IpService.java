package com.example.ipinfoapi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class IpService {

    public Context context;
    public String ip;
    public interface VolleyResponseListener{
        void OnError(String message);
        void OnResponse(String ip);
    }

    public IpService(Context context){
        this.context = context;
    }

    public void getIp(String url, VolleyResponseListener volleyResponseListener){
        ip = "";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ip = response.getString("ip");
                }
                catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                volleyResponseListener.OnResponse(ip);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseListener.OnError("Something went wrong");
                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

}
