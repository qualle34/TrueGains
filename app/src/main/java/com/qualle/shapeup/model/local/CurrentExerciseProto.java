package com.qualle.shapeup.model.local;

import java.util.List;

public class CurrentExerciseProto {

    private long id;
    private String name;
    private String equipment;
    private String imageLink;
    private List<CurrentRecordProto> records;

    public CurrentExerciseProto() {
    }

    public CurrentExerciseProto(long id, String name, String equipment, String imageLink) {
        this.id = id;
        this.name = name;
        this.equipment = equipment;
        this.imageLink = imageLink;
    }

    public CurrentExerciseProto(long id, String name, String equipment, String imageLink, List<CurrentRecordProto> records) {
        this.id = id;
        this.name = name;
        this.equipment = equipment;
        this.imageLink = imageLink;
        this.records = records;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
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
