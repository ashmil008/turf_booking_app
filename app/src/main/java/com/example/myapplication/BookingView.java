package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class BookingView extends AppCompatActivity  {
    ListView lv;
    SharedPreferences sp;
    String ip,url,ttid;

    ArrayList<String> date,time,bid,uname,nslot,status,bdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_view);
        lv=(ListView) findViewById(R.id.lv1);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ip=sp.getString("ip","");

        url ="http://"+ip+":5000/bookinghistory";


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(BookingView.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    date=new ArrayList<>();

                    bid=new ArrayList<>();
                    uname=new ArrayList<>();
                    nslot=new ArrayList<>();
                    status=new ArrayList<>();
                    bdate=new ArrayList<>();




                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        date.add(jo.getString("date"));
                      //  time.add(jo.getString("time"));
                        bid.add(jo.getString("bid"));
                        uname.add(jo.getString("fname")+jo.getString("lname"));
                        nslot.add(jo.getString("slot"));
                        status.add(jo.getString("status"));
                        bdate.add(jo.getString("bdate"));

                    }
                    lv.setAdapter(new CustomViewBooking(getApplicationContext(),uname,bdate,nslot));


//                    lv.setOnItemClickListener(BookingView.this);
//                    ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
//                    lv.setAdapter(ad);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("tid", sp.getString("lid","0"));

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);



    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        ttid=bid.get(position);
//        RequestQueue queue = Volley.newRequestQueue(BookingView.this);
//        String url ="http://"+sp.getString("ip","")+":5000/bookstatusupdate";
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                // Display the response string.
//                Log.d("+++++++++++++++++",response);
//                try {
//                    JSONObject json=new JSONObject(response);
//                    String res=json.getString("task");
//
//                    if(res.equalsIgnoreCase("success"))
//                    {
//                        Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
//
//                        Intent i1=new Intent(getApplicationContext(),BookingView.class);
//                        startActivity(i1);
//                    }
//                    else
//                    {
//
//
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),"Er"+e,Toast.LENGTH_LONG).show();
//
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("bid", ttid);
//
//
//                return params;
//            }
//        };
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
//
//
//
//    }
}