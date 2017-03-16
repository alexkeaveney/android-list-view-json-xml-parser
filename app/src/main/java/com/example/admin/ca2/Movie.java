package com.example.admin.ca2;

import android.graphics.Bitmap;

import java.util.Date;

public class Movie {

    //variables
    private static int numMovies;
    private int id;
    private String title;
    private String category;
    private String director;
    private String[]actors;
    private String description;
    private Date releaseDate;
    private Bitmap photo;
    private String photoLink;

    //the constructor sets the variables of the movie to default values
    public Movie() {
        numMovies++;
        this.title = "Title " + id;
        this.category = "N/A";
        this.director = "N/A";
        this.description = "Default description";
        this.releaseDate = new Date();
        this.photoLink = "";
    }

    //second constructor allows for creating the object with passed in values with image object
    public Movie(int id, String title, String category, String director, String[] actors, String description, Date releaseDate, Bitmap photo) {
        //sets the id to the number of movies incremented
        numMovies++;
        this.id = id;
        this.title = title;
        this.category = category;
        this.director = director;
        this.actors = actors;
        this.description = description;
        this.releaseDate = releaseDate;
        this.photo = photo;
    }

    //third constructor allows for creating the object with passed in values with photo link
    public Movie(int id, String title, String category, String director, String[] actors, String description, Date releaseDate, String photoLink) {
        //number of movies is incremented
        numMovies++;
        this.id = id;
        this.title = title;
        this.category = category;
        this.director = director;
        this.actors = actors;
        this.description = description;
        this.releaseDate = releaseDate;
        this.photoLink = photoLink;
    }

    //mutator and accessor functions for all of the above variables

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {return this.category; }
    public void setCategory(String cat) { this.category = cat; }

    public String getDirector() {
        return this.director;
    }
    public void setDirector(String director) {
        this.director = director;
    }

    public String[] getActors() { return this.actors; }
    public void setActors(String[]actors) { this.actors = actors; }

    public String getDescription() { return this.description; }
    public void setDescription(String desc) { this.description = desc; }

    public Date getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(Date releaseDate) {this.releaseDate = releaseDate;}

    public Bitmap getPhoto() { return this.photo; }
    public void setImage(Bitmap p) { this.photo = p; }

    public String getPhotoLink() { return this.photoLink; }
    public void setPhotoLink(String link) { this.photoLink = link;}


}
