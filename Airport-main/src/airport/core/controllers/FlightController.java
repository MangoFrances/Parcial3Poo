package airport.core.controllers;

import airport.core.controllers.utils.Response;
import airport.core.controllers.utils.Status;
import airport.core.models.Flight;
import airport.core.models.Location;
import airport.core.models.Plane;
import airport.core.models.storage.Storage;
import airport.core.services.Validator;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author becer
 */
public class FlightController {

    private final Storage storage;

    public FlightController() {
        this.storage = Storage.getInstance();
    }

    public Response createFlight(String id, String planeId,
            String departureLocationId,
            String arrivalLocationId,
            String scaleLocationId,
            LocalDateTime departureDate,
            int hoursDurationArrival,
            int minutesDurationArrival,
            int hoursDurationScale,
            int minutesDurationScale) {

        if (!Validator.isValidFlightId(id)) {
            return new Response("Invalid flight ID format (expected format: FLY123)", Status.BAD_REQUEST);
        }
        if (hoursDurationArrival == 0 && minutesDurationArrival == 0) {
            return new Response("Arrival duration must be greater than zero", Status.BAD_REQUEST);
        }

        if (storage.getFlightById(id) != null) {
            return new Response("A flight with this ID already exists", Status.BAD_REQUEST);
        }

        Plane plane = storage.getPlaneById(planeId);
        if (plane == null) {
            return new Response("Plane not found", Status.NOT_FOUND);
        }

        Location departure = storage.getLocationById(departureLocationId);
        Location arrival = storage.getLocationById(arrivalLocationId);
        if (departure == null || arrival == null) {
            return new Response("Departure or arrival location not found", Status.NOT_FOUND);
        }

        Flight flight;

        if (scaleLocationId == null || scaleLocationId.trim().isEmpty() || scaleLocationId.equals("-")) {

            flight = new Flight(id, plane, departure, arrival, departureDate,
                    hoursDurationArrival, minutesDurationArrival);
        } else {
            Location scale = storage.getLocationById(scaleLocationId);
            if (scale == null) {
                return new Response("Scale location not found", Status.NOT_FOUND);
            }

            flight = new Flight(id, plane, departure, scale, arrival, departureDate,
                    hoursDurationArrival, minutesDurationArrival,
                    hoursDurationScale, minutesDurationScale);
        }

        storage.addFlight(flight);
        return new Response("Flight created successfully", Status.CREATED);
    }

    public Response addPassengerToFlight(long passengerId, String flightId) {
// Obtener pasajero
        var passenger = storage.getPassengerById(passengerId);
        if (passenger == null) {
            return new Response("Passenger not found", Status.NOT_FOUND);
        }

// Obtener vuelo
        var flight = storage.getFlightById(flightId);
        if (flight == null) {
            return new Response("Flight not found", Status.NOT_FOUND);
        }

// Validar que no exceda la capacidad
        if (flight.getNumPassengers() >= flight.getPlane().getMaxCapacity()) {
            return new Response("Flight is full", Status.BAD_REQUEST);
        }

// Verificar si el pasajero ya está agregado
        if (passenger.getFlights().contains(flight)) {
            return new Response("Passenger is already registered in this flight", Status.BAD_REQUEST);
        }

// Enlazar pasajero y vuelo
        flight.addPassenger(passenger);
        passenger.addFlight(flight);

        return new Response("Passenger added to flight successfully", Status.OK);
    }

    public Response delayFlight(String flightId, int hours, int minutes) {
        var flight = storage.getFlightById(flightId);
        if (flight == null) {
            return new Response("Flight not found", Status.NOT_FOUND);
        }

        if (hours < 0 || minutes < 0) {
            return new Response("Delay values must be positive", Status.BAD_REQUEST);
        }

        flight.delay(hours, minutes);
        return new Response("Flight delayed successfully", Status.OK);
    }

    public Response getAllFlights() {
        var flights = storage.getAllFlightsCopy();
        return new Response("Flight list loaded", Status.OK, flights);
    }

    public Response getFlightById(String id) {
        var flight = storage.getFlightById(id);
        if (flight == null) {
            return new Response("Flight not found", Status.NOT_FOUND);
        }
        return new Response("Flight found", Status.OK, flight.copy());
    }

    public Response getAllFlightsOrderedByDate() {
        ArrayList<Flight> flights = storage.getAllFlightsCopy();

        flights.sort((a, b) -> a.getDepartureDate().compareTo(b.getDepartureDate()));

        return new Response("Flight list ordered by date", Status.OK, flights);
    }
}
