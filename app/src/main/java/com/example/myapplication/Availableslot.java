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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Availableslot extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button b1,b6;
    ListView lv1;
    SharedPreferences sp;
    ArrayList<String>slot,status;
  EditText d1;
    String ip,url,s="",date,amount,bid="";
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availableslot);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lv1=(ListView)findViewById(R.id.lv2);
        b1=(Button)findViewById(R.id.button2);
        b6=(Button)findViewById(R.id.button6);
        d1 =(EditText) findViewById(R.id.editTextDate3);
        lv1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        amount=getIntent().getStringExtra("amount");
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                date=d1.getText().toString();

                if (date.equalsIgnoreCase(""))
                {
                    d1.setError("Required");
                    d1.requestFocus();
                }
                else {

                    RequestQueue queue = Volley.newRequestQueue(Availableslot.this);
                    String url = "http://" + sp.getString("ip", "") + ":5000/userbooking";

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
                                    bid = json.getString("bid");
                                    SharedPreferences.Editor ed = sp.edit();
                                    ed.putString("bid", bid);
                                    ed.commit();


                                    Intent i = new Intent(getApplicationContext(), PaymentActivity.class);

                                    i.putExtra("p", amount);

                                    startActivity(i);

                                } else { Toast.makeText(getApplicationContext(),res+" "+bid,Toast.LENGTH_LONG).show();


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
                            params.put("slot", s);
                            params.put("date", date);
                            params.put("uid", sp.getString("lid", ""));
                            params.put("tid", sp.getString("turfid", ""));

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }

//
//                Intent i=new Intent(getApplicationContext(),Payment.class);
//                i.putExtra("count",count);
//                i.putExtra("s",s);
//                i.putExtra("date",date);
//                i.putExtra("amount",amount);
//                i.putExtra("bid",bid);
//                startActivity(i);



            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date=d1.getText().toString();

                ip=sp.getString("ip","");
                url ="http://"+ip+":5000/viewmyslotavail";





                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Availableslot.this);


                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);


                            slot=new ArrayList<>();
                            status=new ArrayList<>();
                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
                                slot.add(jo.getString("sloat"));

                            }
                            ArrayAdapter<String> ad=new ArrayAdapter<String>(Availableslot.this,android.R.layout.simple_list_item_multiple_choice,slot);
                            lv1.setAdapter(ad);
                            lv1.setOnItemClickListener(Availableslot.this);




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

                        params.put("date",date);
                        params.put("tid",sp.getString("turfid",""));

                        return params;
                    }
                };



                // Add the request to the RequestQueue.
                queue.add(stringRequest);







            }
        });







    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         String cs=slot.get(position);
         if (s.contains(cs))
         {        count=count-1;
         s=s.replace(cs+"@","");
         }
         else {
             s = s + slot.get(position) + "@";
             count=count+1;


         }
         Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

    }
}