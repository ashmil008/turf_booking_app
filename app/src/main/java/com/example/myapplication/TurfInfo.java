package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.FileUpload;
import com.example.myapplication.FileUtils;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class TurfInfo extends AppCompatActivity {

    TextView e1,e2,e3,e4,e5,e6,e7,e8,e9;
    Button b1,b2,b3;
    SharedPreferences sp;
    ImageView img,img2;
    RatingBar rb;
    String rtb="",fd="";
    private static final int FILE_SELECT_CODE = 0;
    int res;
    String fileName="",path="",amount;
    String ip="",url="",uname="",pass="",tname,place,landmark,Contact_no,Email,longitude,lattitude,pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turf_info);



        img=(ImageView)findViewById(R.id.imageView2);
        img2=(ImageView)findViewById(R.id.imageView4);
        e1=(TextView) findViewById(R.id.Name);
        e2=(TextView) findViewById(R.id.place);
        e3=(TextView) findViewById(R.id.landmark);
        e4=(TextView) findViewById(R.id.Lattlong);
        e5=(TextView) findViewById(R.id.Phone_no);
        e6=(TextView) findViewById(R.id.Email);
//        e7=(TextView) findViewById(R.id.Facility);
//        e8=(TextView) findViewById(R.id.Description);
        e9=(TextView) findViewById(R.id.Feedback);
        rb=findViewById(R.id.ratingBar);

//        e8=(EditText)findViewById(R.id.uname);
//        e9=(EditText)findViewById(R.id.pwd);
        b1=(Button)findViewById(R.id.slotavail);
        b2=(Button)findViewById(R.id.Submit);
        b3=(Button)findViewById(R.id.button9);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Availableslot.class);
                i.putExtra("amount",amount);
                startActivity(i);
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ViewFecility.class);
                i.putExtra("tid",getIntent().getStringExtra("tid"));
                startActivity(i);
            }
        });



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fd=e9.getText().toString();

//                Toast.makeText(TurfInfo.this, ""+ sp.getString("tid",""), Toast.LENGTH_SHORT).show();
                rtb=rb.getRating()+"";

                if (fd.equalsIgnoreCase(""))
                {
                    e9.setError("Please enter your feedback");
                    e9.requestFocus();
                }
                else {

                    RequestQueue queue = Volley.newRequestQueue(TurfInfo.this);
                    String url = "http://" + sp.getString("ip", "") + ":5000/feedbackbyuser";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("success")) {
                                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                                    rb.setRating(0.0f);
                                    e9.setText("");

//                                startActivity(new Intent(getApplicationContext(),TurfInfo.class));

                                } else {


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Errorrr" + e, Toast.LENGTH_LONG).show();

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
                            params.put("feed", fd);
                            params.put("rating", rtb);
                            params.put("tid", sp.getString("turfid", ""));
                            params.put("uid", sp.getString("lid", ""));

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }

            }
        });




        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        e1.setText(getIntent().getStringExtra("tname"));
        e2.setText(getIntent().getStringExtra("place"));
        e3.setText(getIntent().getStringExtra("landmark"));
        e4.setText(getIntent().getStringExtra("longi")+""+(getIntent().getStringExtra("latti")));
        e5.setText(getIntent().getStringExtra("phno"));
        e6.setText(getIntent().getStringExtra("mail_id"));
//        e7.setText(getIntent().getStringExtra("facility"));
//        e8.setText(getIntent().getStringExtra("description"));

        amount = getIntent().getStringExtra("amount");

        try {

            //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");

            java.net.URL thumb_u;
            thumb_u = new java.net.URL("http://"+sp.getString("ip","")+":5000/static/turfimg/"+getIntent().getStringExtra("image"));
            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            img2.setImageDrawable(thumb_d);

        }
        catch (Exception e)
        {
            Log.d("errsssssssssssss",""+e);
        }

//        try {
//
//            //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");
//
//            java.net.URL thumb_u;
//            thumb_u = new java.net.URL("http://"+sp.getString("ip","")+":5000/static/fecility/"+getIntent().getStringExtra("fimage"));
//            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
//            img.setImageDrawable(thumb_d);
//
//        }
//        catch (Exception e)
//        {
//            Log.d("errsssssssssssss",""+e);
//        }
    }




}


