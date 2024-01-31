package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Ipset extends AppCompatActivity {

    EditText e1;
    Button ipset;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipset);
        e1=(EditText) findViewById(R.id.ip);
        ipset=(Button) findViewById(R.id.setip);

        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //For setting Ip address
        ipset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip=e1.getText().toString();

                if (ip.equalsIgnoreCase(""))
                {
                    e1.setError("Please enter your ip");
                    e1.requestFocus();
                }

                else {

                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("ip", ip);
                    ed.commit();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }

            }
        });


    }
}