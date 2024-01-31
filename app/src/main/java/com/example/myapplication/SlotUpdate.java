package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class SlotUpdate extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ArrayList<String> sloatadd = new ArrayList<>();
    Spinner sp;
    Button b1;
    SharedPreferences sh;
    ListView lv;
    String sid,date;
    EditText ed;
    String tid;
    ArrayList<String> slots,status,slotid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_update);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed=findViewById(R.id.editTextTextPersonName);
        tid=getIntent().getStringExtra("tid");
        sloatadd.add("07 to 08");
        sloatadd.add("08 to 09");
        sloatadd.add("09 to 10");
        sloatadd.add("10 to 11");
        sloatadd.add("11 to 12");
        sloatadd.add("12 to 13");
        sloatadd.add("13 to 14");
        sloatadd.add("14 to 15");
        sloatadd.add("15 to 16");
        sloatadd.add("16 to 17");
        sloatadd.add("17 to 18");
        sloatadd.add("18 to 19");
        sloatadd.add("19 to 20");
        sloatadd.add("20 to 21");
        sloatadd.add("21 to 22");
        sloatadd.add("22 to 23");
        sloatadd.add("23 to 00");
        sloatadd.add("00 to 01");
        sloatadd.add("01 to 02");
        sloatadd.add("02 to 03");
        sloatadd.add("03 to 04");
        sloatadd.add("04 to 05");
        sloatadd.add("05 to 06");
        sloatadd.add("06 to 07");

        lv=(ListView)findViewById(R.id.list);

        sp=(Spinner) findViewById(R.id.Sloatadd);
        b1=(Button) findViewById(R.id.Addbtn);
        ArrayAdapter<String> ad=new ArrayAdapter<>(SlotUpdate.this,android.R.layout.simple_list_item_1,sloatadd);
        sp.setAdapter(ad);
        viewaddedslot();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String slots=sp.getSelectedItem().toString();
                date=ed.getText().toString();

                final ProgressDialog pDialog = new ProgressDialog(SlotUpdate.this);
                pDialog.setMessage("Loading...");
                pDialog.show();



                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(SlotUpdate.this);
                String url ="http://"+sh.getString("ip","")+":5000/insertslots";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {
                            JSONObject json=new JSONObject(response);
                            String res=json.getString("task");
                            pDialog.hide();
                            if(res.equalsIgnoreCase("ok"))
                            {

                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();

//                                startActivity(new Intent(getApplicationContext(),SlotUpdate.class));
                                Intent i=new Intent(getApplicationContext(),SlotUpdate.class);
                                i.putExtra("tid",tid);
                                startActivity(i);
                            }
                            else
                            {


                                Toast.makeText(getApplicationContext(),"Duplicate Entry",Toast.LENGTH_LONG).show();



                              //  startActivity(new Intent(getApplicationContext(),OwnerHome.class));



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Duplicate Entry",Toast.LENGTH_LONG).show();

                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        pDialog.hide();
                        Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("slot", slots);
                        params.put("tid", tid);
                        params.put("date",date);

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });


    }

    private void viewaddedslot() {

        String url1 ="http://"+sh.getString("ip","")+":5000/viewmyslot";


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(SlotUpdate.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("----------------", response);
                try {

                    JSONArray ar = new JSONArray(response);

                    slots = new ArrayList<>();
                    status = new ArrayList<>();
                    slotid = new ArrayList<>();

                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        slots.add(jo.getString("sloat"));
                        status.add(jo.getString("date")+" "+jo.getString("status"));
                        slotid.add(jo.getString("ssid"));

                    }

                       lv.setAdapter(new custom2(SlotUpdate.this,slots,status));
                    lv.setOnItemClickListener(SlotUpdate.this);


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

                    params.put("tid", tid);

                    return params;
                }
            };
            // Add the request to the RequestQueue.
                queue.add(stringRequest);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        sid=slotid.get(position);


        AlertDialog.Builder ald=new AlertDialog.Builder(SlotUpdate.this);
        ald.setTitle("Select option")
                .setPositiveButton(" Remove ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {






                        RequestQueue queue = Volley.newRequestQueue(SlotUpdate.this);
                        String url ="http://"+sh.getString("ip","")+":5000/removeslot";

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Log.d("+++++++++++++++++",response);
                                try {
                                    JSONObject json=new JSONObject(response);
                                    String res=json.getString("task");

                                    if(res.equalsIgnoreCase("success"))
                                    {
                                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();

                                        Intent i=new Intent(getApplicationContext(),SlotUpdate.class);
                                        i.putExtra("tid",tid);
                                        startActivity(i);

                                    }
                                    else
                                    {

                                        Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_LONG).show();


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(),"Er"+e,Toast.LENGTH_LONG).show();

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
                                params.put("ssid", sid);




                                return params;
                            }
                        };
                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);










                    }
                })
                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent i=new Intent(getApplicationContext(),SlotsView.class);
                        startActivity(i);

                    }
                });

        AlertDialog al=ald.create();
        al.show();



    }
}