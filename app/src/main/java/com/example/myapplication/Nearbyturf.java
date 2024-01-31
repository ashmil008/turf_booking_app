package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.Map;

public class Nearbyturf extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView lv;
    Button b1;
    TextView tv1;
    SharedPreferences sp;
    String ip,url,tid;
    ArrayList<String>name,place,img,phno,landmark,latti,longi,Contact_no,Email,turfid,amount,facility,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearbyturf);
        lv=(ListView)findViewById(R.id.list);
        b1=findViewById(R.id.button7);
        tv1=findViewById(R.id.textView46);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        ip=sp.getString("ip","");
        url ="http://"+ip+":5000/viewnearst_turf";

b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        tv1.setText("Other Turfs");
        url ="http://"+ip+":5000/viewallturf";

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(Nearbyturf.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    name=new ArrayList<>();
                    place=new ArrayList<>();
                    phno=new ArrayList<>();
                    landmark=new ArrayList<>();
                    latti=new ArrayList<>();
                    longi=new ArrayList<>();
                    Contact_no=new ArrayList<>();
                    Email=new ArrayList<>();
                    img=new ArrayList<>();
                    turfid=new ArrayList<>();
                    amount=new ArrayList<>();
//                    facility=new ArrayList<>();
//                    description=new ArrayList<>();
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        name.add(jo.getString("name"));
                        place.add(jo.getString("place"));
                        img.add(jo.getString("image"));
                        landmark.add(jo.getString("landmark"));
                        Contact_no.add(jo.getString("phno"));
                        Email.add(jo.getString("mail_id"));
                        longi.add(jo.getString("longi"));
                        latti.add(jo.getString("latti"));
                        turfid.add(jo.getString("tid"));
                        amount.add(jo.getString("amount"));
//                        facility.add(jo.getString("facility"));
//                        description.add(jo.getString("description"));

                        Toast.makeText(Nearbyturf.this, ""+place, Toast.LENGTH_SHORT).show();
                    }

                    lv.setAdapter(new customimgturf(Nearbyturf.this,name,place,img));
                    lv.setOnItemClickListener(Nearbyturf.this);

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

               // params.put("latti",LocationService.lati);
               // params.put("longi",LocationService.logi);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);







    }
});


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(Nearbyturf.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    name=new ArrayList<>();
                    place=new ArrayList<>();
                    phno=new ArrayList<>();
                    landmark=new ArrayList<>();
                    latti=new ArrayList<>();
                    longi=new ArrayList<>();
                    Contact_no=new ArrayList<>();
                    Email=new ArrayList<>();
                    img=new ArrayList<>();
                    turfid=new ArrayList<>();
                    amount=new ArrayList<>();
//                    facility=new ArrayList<>();
//                    description=new ArrayList<>();
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        name.add(jo.getString("name"));
                        place.add(jo.getString("place"));
                        img.add(jo.getString("image"));
                        landmark.add(jo.getString("landmark"));
                        Contact_no.add(jo.getString("phno"));
                        Email.add(jo.getString("mail_id"));
                        longi.add(jo.getString("longi"));
                        latti.add(jo.getString("latti"));
                        turfid.add(jo.getString("tid"));
                        amount.add(jo.getString("amount"));
//                        facility.add(jo.getString("facility"));
//                        description.add(jo.getString("description"));

                        Toast.makeText(Nearbyturf.this, ""+place, Toast.LENGTH_SHORT).show();
                    }

                    lv.setAdapter(new customimgturf(Nearbyturf.this,name,place,img));
                    lv.setOnItemClickListener(Nearbyturf.this);

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

                params.put("latti",LocationService.lati);
                params.put("longi",LocationService.logi);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);





    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        tid=turfid.get(position);




        Intent ik=new Intent(getApplicationContext(),TurfInfo.class);
        ik.putExtra("tname",name.get(position));
        ik.putExtra("place",place.get(position));
        ik.putExtra("landmark",landmark.get(position));
        ik.putExtra("phno",Contact_no.get(position));
        ik.putExtra("mail_id",Email.get(position));
        ik.putExtra("latti",longi.get(position));
        ik.putExtra("longi",latti.get(position));
        ik.putExtra("image",img.get(position));
        ik.putExtra("tid",tid);
//        ik.putExtra("facility",facility.get(position));
//        ik.putExtra("description",description.get(position));
        SharedPreferences.Editor ed=sp.edit();
        ed.putString("turfid",tid);
        ed.commit();
        ik.putExtra("amount",amount.get(position));



        startActivity(ik);


    }
}