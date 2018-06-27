package com.example.user.xpirianceprofileproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;

public class MainActivity extends AppCompatActivity {
    ImageView ProfileImage;
    TextView About;
    TextView UserName;
    TextView Questions;
    TextView Answers;
    TextView Followers;
    userStats UserStats;
    private static final String URL_STATS = "http://172.23.148.194/api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProfileImage=(ImageView)findViewById(R.id.ProfileImage);
        About=(TextView)findViewById(R.id.AboutUser);
        UserName=(TextView)findViewById(R.id.UserName);
        Questions=(TextView)findViewById(R.id.Questions);
        Answers=(TextView)findViewById(R.id.Answers);
        Followers=(TextView)findViewById(R.id.Followers);
        loadUserdata();
    }
    public void loadUserdata(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_STATS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            //only one object in sample
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject userstats = array.getJSONObject(i);
                                UserStats=new userStats(userstats.getString("imageurl"),userstats.getString("name"),userstats.getString("about"),userstats.getInt("questions"),userstats.getInt("answers"),userstats.getInt("followers"));
                            }
                            Glide.with(getApplicationContext()).load(UserStats.getImageUrl())
                                    .into(ProfileImage);
                            About.setText(String.valueOf(UserStats.getAbout()));
                            UserName.setText(String.valueOf(UserStats.getUserName()));
                            Questions.setText(String.valueOf(UserStats.getQuestions()));
                            Answers.setText(String.valueOf(UserStats.getAnswers()));
                            Followers.setText(String.valueOf(UserStats.getFollowers()));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
