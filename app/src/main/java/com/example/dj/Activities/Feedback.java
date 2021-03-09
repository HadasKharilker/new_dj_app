package com.example.dj.Activities;

public class Feedback {
    private String nameOfRater;
    private String rating;
    private String feedBackText;

    public Feedback(String nameOfRater, String rating, String feedBackText) {
        this.nameOfRater = nameOfRater;
        this.rating = rating;
        this.feedBackText = feedBackText;
    }
    public Feedback(){}

    public String getFeedBackText() {
        return feedBackText;
    }

    public void setFeedBackText(String feedBackText) {
        this.feedBackText = feedBackText;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNameOfRater() {
        return nameOfRater;
    }

    public void setNameOfRater(String nameOfRater) {
        this.nameOfRater = nameOfRater;
    }
}
