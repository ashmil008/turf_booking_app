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
import android.widget.ListView;
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

public class SlotsView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<String> sloats = new ArrayList<>();
    String url1,ip,tids="";
    SharedPreferences sp;
    ListView lv;
    Button b1;
    Spinner sp1;
    ArrayList<String>tname,tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slots_view);
        b1=(Button)findViewById(R.id.button3);

        sloats.add("01 to 02");
        sloats.add("02 to 03");
        sloats.add("03 to 04");
        sloats.add("04 to 05");
        sloats.add("05 to 06");
        sloats.add("06 to 07");
        sloats.add("07 to 08");
        sloats.add("08 to 09");
        sloats.add("09 to 10");
        sloats.add("10 to 11");
        sloats.add("11 to 12");
        sloats.add("12 to 13");
        sloats.add("13 to 14");
        sloats.add("14 to 15");
        sloats.add("15 to 16");
        sloats.add("16 to 17");
        sloats.add("17 to 18");
        sloats.add("18 to 19");
        sloats.add("19 to 20");
        sloats.add("20 to 21");
        sloats.add("21 to 22");
        sloats.add("22 to 23");
        sloats.add("23 to 00");
        sloats.add("00 to 01");

        sp1=(Spinner)findViewById(R.id.spinner2);
        sp1.setOnItemSelectedListener(SlotsView.this);
        lv=(ListView) findViewById(R.id.lv1);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=getIntent().getStringExtra("ty");
                if(s.equalsIgnoreCase("s")) {
                    Intent n = new Intent(getApplicationContext(), SlotUpdate.class);
                    n.putExtra("tid", tids);
                    startActivity(n);
                }
                else
                {
                    Intent n = new Intent(getApplicationContext(), IncomeView.class);
                    n.putExtra("tid", tids);
                    startActivity(n);
                }

            }
        });


        ip=sp.getString("ip","");


        url1 ="http://"+ip+":5000/turfunique";


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(SlotsView.this);


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
                    ArrayAdapter<String> ad=new ArrayAdapter<>(SlotsView.this,android.R.layout.simple_spinner_item,tname);
                    sp1.setAdapter(ad);


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



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tids=tid.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


        ArrayAdapter<String> ad=new ArrayAdapter<>(SlotsView.this,android.R.layout.simple_list_item_1,sloats);
        lv.setAdapter(ad);

    }
}