package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
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
public class TurfView extends AppCompatActivity  implements AdapterView.OnItemClickListener  {
    GridView gv;
    SharedPreferences sp;
    String ip,url,ttid;

    ImageView img;
    ArrayList<String> tid,tname,place,rating,landmark,Contact_no,Email,longitude,lattitude,fac,des,imgs,fimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turf_view);
        gv=(GridView) findViewById(R.id.grid1);
        img=(ImageView)findViewById(R.id.imageView2);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ip=sp.getString("ip","");

        url ="http://"+ip+":5000/turfviewuser";


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(TurfView.this);


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);



                    tid=new ArrayList<>();
                    tname=new ArrayList<>();
                    place=new ArrayList<>();
                    rating=new ArrayList<>();
                    landmark=new ArrayList<>();
                    Contact_no=new ArrayList<>();
                    Email=new ArrayList<>();
                    longitude=new ArrayList<>();
                    lattitude=new ArrayList<>();
                    fac=new ArrayList<>();
                    des=new ArrayList<>();
                    imgs=new ArrayList<>();
                    fimg=new ArrayList<>();




                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        //  time.add(jo.getString("time"));
                        tid.add(jo.getString("lid"));
                        tname.add(jo.getString("name"));
                        rating.add(jo.getString("rating"));
                        place.add(jo.getString("place"));
                        landmark.add(jo.getString("landmark"));
                        Contact_no.add(jo.getString("phno"));
                        Email.add(jo.getString("mail_id"));
                        longitude.add(jo.getString("longi"));
                        lattitude.add(jo.getString("latti"));
                        fac.add(jo.getString("facility"));
                        des.add(jo.getString("description"));
                        imgs.add(jo.getString("image"));
                        fimg.add(jo.getString("fimage"));


                    }
                    gv.setAdapter(new custom1(getApplicationContext(),tname,place,rating));
                    gv.setOnItemClickListener(TurfView.this);



                    //    gv.setOnItemClickListener(TurfView.this);
//                    ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
//                    lv.setAdapter(ad);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error"+e,Toast.LENGTH_LONG).show();
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
                params.put("lid", sp.getString("lid","0"));

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        SharedPreferences.Editor ed=sp.edit();
        ed.putString("tid",tid.get(position));
        ed.commit();

        Toast.makeText(this, ""+tid.get(position), Toast.LENGTH_SHORT).show();

        Intent ik=new Intent(getApplicationContext(),TurfInfo.class);
        ik.putExtra("tname",tname.get(position));
        ik.putExtra("place",place.get(position));
        ik.putExtra("landmark",landmark.get(position));
        ik.putExtra("phno",Contact_no.get(position));
        ik.putExtra("mail_id",Email.get(position));
        ik.putExtra("latti",longitude.get(position));
        ik.putExtra("longi",lattitude.get(position));
        ik.putExtra("image",imgs.get(position));
        ik.putExtra("fimage",fimg.get(position));
        ik.putExtra("facility",fac.get(position));
        ik.putExtra("description",des.get(position));



        startActivity(ik);

    }
}