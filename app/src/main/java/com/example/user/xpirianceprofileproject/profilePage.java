package com.example.user.xpirianceprofileproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class profilePage extends AppCompatActivity {
    ImageView ProfileImage;
    TextView About;
    TextView UserName;
    TextView Questions;
    TextView Answers;
    TextView Followers;
    userStats UserStats;
    Adapter useradapter;
    Adapteranswers useransweradapter;


    private Toolbar toolbar;
    private static final String URL_STATS = "http://192.168.43.174/api.php";
    private static final String URL_POSTS = "http://192.168.43.174/userquestions.php";
    private static final String URL_ANSWERS = "http://192.168.43.174/userquestionanswers.php";

    List<userPosts> userpostlist;
    List<useranswers> useranswersList;
    RecyclerView recyclerView;
    RecyclerView recyclerViewanswers;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        ProfileImage=(ImageView)findViewById(R.id.ProfileImage);

        About=(TextView)findViewById(R.id.AboutUser);

        UserName=(TextView)findViewById(R.id.UserName);
        Questions=(TextView)findViewById(R.id.questions);

        Answers=(TextView)findViewById(R.id.answers);
        Followers=(TextView)findViewById(R.id.followers);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerViewanswers=findViewById(R.id.recycleranswers);
        recyclerViewanswers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewanswers.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.setHasFixedSize(true);

        recyclerView.setNestedScrollingEnabled(false);
        userpostlist=new ArrayList<>();
        useranswersList=new ArrayList<>();
        loadUserdata();
        loadUserPosts();
        loadUserAnswers();


        fetchImage();
        Answers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                recyclerViewanswers.setVisibility(View.VISIBLE);

            }
        });
        Questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewanswers.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);


            }
        });

    }
    private void loadUserdata(){

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
    private void loadUserPosts(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_POSTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject userposts = array.getJSONObject(i);

                                //adding the product to product list
                                userpostlist.add(new userPosts(
                                        userposts.getString("questions")
                                ));
                            }

                            useradapter = new Adapter(profilePage.this, userpostlist);
                            recyclerView.setAdapter(useradapter);
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
    public void loadUserAnswers(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ANSWERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject userposts = array.getJSONObject(i);

                                //adding the product to product list
                                useranswersList.add(new useranswers(
                                        userposts.getString("questions"),userposts.getString("answers")
                                ));
                            }

                            useransweradapter = new Adapteranswers(profilePage.this, useranswersList);
                            recyclerViewanswers.setAdapter(useransweradapter);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit) {

            Intent intent = new Intent(this,edit_Activity.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.settings) {
            Toast.makeText(this, "Android Settings Menu is Clicked", Toast.LENGTH_LONG).show();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public void fetchImage(){
        try {
            bitmap = BitmapFactory.decodeStream(this.openFileInput( "myImage"));
            ProfileImage.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
