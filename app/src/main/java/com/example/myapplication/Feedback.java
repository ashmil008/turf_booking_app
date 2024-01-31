package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {
    Button b1;
    EditText editText;
    SharedPreferences sp;
    String ip="",url="",fd="",rtb;
    RatingBar rb;


    ArrayList<String> tid,tname,place;
    Spinner ss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        editText=(EditText)findViewById(R.id.editTextTextPersonName7);
        b1=(Button) findViewById(R.id.button8);
        rb=(RatingBar) findViewById(R.id.ratingBar2);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fd=editText.getText().toString();



                rtb=rb.getRating()+"";


                if (fd.equalsIgnoreCase(""))
                {
                    editText.setError("Required");
                    editText.requestFocus();
                }
                else {

                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(Feedback.this);
                    String url = "http://" + sp.getString("ip", "") + ":5000/feedback";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("success")) {
                                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(getApplicationContext(), UserHome.class));

                                } else {


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Er" + e, Toast.LENGTH_LONG).show();

                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("feed", fd);
                            params.put("rating", rtb);

                            params.put("uid", sp.getString("lid", ""));

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }



            }
        });



        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    }



}