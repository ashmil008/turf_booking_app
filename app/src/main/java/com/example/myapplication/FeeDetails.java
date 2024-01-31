package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class FeeDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText e1;
    Button b1;
    String amt,url1,ip,tids;
    SharedPreferences sp;
    Spinner ab;
    ArrayList<String>tname,tid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_details);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.mfees);
        ab=(Spinner) findViewById(R.id.spinner2);

        b1=(Button)findViewById(R.id.b1);

        ab.setOnItemSelectedListener(FeeDetails.this);
        ip=sp.getString("ip","");


        url1 ="http://"+ip+":5000/turfunique";


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(FeeDetails.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);



                    tid=new ArrayList<>();
                    tname=new ArrayList<>();





                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        //  time.add(jo.getString("time"));
                        tid.add(jo.getString("tid"));
                        tname.add(jo.getString("name"));



                    }
//                    gv.setAdapter(new custom1(getApplicationContext(),tname,place,rating));
//                    gv.setOnItemClickListener(TurfView.this);



                    //    gv.setOnItemClickListener(TurfView.this);
                    ArrayAdapter<String> ad=new ArrayAdapter<>(FeeDetails.this,android.R.layout.simple_spinner_item,tname);
                    ab.setAdapter(ad);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_LONG).show();
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
                   params.put("lid", sp.getString("lid","0"));

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);











        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amt=e1.getText().toString();

                if (amt.equalsIgnoreCase(""))
                {
                    e1.setError("Required");
                    e1.requestFocus();
                }
                else
                {

                    RequestQueue queue = Volley.newRequestQueue(FeeDetails.this);
                    String url = "http://" + sp.getString("ip", "") + ":5000/feeinsert";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");
                                Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                                if (res.equalsIgnoreCase("success")) {
                                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();

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
                            params.put("amount", amt);
                            params.put("tid", tids);


                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     tids=tid.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}