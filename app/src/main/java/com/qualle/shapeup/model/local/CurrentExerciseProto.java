package com.qualle.shapeup.model.local;

import java.util.List;

public class CurrentExerciseProto {

    private long id;
    private String title;
    private String imageLink;
    private List<CurrentRecordProto> records;

    public CurrentExerciseProto() {
    }

    public CurrentExerciseProto(long id, String title, String imageLink, List<CurrentRecordProto> records) {
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

    public List<CurrentRecordProto> getRecords() {
        return records;
    }

    public void setRecords(List<CurrentRecordProto> records) {
        this.records = records;
    }
}
