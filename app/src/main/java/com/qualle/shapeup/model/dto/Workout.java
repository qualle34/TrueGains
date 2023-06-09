package com.qualle.shapeup.model.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public class Workout {

    private List<Record> records;
    private LocalDateTime date;

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
