package com.easygolfapi.golf_api_frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class PlayersMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_main);

        final TextView mTest = (TextView) findViewById(R.id.testTextView);

        String getAllPlayersUrl = "https://easygolfapi.appspot.com/players";

        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        StringRequest mStringRequest = new StringRequest(
                Request.Method.GET,
                getAllPlayersUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mTest.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTest.setText(error.getMessage());
                    }
                }
        );

        MySingleton.getInstance(this).addToRequestQueue(mStringRequest);
    }
}
