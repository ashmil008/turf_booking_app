package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class UserSignup extends AppCompatActivity {

    EditText e1,e3,e4,e5,e6,e7,e8,e9;
    Button b3;
    SharedPreferences sp;

    String ip="",url="",uname="",pass="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        e1=(EditText)findViewById(R.id.fname);

        e3=(EditText)findViewById(R.id.last_name);
        e6=(EditText)findViewById(R.id.Con_no);
        e7=(EditText)findViewById(R.id.Email_id1);
        e8=(EditText)findViewById(R.id.uname1);
        e9=(EditText)findViewById(R.id.pwd1);
        b3=(Button)findViewById(R.id.register1);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        //back to login page
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           final String fname=e1.getText().toString();

                final   String lastname=e3.getText().toString();
                final String Con_no=e6.getText().toString();
                final String Email_id=e7.getText().toString();
                final  String uname1=e8.getText().toString();
                final String pwd1=e9.getText().toString();


                if (fname.equalsIgnoreCase(""))
                {
                    e1.setError("Please enter your first name");
                    e1.requestFocus();
                }
                else if (!fname.matches("^[a-z A-Z]*$"))
                {
                    e1.setError("Only characters are allowed");
                    e1.requestFocus();
                }

                else if (lastname.equalsIgnoreCase(""))
                {
                    e3.setError("Please enter your last name");
                    e3.requestFocus();
                }
                else if (!lastname.matches("^[a-z A-Z]*$"))
                {
                    e3.setError("Only characters are allowed");
                    e3.requestFocus();
                }
                else if (Con_no.equalsIgnoreCase(""))
                {
                    e4.setError("Please enter your contact number");
                    e4.requestFocus();
                }
                else if (Con_no.length()!=10)
                {
                    e6.setError("Phone number must be 10 numbers");
                    e6.requestFocus();
                }
                else if (Email_id.equalsIgnoreCase(""))
                {
                    e7.setError("Please enter your email address");
                    e7.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email_id).matches())
                {
                    e7.setError("Please enter a valid email address");
                    e7.requestFocus();
                }
                else if (uname1.equalsIgnoreCase(""))
                {
                    e8.setError("Please enter your username");
                    e8.requestFocus();
                }
                else if (pwd1.equalsIgnoreCase(""))
                {
                    e9.setError("Please enter your password");
                    e9.requestFocus();
                }
                else {

                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(UserSignup.this);
                    String url = "http://" + sp.getString("ip", "") + ":5000/signupuser";

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
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                } else {
                                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error" + e, Toast.LENGTH_LONG).show();

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
                            params.put("fname", fname);
                            params.put("lname", lastname);
                            params.put("un", uname1);
                            params.put("phone", Con_no);
                            params.put("pw", pwd1);
                            params.put("email", Email_id);

                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }

            }
        });
    }
}