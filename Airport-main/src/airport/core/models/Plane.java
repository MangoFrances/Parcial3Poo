package airport.core.models;

import java.util.ArrayList;

/**
 *
 * @author edangulo
 */
public class Plane {

    private final String id;
    private String brand;
    private String model;
    private final int maxCapacity;
    private String airline;
    private ArrayList<Flight> flights;

    public Plane(String id, String brand, String model, int maxCapacity, String airline) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.maxCapacity = maxCapacity;
        this.airline = airline;
        this.flights = new ArrayList<>();

    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getAirline() {
        return airline;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public int getNumFlights() {
        return flights.size();
    }

    public Plane copy() {
        Plane copy = new Plane(this.id, this.brand, this.model, this.maxCapacity, this.airline);

        for (Flight flight : this.flights) {
            copy.getFlights().add(flight);
        }

        return copy;
    }
}
