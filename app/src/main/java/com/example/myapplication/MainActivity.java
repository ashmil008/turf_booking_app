package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.content.DialogInterface;
import android.content.Intent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button b1,b2,b3;
    EditText editText,editText2;
    SharedPreferences sp;
    String ip="",url="",uname="",pass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.uname);
        editText2 = (EditText) findViewById(R.id.pwd);
        b1 = (Button) findViewById(R.id.login);
        b2 = (Button) findViewById(R.id.SignupUser);
        b3 = (Button) findViewById(R.id.SignupTurf);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        //login button

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String un = editText.getText().toString();
                final String pwd = editText2.getText().toString();
                if(un.equalsIgnoreCase(""))
                {
                    editText.setError("Enter Username ");
                    editText.requestFocus();
                }
                else if(pwd.equalsIgnoreCase("")){
                    editText2.setError("Enter Password");
                    editText2.requestFocus();
                }else{
                    final ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
                    pDialog.setMessage("Loading...");
                     pDialog.show();


                // Instantiate the RequestQueue.
                      RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                      String url = "http://" + sp.getString("ip", "") + ":5000/login";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");
                            pDialog.hide();
                            if (res.equalsIgnoreCase("fail")) {
                                Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                            } else {
                                SharedPreferences.Editor ed = sp.edit();
                                ed.putString("lid", res);
                                ed.commit();
                                String type = json.getString("type");
//                                Toast.makeText(getApplicationContext(), "Type is", Toast.LENGTH_SHORT).show();
                                if (type.equals("turf")) {
                                    startActivity(new Intent(getApplicationContext(), OwnerHome.class));
                                } else if (type.equals("user")) {
                                    startService(new Intent(getApplicationContext(), LocationService.class));
                                    startActivity(new Intent(getApplicationContext(), UserHome.class));



                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Er" + e, Toast.LENGTH_LONG).show();

                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        pDialog.hide();
                        Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("uname", un);
                        params.put("pass", pwd);

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
            }

                              });
        //TurfSignup

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), UserSignup.class);
                startActivity(i);
            }
        });

        //UserSignup

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), TurfSignup.class);
                startActivity(j);
            }
        });
    }
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);


        public void onBackPressed () {
            // TODO Auto-generated method stub
            AlertDialog.Builder ald = new AlertDialog.Builder(MainActivity.this);
            ald.setTitle("Do you want to Exit")
                    .setPositiveButton(" YES ", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent in = new Intent(Intent.ACTION_MAIN);
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            in.addCategory(Intent.CATEGORY_HOME);
                            startActivity(in);
                        }
                    })
                    .setNegativeButton(" NO ", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

            AlertDialog al = ald.create();
            al.show();





    }

}