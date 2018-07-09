package com.example.user.xpirianceprofileproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText userName;
    EditText name;
    EditText password;
    Button signUp;
    private static final String URL_REGISTER= "http://192.168.43.174/register.php";
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName=(EditText)findViewById( R.id.username);
        name=(EditText)findViewById(R.id.name) ;
        password=(EditText)findViewById(R.id.password);
        pd = new ProgressDialog(this);
        signUp=(Button) findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupRequest();
            }
        });

    }
    private void signupRequest(){
        pd.setMessage("Signing Up . . .");
        pd.show();




        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        pd.hide();

                        if(response.equals("Successfully Registered")) {

                            startActivity(new Intent(getApplicationContext(), login.class));

                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.hide();
                        Log.d("Error.Response", error.toString());



                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("username", userName.getText().toString());
                params.put("password",  Encrypt.sha256(password.getText().toString()));
                params.put("name", name.getText().toString());

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
}
