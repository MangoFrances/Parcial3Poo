package airport.core.controllers;

import airport.core.controllers.utils.Response;
import airport.core.controllers.utils.Status;
import airport.core.models.Location;
import airport.core.models.storage.Storage;
import airport.core.ports.IStorage;
import airport.core.ports.IValidator;
import airport.core.validators.CoordinatesValidator;
import airport.core.validators.StringNotBlankValidator;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author becer
 */
public class LocationController {

    /* ------------- Dependencias ------------- */
    private final IStorage storage;
    private final IValidator<String> stringValidator;
    private final IValidator<double[]> coordinatesValidator;

    /* ---------- Constructor por inyección ---------- */
    public LocationController(IStorage storage,
            IValidator<String> stringValidator,
            IValidator<double[]> coordinatesValidator) {
        this.storage = storage;
        this.stringValidator = stringValidator;
        this.coordinatesValidator = coordinatesValidator;
    }

    /* ---------- Constructor por defecto (aplicación) ---------- */
    public LocationController() {
        this(Storage.getInstance(),
                new StringNotBlankValidator(),
                new CoordinatesValidator());
    }

    /* ================================================ */
 /* ================   CASOS DE USO   =============== */
 /* ================================================ */
    public Response createLocation(String id, String name, String city,
            String country, double latitude, double longitude) {

        if (!stringValidator.isValid(id)) {
            return new Response("Location ID is required", Status.BAD_REQUEST);
        }
        if (!stringValidator.isValid(name)
                || !stringValidator.isValid(city)
                || !stringValidator.isValid(country)) {
            return new Response("Name, city and country are required", Status.BAD_REQUEST);
        }

        if (!coordinatesValidator.isValid(new double[]{latitude, longitude})) {
            return new Response(coordinatesValidator.getMessage(), Status.BAD_REQUEST);
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
        List<Location> locations = storage.getAllLocationsCopy();
        return new Response("Location list loaded", Status.OK, locations);
    }

    public Response getAllLocationsOrderedByCity() {
        List<Location> locations = storage.getAllLocationsCopy();
        locations.sort((a, b) -> a.getAirportCity()
                .compareToIgnoreCase(b.getAirportCity()));
        return new Response("Location list ordered by city", Status.OK, locations);
    }

    public Response getAllLocationsOrdered() {
        List<Location> locs = storage.getAllLocationsCopy();
        locs.sort(Comparator.comparing(Location::getAirportId));
        return new Response("Location list ordered by ID", Status.OK, locs);
    }
}
