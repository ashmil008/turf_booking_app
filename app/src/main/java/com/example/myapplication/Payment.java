package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Payment extends AppCompatActivity {

    TextView e1,e2,e3;
    Button b1;
    String date,amount,bid;
    int nsloat;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(TextView)findViewById(R.id.sselect);
        e2=(TextView)findViewById(R.id.bdate);
        e3=(TextView)findViewById(R.id.amt);
        b1=(Button) findViewById(R.id.button4);
        date=getIntent().getStringExtra("date");
        nsloat=getIntent().getIntExtra("count",1);
        e2.setText(date);
        e1.setText(nsloat+"");
        bid=sp.getString("bid","");
        amount=getIntent().getStringExtra("amount");

        Toast.makeText(getApplicationContext(),amount+"========"+bid,Toast.LENGTH_LONG).show();
        float am=nsloat*Float.parseFloat(amount);
        e3.setText(am+"");
        amount=e3.getText().toString();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k=new Intent(getApplicationContext(),finalpay.class);
                k.putExtra("bid",bid);
                k.putExtra("amt",amount);
                startActivity(k);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}