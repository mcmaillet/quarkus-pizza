package org.acme.resources.restaurant;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.TextSearchRequest;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.acme.ApplicationConstants;
import org.acme.resources.location.Location;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {
    private static final List<Location> _locations = Collections.synchronizedList(new ArrayList<>());

    private static final String GOOGLE_PLACES_API_KEY = System.getenv(ApplicationConstants.GOOGLE_PLACES_API_KEY);

    public RestaurantResource() {
        _locations.add(new Location("calgary.AB", "Calgary, AB"));
        _locations.add(new Location("edmonton.AB", "Edmonton, AB"));
        _locations.add(new Location("vancouver.BC", "Vancouver, BC"));
    }

    @GET
    public OpenRestaurantResponse getOpenRestaurant(@HeaderParam(ApplicationConstants.TOKEN_KEY) final String token) {
        List<Restaurant> restaurants = new ArrayList<>();
        final Location location = getRandomLocation();
        try {

            final String query = "pizza in " + location.getFormatted();

            TextSearchRequest request = PlacesApi.textSearchQuery(
                    new GeoApiContext.Builder()
                            .apiKey(GOOGLE_PLACES_API_KEY)
                            .build(),
                    query);
            PlacesSearchResponse response = request.await();
            for (PlacesSearchResult result : response.results) {
                if (!result.permanentlyClosed && result.openingHours != null && result.openingHours.openNow) {
                    restaurants.add(
                            Restaurant.newBuilder()
                                    .setId(result.placeId)
                                    .setName(result.name)
                                    .setAddress(result.formattedAddress)
                                    .setPhotos(result.photos)
                                    .setHours(result.openingHours)
                                    .setRating(result.rating)
                                    .build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return OpenRestaurantResponse.newBuilder()
                    .setRestaurant(null)
                    .setLocation(location)
                    .setMessage("Internal error.")
                    .build();
        }
        if (restaurants.isEmpty()) {
            return OpenRestaurantResponse.newBuilder()
                    .setRestaurant(null)
                    .setLocation(location)
                    .setMessage("No open restaurants.")
                    .build();
        }
        return OpenRestaurantResponse.newBuilder()
                .setRestaurant(restaurants.get(getRandomInclusiveRange(0, restaurants.size() - 1)))
                .setLocation(location)
                .setMessage("This one is open!")
                .build();

    }

    private Location getRandomLocation() {
        return _locations.get(getRandomInclusiveRange(0, _locations.size() - 1));
    }

    private int getRandomInclusiveRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
