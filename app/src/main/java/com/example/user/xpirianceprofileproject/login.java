package com.example.user.xpirianceprofileproject;

import android.content.Context;
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

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class login extends AppCompatActivity {
    EditText userName;
    EditText password;
    TextView signUp;
    Button login;
    Context mcontext;
    private static final String URL_LOGIN = "http://192.168.43.174/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName=(EditText)findViewById( R.id.username);
        password=(EditText)findViewById(R.id.password);
        signUp=(TextView)findViewById(R.id.register);
        login=(Button)findViewById(R.id.login);
        mcontext=this;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext,register.class);
                mcontext.startActivity(intent);
            }
        });
    }
    private void loginRequest(){


        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {



                        if(response.equals("Login")) {


                            startActivity(new Intent(getApplicationContext(), profilePage.class));
                        }


                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error;
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
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);



    }

}
