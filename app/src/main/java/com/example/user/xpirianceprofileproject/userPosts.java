package com.example.user.xpirianceprofileproject;

public class userPosts {
    private  String Time;
    private String Date;
    private String Post;

    public userPosts(String time, String date, String post) {
        this.Time = time;
        this.Date = date;
        this.Post = post;
    }

    public String getTime() {
        return Time;
    }


    public String getDate() {
        return Date;
    }

    public String getPost() {
        return Post;
    }
}
