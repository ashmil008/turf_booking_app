package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

public class ViewFecility extends AppCompatActivity {
    SharedPreferences sp;
    String ip,url,tid;
    ArrayList<String> img,facility,description;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fecility);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        lv=findViewById(R.id.lv1);
        ip=sp.getString("ip","");
        url ="http://"+ip+":5000/viewfecility";

        tid=getIntent().getStringExtra("tid");

        RequestQueue queue = Volley.newRequestQueue(ViewFecility.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);


                    img=new ArrayList<>();
                    facility=new ArrayList<>();
                    description=new ArrayList<>();
//                    facility=new ArrayList<>();
//                    description=new ArrayList<>();
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        facility.add(jo.getString("facility"));
                        description.add(jo.getString("description"));
                        img.add(jo.getString("image"));

//                        facility.add(jo.getString("facility"));
//                        description.add(jo.getString("description"));


                    }

                    lv.setAdapter(new custon_vf(ViewFecility.this,facility,description,img));
//                    lv.setOnItemClickListener(Nearbyturf.this);

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

                 params.put("tid",tid);
                // params.put("longi",LocationService.logi);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);






    }
}