package com.example.user.xpirianceprofileproject;

public class userStats {
    private String imageUrl;
    private String userName;
    private String about;
    private int questions;
    private int answers;
    private int followers;

    public userStats(String imageUrl, String userName,String about,int questions, int answers, int followers) {
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.about = about;
        this.questions = questions;
        this.answers= answers;
        this.followers= followers;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getAbout() {
        return about;
    }

    public int getQuestions() {
        return questions;
    }

    public int getAnswers() {
        return answers;
    }

    public int getFollowers() {
        return followers;
    }
}