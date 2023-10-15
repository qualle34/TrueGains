package com.qualle.truegain.client.api;

import com.qualle.truegain.util.DateFormatterUtil;

import java.time.LocalDateTime;
import java.util.List;

public class Workout {

    private long id;
    private List<Record> records;
    private LocalDateTime date;
    private int achievementsCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getFormattedDate(){
        return DateFormatterUtil.formatToSimpleDate(date);
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getAchievementsCount() {
        return achievementsCount;
    }

    public void setAchievementsCount(int achievementsCount) {
        this.achievementsCount = achievementsCount;
    }
}
