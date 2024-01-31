package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class finalpay extends AppCompatActivity {

    EditText e1,e2,e3,e4;
    TextView tv;
    SharedPreferences sp;
    Button b1;
    String amt,bid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalpay);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=findViewById(R.id.bname);
        e2=findViewById(R.id.accno);
        tv=findViewById(R.id.amt);
        e3=findViewById(R.id.ifsc);
        e4=findViewById(R.id.Con_no);
        b1=findViewById(R.id.button5);
        amt=getIntent().getStringExtra("amt");
        bid=getIntent().getStringExtra("bid");
        tv.setText(amt+"");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String bname = e1.getText().toString();
                final String accno = e2.getText().toString();
                final String ifsc = e3.getText().toString();
                final String phno = e4.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(finalpay.this);
                String url ="http://"+sp.getString("ip","")+":5000/finalpay";

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
                                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();

                                startActivity(new Intent(getApplicationContext(),UserHome.class));

                            }
                            else
                            {

                                Toast.makeText(finalpay.this, "invalid", Toast.LENGTH_SHORT).show();

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
                        params.put("bname", bname);
                        params.put("accno",accno );
                        params.put("ifsc",ifsc );
                        params.put("phno",phno );
                        params.put("bid",sp.getString("bid","") );
                        params.put("amt",amt );

                        params.put("uid", sp.getString("lid",""));

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);



            }
        });





    }
}