package com.example.demo.model;

public class Post {
    private int id;
    private String title;
    private String body;
    private String imageName;
    private String name;
    private String email;
    private int noLikes;
    private int noComments;
    private boolean likedPost;

    public Post(String title, String body, String imageName) {
        this.title = title;
        this.body = body;
        this.imageName = imageName;
    }

    public Post(){}

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setName(String surname) {
        this.name = surname ;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNoLikes() {
        return noLikes;
    }

    public void setNoLikes(int noLikes) {
        this.noLikes = noLikes;
    }

    public int getNoComments() {
        return noComments;
    }

    public void setNoComments(int noComments) {
        this.noComments = noComments;
    }

    public boolean isLikedPost() {
        return likedPost;
    }

    public void setLikedPost(boolean likedPost) {
        this.likedPost = likedPost;
    }

}