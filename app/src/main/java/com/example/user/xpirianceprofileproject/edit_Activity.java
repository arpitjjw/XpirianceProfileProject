package com.example.user.xpirianceprofileproject;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class edit_Activity extends AppCompatActivity implements View.OnClickListener {
    private final int PICK_IMAGE_REQUEST = 1;
    EditText editname;
    EditText editabout;
    Button nameedit;
    Button aboutedit;
    Button ChangePic;
    Button Uploadpic;
    private Bitmap bitmap;
    String imageBase64;
    private ProgressDialog pd;

    private static final String URL_EDIT_NAME = "http://192.168.43.174/editname.php";
    private static final String URL_EDIT_ABOUT = "http://192.168.43.174/editabout.php";
    private static final String URL_PROFILE_PIC = "http://192.168.43.174/profilepic.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        editname=(EditText)findViewById(R.id.EditUsername);

        editabout=(EditText)findViewById(R.id.EditAbout);
        pd = new ProgressDialog(this);
        nameedit=(Button)findViewById(R.id.SaveName);
        aboutedit=(Button)findViewById(R.id.SaveAbout);
        ChangePic=(Button)findViewById(R.id.ProfilePic);
        Uploadpic=(Button)findViewById(R.id.Uploadpic);
        Uploadpic.setOnClickListener(this);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");

        nameedit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editProfilename();

            }
        });
        aboutedit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               editProfileabout();
            }
        });

        ChangePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this,profilePage.class);
                this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void editProfilename(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_EDIT_NAME,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", editname.getText().toString());


                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
    private void editProfileabout(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_EDIT_ABOUT,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("about", editabout.getText().toString());

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
    private void uploadPic(){
        pd.setMessage("Saving Image");
        pd.show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_PROFILE_PIC,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        pd.setMessage("Saved Successfully");
                        pd.hide();
                        pd.dismiss();
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.setMessage("Error");
                        pd.hide();
                        pd.dismiss();
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("image", imageBase64);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.Uploadpic:

                    uploadPic();

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                createImageFromBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";
        try {
            ByteArrayOutputStream bytesA = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytesA);
            byte [] imgbytes =bytesA.toByteArray();
            imageBase64= Base64.encodeToString(imgbytes,Base64.DEFAULT);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytesA.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,profilePage.class);
        this.startActivity(intent);

    }

}
