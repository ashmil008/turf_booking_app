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

public class ProfileViewUser extends AppCompatActivity {

    ListView lv1;

    SharedPreferences sp;
    String url="",ip="";
    Button b1;
    ArrayList<String> Name,phno,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view_user);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        lv1=(ListView)findViewById(R.id.lv1);
        ip=sp.getString("ip","");
        url ="http://"+ip+":5000/viewuser";




        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ProfileViewUser.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    Name=new ArrayList<>();
                    phno=new ArrayList<>();
                    email=new ArrayList<>();
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        Name.add(jo.getString("fname")+jo.getString("lname"));
                        phno.add(jo.getString("phno"));
                        email.add(jo.getString("email"));
                    }

                    lv1.setAdapter(new custom3feed(ProfileViewUser.this,Name,phno,email));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
            }
        });



        // Add the request to the RequestQueue.
        queue.add(stringRequest);





    }
}