package airport.core.controllers;

import airport.core.controllers.utils.Response;
import airport.core.controllers.utils.Status;
import airport.core.models.Location;
import airport.core.models.storage.Storage;
import airport.core.services.Validator;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author becer
 */
public class LocationController {

    private final Storage storage;

    public LocationController() {
        this.storage = Storage.getInstance();
    }

    public Response createLocation(String id, String name, String city, String country, double latitude, double longitude) {
        if (!Validator.isValidString(id)) {
            return new Response("Location ID is required", Status.BAD_REQUEST);
        }

        if (!Validator.isValidString(name) || !Validator.isValidString(city) || !Validator.isValidString(country)) {
            return new Response("Name, city and country are required", Status.BAD_REQUEST);
        }

        if (!Validator.isValidCoordinates(latitude, longitude)) {
            return new Response("Invalid coordinates", Status.BAD_REQUEST);
        }

        if (storage.getLocationById(id) != null) {
            return new Response("A location with this ID already exists", Status.BAD_REQUEST);
        }

        Location location = new Location(id, name, city, country, latitude, longitude);
        storage.addLocation(location);
        return new Response("Location created successfully", Status.CREATED);
    }

    public Response getLocationById(String id) {
        Location loc = storage.getLocationById(id);
        if (loc == null) {
            return new Response("Location not found", Status.NOT_FOUND);
        }
        return new Response("Location found", Status.OK, loc.copy());
    }

    public Response getAllLocations() {
        ArrayList<Location> locations = storage.getAllLocationsCopy();
        return new Response("Location list loaded", Status.OK, locations);
    }

    public Response getAllLocationsOrderedByCity() {
        ArrayList<Location> locations = storage.getAllLocationsCopy();

        locations.sort((a, b) -> a.getAirportCity().compareToIgnoreCase(b.getAirportCity()));

        return new Response("Location list ordered by city", Status.OK, locations);
    }

    public Response getAllLocationsOrdered() {
        ArrayList<Location> locs = storage.getAllLocationsCopy();
        locs.sort(Comparator.comparing(Location::getAirportId));
        return new Response("Location list ordered by ID", Status.OK, locs);
    }
}
