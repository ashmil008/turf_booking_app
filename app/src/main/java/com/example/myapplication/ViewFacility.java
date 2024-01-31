package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class ViewFacility extends AppCompatActivity {
    ListView lv2;

    SharedPreferences sp;
    String url="",ip="";
    Button b1;

    ArrayList<String> facility,descrip,img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_facility);
        lv2=(ListView)findViewById(R.id.facilityview);
        b1=(Button)findViewById(R.id.btn);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n=new Intent(getApplicationContext(),AddFecility.class);
                n.putExtra("tid",getIntent().getStringExtra("tid"));
                startActivity(n);
            }
        });
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        ip=sp.getString("ip","");
        url ="http://"+ip+":5000/viewfacility";




        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(ViewFacility.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    facility=new ArrayList<>();
                    descrip=new ArrayList<>();
                    img=new ArrayList<>();
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        facility.add(jo.getString("facility"));
                        descrip.add(jo.getString("description"));
                        img.add(jo.getString("image"));
                    }

                    lv2.setAdapter(new CustomImg(ViewFacility.this,facility,img,descrip));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("tid", getIntent().getStringExtra("tid"));

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);




    }
}