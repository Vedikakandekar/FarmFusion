package com.sem3.farmfusion;


public class Posts {
    String cropName;
    String content;

    String userEmail;
    String cropImageUri;



    String dateTime;

    public Posts() {
    }

    public Posts(String cropName, String content, String userEmail, String cropImageUri, String dateTime) {
        this.cropName = cropName;
        this.content = content;
        this.userEmail = userEmail;
        this.cropImageUri = cropImageUri;
        this.dateTime = dateTime;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCropImageUri() {
        return cropImageUri;
    }

    public void setCropImageUri(String cropImageUri) {
        this.cropImageUri = cropImageUri;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
