package org.acme.resources.restaurant;


import org.acme.resources.location.Location;

public class OpenRestaurantResponse {
    private final Restaurant restaurant;
    private final Location location;
    private final String message;

    private OpenRestaurantResponse(Builder builder) {
        restaurant = builder.restaurant;
        location = builder.location;
        message = builder.message;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Location getLocation() {
        return location;
    }

    public String getMessage() {
        return message;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Restaurant restaurant;
        private Location location;
        private String message;

        public Builder setRestaurant(Restaurant restaurant) {
            this.restaurant = restaurant;
            return this;
        }

        public Builder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public OpenRestaurantResponse build() {
            return new OpenRestaurantResponse(this);
        }
    }
}
