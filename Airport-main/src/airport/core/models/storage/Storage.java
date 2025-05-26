package airport.core.models.storage;

import airport.core.models.Plane;
import airport.core.models.Passenger;
import java.util.ArrayList;
import airport.core.models.Location;
import airport.core.models.Flight;
import airport.core.ports.IStorage;
import airport.core.ports.ModelChangeListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Storage implements IStorage {

    private static final Storage INSTANCE = new Storage();

    public static Storage getInstance() {
        return INSTANCE;
    }

    private Storage() {
    }

    private final List<Flight> flights = new ArrayList<>();
    private final List<Passenger> passengers = new ArrayList<>();
    private final List<Plane> planes = new ArrayList<>();
    private final List<Location> locations = new ArrayList<>();

    private final List<ModelChangeListener> listeners = new CopyOnWriteArrayList<>();

    public void addListener(ModelChangeListener l) {
        listeners.add(l);
    }

    public void removeListener(ModelChangeListener l) {
        listeners.remove(l);
    }

    private void notifyChange(String entity) {
        for (ModelChangeListener l : listeners) {
            l.onModelChanged(entity);
        }
    }

    @Override
    public Flight getFlightById(String id) {
        return flights.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Flight> getAllFlightsCopy() {
        List<Flight> copy = new ArrayList<>();
        for (Flight f : flights) {
            copy.add(f.copy());
        }
        return copy;
    }

    @Override
    public void addFlight(Flight flight) {
        flights.add(flight);
        notifyChange("flight");
    }

    @Override
    public Passenger getPassengerById(long id) {
        return passengers.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addPassenger(Passenger p) {
        passengers.add(p);
        notifyChange("passenger");
    }

    @Override
    public Plane getPlaneById(String id) {
        return planes.stream()
                .filter(pl -> pl.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addPlane(Plane plane) {
        planes.add(plane);
        notifyChange("plane");
    }

    @Override
    public Location getLocationById(String id) {
        return locations.stream()
                .filter(l -> l.getAirportId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addLocation(Location loc) {
        locations.add(loc);
        notifyChange("location");
    }

    @Override
    public List<Passenger> getAllPassengersCopy() {
        List<Passenger> copy = new ArrayList<>();
        for (Passenger p : passengers) {
            copy.add(p.copy());
        }
        return copy;
    }

    @Override
    public List<Plane> getAllPlanesCopy() {
        List<Plane> copy = new ArrayList<>();
        for (Plane pl : planes) {
            copy.add(pl.copy());
        }
        return copy;
    }

    @Override
    public List<Location> getAllLocationsCopy() {
        List<Location> copy = new ArrayList<>();
        for (Location loc : locations) {
            copy.add(loc.copy());
        }
        return copy;
    }

    @Override
    public void flightUpdated() {
        notifyChange("flight");
    }

    @Override
    public void passengerUpdated() {
        notifyChange("passenger");
    }

    public void clearAll() {
        flights.clear();
        passengers.clear();
        planes.clear();
        locations.clear();
        notifyChange("clear");
    }

}
