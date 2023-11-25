package com.qualle.truegain.client.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SimpleExercise implements Serializable {

    private long id;
    private String name;
    private String equipment;
    private int recordCount;

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

    public int getRecordsCount() {
        return recordCount;
    }

    public void setRecordsCount(int recordCount) {
        this.recordCount = recordCount;
    }
}
