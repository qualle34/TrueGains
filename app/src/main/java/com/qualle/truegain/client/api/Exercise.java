package com.qualle.truegain.client.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Exercise implements Serializable {

    private long id;
    private String name;
    private String equipment;
    private String summary;
    private String description;
    private String technique;
    private String imageLink;
    private String iconLink;
    private List<Record> records;
    private Map<Float, Float> loadData;
    private Map<Float, Float> maxRepData;

    public Exercise() {
    }

    public Exercise(long id, List<Record> records) {
        this.id = id;
        this.records = records;
    }

    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Exercise(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Exercise(long id, String name, String equipment, String description) {
        this.id = id;
        this.name = name;
        this.equipment = equipment;
        this.description = description;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Map<Float, Float> getLoadData() {
        return loadData;
    }

    public void setLoadData(Map<Float, Float> loadData) {
        this.loadData = loadData;
    }

    public Map<Float, Float> getMaxRepData() {
        return maxRepData;
    }

    public void setMaxRepData(Map<Float, Float> maxRepData) {
        this.maxRepData = maxRepData;
    }
}
