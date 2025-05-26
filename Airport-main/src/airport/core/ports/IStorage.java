package airport.core.ports;

/**
 *
 * @author becer
 */
import airport.core.models.*;
import java.util.List;

public interface IStorage {

    /* Flights */
    Flight getFlightById(String id);

    List<Flight> getAllFlightsCopy();

    void addFlight(Flight flight);

    /* Passengers */
    Passenger getPassengerById(long id);

    void addPassenger(Passenger p);

    List<Passenger> getAllPassengersCopy();

    /* Planes */
    Plane getPlaneById(String id);

    void addPlane(Plane plane);

    List<Plane> getAllPlanesCopy();

    /* Locations */
    Location getLocationById(String id);

    void addLocation(Location loc);

    List<Location> getAllLocationsCopy();

    void flightUpdated();

    void passengerUpdated();
}
