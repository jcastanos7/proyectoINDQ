package com.example.proyectoindq.datos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Events {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("attendances")
    @Expose
    private Integer attendances;
    @SerializedName("willYouAttend")
    @Expose
    private Boolean willYouAttend;
    @SerializedName("location")
    @Expose
    private List<Integer> location = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getAttendances() {
        return attendances;
    }

    public void setAttendances(Integer attendances) {
        this.attendances = attendances;
    }

    public Boolean getWillYouAttend() {
        return willYouAttend;
    }

    public void setWillYouAttend(Boolean willYouAttend) {
        this.willYouAttend = willYouAttend;
    }

    public List<Integer> getLocation() {
        return location;
    }

    public void setLocation(List<Integer> location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Events{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                ", attendances=" + attendances +
                ", willYouAttend=" + willYouAttend +
                ", location=" + location +
                '}';
    }
}

