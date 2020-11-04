package org.acme.resources.restaurant;

import com.google.maps.model.OpeningHours;
import com.google.maps.model.Photo;

public class Restaurant {
    private final String id;
    private final String name;
    private final String address;
    private final Photo[] photos;
    private final OpeningHours hours;
    private final float rating;

    private Restaurant(Builder builder) {
        id = builder.id;
        name = builder.name;
        address = builder.address;
        photos =builder.photos;
        hours = builder.hours;
        rating = builder.rating;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Photo[] getPhotos() {
        return photos;
    }

    public OpeningHours getHours() {
        return hours;
    }

    public float getRating() {
        return rating;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{
        private String id;
        private String name;
        private String address;
        private Photo[] photos;
        private OpeningHours hours;
        private float rating;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setPhotos(Photo[] photos) {
            this.photos = photos;
            return this;
        }

        public Builder setHours(OpeningHours hours) {
            this.hours = hours;
            return this;
        }

        public Builder setRating(float rating) {
            this.rating = rating;
            return this;
        }

        public Restaurant build(){
            return new Restaurant(this);
        }
    }
}
