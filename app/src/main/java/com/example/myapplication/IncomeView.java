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
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;

public class IncomeView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner mnth,yr;
    TextView tv;
    String ip="",url="",uname="",pass="";
    String month[]={"January","February","March","April","May","June","July","August","September","October","November","December"};
    String year[]={"2021","2022","2023","2024","2025"};
    SharedPreferences sp;
    String yrr,mm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_view);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mnth=(Spinner)findViewById(R.id.spinner);
        yr=(Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String>ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, month);
        mnth.setAdapter(ad);
        ArrayAdapter<String>ad1=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, year);
        yr.setAdapter(ad1);

        tv=(TextView)findViewById(R.id.textView21);

        yr.setOnItemSelectedListener(IncomeView.this);
        mnth.setOnItemSelectedListener(IncomeView.this);



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        yrr=yr.getSelectedItem().toString();
        mm = mnth.getSelectedItem().toString();
if(view==mnth)
{
    Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_LONG).show();

}

     //   Toast.makeText(getApplicationContext(),mm,Toast.LENGTH_LONG).show();


        RequestQueue queue = Volley.newRequestQueue(IncomeView.this);




        String url = "http://" + sp.getString("ip", "") + ":5000/incomeview";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
                    JSONObject json = new JSONObject(response);
                    String res = json.getString("task");
                    if(res.equals("none"))
                    {
                        tv.setText("None");

                        Toast.makeText(getApplicationContext(), "no amount", Toast.LENGTH_LONG).show();

                    }
                    else {
                        tv.setText(res);

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
                params.put("month", mm);
                params.put("year", yrr);
                params.put("tid", getIntent().getStringExtra("tid"));
                params.put("lid", sp.getString("lid",""));
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}