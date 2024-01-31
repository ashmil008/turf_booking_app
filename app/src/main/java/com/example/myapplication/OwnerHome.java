package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OwnerHome extends AppCompatActivity {

    Button b1,b3,b4,b5,b6,b7,b8,b9,b10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);
        b1=(Button) findViewById(R.id.Vuser);
//        b2=(Button) findViewById(R.id.Turfadd);
        b3=(Button) findViewById(R.id.Vturf);
        b4=(Button) findViewById(R.id.Vfeed);
        b5=(Button) findViewById(R.id.facility);
        b6=(Button) findViewById(R.id.Vbook);
        b7=(Button) findViewById(R.id.tslots);
        b8=(Button) findViewById(R.id.feedetail);
        b9=(Button) findViewById(R.id.Vincome);
        b10=(Button) findViewById(R.id.logout);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n=new Intent(getApplicationContext(),ProfileViewUser.class);
                startActivity(n);
            }
        });

//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(getApplicationContext(),Addnewturf.class);
//                startActivity(i);
//            }
//        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(getApplicationContext(),Ownerviewturfdetail.class);
                startActivity(j);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k=new Intent(getApplicationContext(),FeedbackView.class);
                startActivity(k);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n=new Intent(getApplicationContext(),Facilityunique.class);
                startActivity(n);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l=new Intent(getApplicationContext(),BookingView.class);
                startActivity(l);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(getApplicationContext(),SlotsView.class);
                m.putExtra("ty","s");
                startActivity(m);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o=new Intent(getApplicationContext(),FeeDetails.class);
                startActivity(o);
            }
        });


        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z=new Intent(getApplicationContext(),SlotsView.class);
                z.putExtra("ty","i");
                startActivity(z);
            }
        });

        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(x);
            }
        });
    }
}