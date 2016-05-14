package com.example.sachin.moviezilla;

/**
 * Created by SACHIN on 14-05-2016.
 */
public class ReviewModel {

    public ReviewModel(){

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    String author;
    String review;


    public ReviewModel( String author, String review)
    {

        this.author=author;
        this.review =review;
    }
}
