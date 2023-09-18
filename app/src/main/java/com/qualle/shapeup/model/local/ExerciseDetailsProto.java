package com.qualle.shapeup.model.local;

import java.io.Serializable;
import java.util.List;

public class ExerciseDetailsProto implements Serializable {

    private long id;
    private String title;
    private String imageLink;
    private List<RecordDetailsProto> records;

    public ExerciseDetailsProto() {
    }

    public ExerciseDetailsProto(long id, String title, String imageLink) {
        this.id = id;
        this.title = title;
        this.imageLink = imageLink;
    }

    public ExerciseDetailsProto(long id, String title, String imageLink, List<RecordDetailsProto> records) {
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

    public List<RecordDetailsProto> getRecords() {
        return records;
    }

    public void setRecords(List<RecordDetailsProto> records) {
        this.records = records;
    }

    public static class RecordDetailsProto implements Serializable {
        private long id;
        private float weight;
        private int reps;

        public RecordDetailsProto(long id, float weight, int reps) {
            this.id = id;
            this.weight = weight;
            this.reps = reps;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public int getReps() {
            return reps;
        }

        public void setReps(int reps) {
            this.reps = reps;
        }
    }
}
