package com.qualle.truegain.client.api;

import java.util.Map;

public class Measure {

    private long id;
    private String name;
    private Map<Float, Float> data;

    public Measure() {
    }

    public Measure(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Map<Float, Float> getData() {
        return data;
    }

    public void setData(Map<Float, Float> data) {
        this.data = data;
    }
}