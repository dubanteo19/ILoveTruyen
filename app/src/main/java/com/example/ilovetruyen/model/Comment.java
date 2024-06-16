package com.example.ilovetruyen.model;
// Comment.java
public class Comment {
    private String userName;
    private String commentText;
    private String time;
    private int userImage;

    public Comment(String userName, String commentText, String time, int userImage) {
        this.userName = userName;
        this.commentText = commentText;
        this.time = time;
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }
}
