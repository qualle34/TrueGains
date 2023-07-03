package com.qualle.shapeup.client.api;

public class Image {

    private long id;
    private String name;
    private String link;

    public Image() {
    }

    public Image(long id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
