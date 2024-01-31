package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
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

public class FeedbackView extends AppCompatActivity {


    ListView lv2;

    SharedPreferences sp;
    String url="",ip="";
    Button b1;

    ArrayList<String> uname,feedback,rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_view);

        lv2=(ListView)findViewById(R.id.list);


        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        ip=sp.getString("ip","");
        url ="http://"+ip+":5000/viewfeedback";




        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(FeedbackView.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    uname=new ArrayList<>();
                    feedback=new ArrayList<>();
                    rating=new ArrayList<>();
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        uname.add(jo.getString("fname")+jo.getString("mname")+jo.getString("lname"));
                        feedback.add(jo.getString("feedback"));
                        rating.add(jo.getString("rating"));
                    }

                    lv2.setAdapter(new custom3feed(FeedbackView.this,uname,feedback,rating));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("tid", sp.getString("lid",""));

                return params;
            }
        };



        // Add the request to the RequestQueue.
        queue.add(stringRequest);




    }
}