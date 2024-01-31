package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Addnewturf extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    Button b1,b2;
    SharedPreferences sp;
    private static final int FILE_SELECT_CODE = 0;
    int res;
    String fileName="",path="";
    String ip="",url="",uname="",pass="",tname,place,landmark,Contact_no,Email,longitude,lattitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewturf);
        e1=(EditText)findViewById(R.id.tname);
        e2=(EditText)findViewById(R.id.place);
        e3=(EditText)findViewById(R.id.land_mark);
        e4=(EditText)findViewById(R.id.Contact_no);
        e5=(EditText)findViewById(R.id.Email_id);
        e6=(EditText)findViewById(R.id.latt);
        e7=(EditText)findViewById(R.id.longitude);
        e10=(EditText)findViewById(R.id.Imgname);

        b1=(Button)findViewById(R.id.register);
        b2=(Button)findViewById(R.id.button);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //getting all types of files
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(Intent.createChooser(intent, ""), FILE_SELECT_CODE);
                } catch (android.content.ActivityNotFoundException ex) {

                    Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //back to login page
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tname=e1.getText().toString();
                place=e2.getText().toString();
                landmark=e3.getText().toString();
                Contact_no=e4.getText().toString();
                Email=e5.getText().toString();
                lattitude=e6.getText().toString();
                longitude=e7.getText().toString();

                if(tname.equalsIgnoreCase(""))
                {
                    e1.setError("Please enter turf name");
                    e1.requestFocus();
                }
                else if (place.equalsIgnoreCase(""))
                {
                    e2.setError("Please enter the place");
                    e2.requestFocus();
                }
                else if (landmark.equalsIgnoreCase(""))
                {
                    e3.setError("Required");
                    e3.requestFocus();
                }
                else if (Contact_no.equalsIgnoreCase(""))
                {
                    e4.setError("Please enter your contact details");
                    e4.requestFocus();
                }
                else if (Contact_no.length()!=10)
                {
                    e4.setError("Phone number must be 10 numbers");
                    e4.requestFocus();
                }
                else if (Email.equalsIgnoreCase(""))
                {
                    e5.setError("Place enter email");
                    e5.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
                {
                    e5.setError("Please enter a valid email address");
                    e5.requestFocus();
                }
                else if (lattitude.equalsIgnoreCase(""))
                {
                    e6.setError("Required");
                    e6.requestFocus();
                }
                else if (longitude.equalsIgnoreCase(""))
                {
                    e7.setError("Required");
                    e7.requestFocus();
                }
                else {
                    res = uploadFile(path);
                }

                if (res == 1) {
                    Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG).show();
                    Intent ik = new Intent(getApplicationContext(), OwnerHome.class);
                    startActivity(ik);

                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("File Uri", "File Uri: " + uri.toString());
                    // Get the path
                    try {
                        path = FileUtils.getPath(this, uri);
                        //e4.setText(path1);
                        Log.d("path", path);
                        File fil = new File(path);
                        int fln = (int) fil.length();
                        e10.setText(path);

                        File file = new File(path);

                        byte[] byteArray = null;
                        try {
                            InputStream inputStream = new FileInputStream(fil);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte[] b = new byte[fln];
                            int bytesRead = 0;

                            while ((bytesRead = inputStream.read(b)) != -1) {
                                bos.write(b, 0, bytesRead);
                            }

                            byteArray = bos.toByteArray();
                            inputStream.close();
                            Bitmap bmp= BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            if(bmp!=null){
//
//
                                // i1.setVisibility(View.VISIBLE);
                                //i1.setImageBitmap(bmp);
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    } catch (URISyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Please select suitable file", Toast.LENGTH_LONG).show();
                }
                break;


        }


    }

    public int uploadFile(String sourceFileUri) {
        try {
            fileName = sourceFileUri;
            String upLoadServerUri = "http://" + sp.getString("ip", "") + ":5000/addnewturf";
            Toast.makeText(getApplicationContext(), upLoadServerUri, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
            FileUpload fp = new FileUpload(fileName);
            Map mp = new HashMap<String, String>();
            mp.put("name",tname);
            mp.put("place",place);
            mp.put("landmark",landmark);
            mp.put("long",longitude);
            mp.put("lat",lattitude);
            mp.put("phone",Contact_no);
            mp.put("email",Email);
            mp.put("id",sp.getString("lid",""));


            fp.multipartRequest(upLoadServerUri, mp, fileName, "files", "application/octet-stream");
            return 1;

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),"error"+e,Toast.LENGTH_LONG).show();
            return 0;
        }
    }


}


