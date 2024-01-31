package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Turfviewowner extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7;
    ImageView img;
    Button b1;
    String tid;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turfviewowner);

        e1=(EditText)findViewById(R.id.tname);
        e2=(EditText)findViewById(R.id.place);
        e3=(EditText)findViewById(R.id.landmark);
        e4=(EditText)findViewById(R.id.Lattitude);
        e5=(EditText)findViewById(R.id.Phone_no);
        e6=(EditText)findViewById(R.id.Email);
        e7=(EditText)findViewById(R.id.Longitude);
        img=(ImageView)findViewById(R.id.imageView2);
        b1=(Button)findViewById(R.id.update);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        tid=getIntent().getStringExtra("tid");

        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        e1.setText(getIntent().getStringExtra("tname"));
        e2.setText(getIntent().getStringExtra("place"));
        e3.setText(getIntent().getStringExtra("landmark"));
        e4.setText(getIntent().getStringExtra("latti"));
        e7.setText(getIntent().getStringExtra("longi"));
        e6.setText(getIntent().getStringExtra("phno"));
        e5.setText(getIntent().getStringExtra("mail_id"));




        try {

            //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");

            java.net.URL thumb_u;
            thumb_u = new java.net.URL("http://"+sp.getString("ip","")+":5000/static/turfimg/"+getIntent().getStringExtra("image"));
            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            img.setImageDrawable(thumb_d);

        }
        catch (Exception e)
        {
            Log.d("errsssssssssssss",""+e);
        }






        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name=e1.getText().toString();
                final String place=e2.getText().toString();
                final String landmark=e3.getText().toString();
                final String Lattitude=e4.getText().toString();
                final String Longitude=e7.getText().toString();

                final String Contact_no=e6.getText().toString();
                final String Email=e5.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(Turfviewowner.this);
                String url ="http://"+sp.getString("ip","")+":5000/updateturfinfo";

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
                                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();

                                startActivity(new Intent(getApplicationContext(),Ownerviewturfdetail.class));

                            }
                            else
                            {

                                Toast.makeText(getApplicationContext(),"Updation Failed",Toast.LENGTH_LONG).show();

                                startActivity(new Intent(getApplicationContext(),UserHome.class));

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
                        params.put("uid",sp.getString("tid",""));
                        params.put("name", name);
                        params.put("place", place);
                        params.put("landmark", landmark);
                        params.put("latti", Lattitude);
                        params.put("longi",Longitude);
                        params.put("phno", Contact_no);
                        params.put("email", Email);

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });

    }
}