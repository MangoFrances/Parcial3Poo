package airport.core.models.storage;

import airport.core.models.Plane;
import airport.core.models.Passenger;
import java.util.ArrayList;
import airport.core.models.Location;
import airport.core.models.Flight;

public class Storage {

    private static Storage instance;
    private final ArrayList<Location> locations;
    private final ArrayList<Passenger> passengers;
    private final ArrayList<Plane> planes;
    private final ArrayList<Flight> flights;

    private Storage() {
        this.passengers = new ArrayList<>();
        this.planes = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.flights = new ArrayList<>();

    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

   
    public boolean addPassenger(Passenger p) {
        if (getPassengerById(p.getId()) != null) {
            return false;
        }
        passengers.add(p);
        return true;
    }

    public Passenger getPassengerById(long id) {
        for (Passenger p : passengers) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Passenger> getAllPassengersCopy() {
        ArrayList<Passenger> copyList = new ArrayList<>();
        for (Passenger p : passengers) {
            copyList.add(p.copy());
        }
        return copyList;
    }

    
    public boolean addPlane(Plane p) {
        if (getPlaneById(p.getId()) != null) {
            return false;
        }
        planes.add(p);
        return true;
    }

    public Plane getPlaneById(String id) {
        for (Plane p : planes) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Plane> getAllPlanesCopy() {
        ArrayList<Plane> copyList = new ArrayList<>();
        for (Plane p : planes) {
            copyList.add(p.copy());
        }
        return copyList;
    }

    public boolean addLocation(Location loc) {
        if (getLocationById(loc.getAirportId()) != null) {
            return false;
        }
        locations.add(loc);
        return true;
    }

    public Location getLocationById(String id) {
        for (Location l : locations) {
            if (l.getAirportId().equals(id)) {
                return l;
            }
        }
        return null;
    }

    public ArrayList<Location> getAllLocationsCopy() {
        ArrayList<Location> copyList = new ArrayList<>();
        for (Location l : locations) {
            copyList.add(l.copy());
        }
        return copyList;
    }

    public boolean addFlight(Flight flight) {
        if (getFlightById(flight.getId()) != null) {
            return false;
        }
        flights.add(flight);
        return true;
    }

    public Flight getFlightById(String id) {
        for (Flight f : flights) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    public ArrayList<Flight> getAllFlightsCopy() {
        ArrayList<Flight> copyList = new ArrayList<>();
        for (Flight f : flights) {
            copyList.add(f.copy());
        }
        return copyList;
    }

}
