package com.qualle.shapeup.client.api;

import java.util.List;

public class CurrentExercise {

    private long id;
    private String title;
    private String imageLink;
    private List<CurrentRecord> records;

    public CurrentExercise() {
    }

    public CurrentExercise(long id, String title, String imageLink, List<CurrentRecord> records) {
        this.id = id;
        this.title = title;
        this.imageLink = imageLink;
        this.records = records;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<CurrentRecord> getRecords() {
        return records;
    }

    public void setRecords(List<CurrentRecord> records) {
        this.records = records;
    }
}
