package airport.core.controllers;

import airport.core.controllers.utils.Response;
import airport.core.controllers.utils.Status;
import airport.core.models.Flight;
import airport.core.models.Passenger;
import airport.core.models.storage.Storage;
import airport.core.services.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class PassengerController {

    private final Storage storage;

    public PassengerController() {
        this.storage = Storage.getInstance();
    }

    public Response createPassenger(long id, String firstname, String lastname, LocalDate birthDate,
            int countryPhoneCode, long phone, String country) {

        if (!Validator.isValidId(id, 15)) {
            return new Response("Invalid ID", Status.BAD_REQUEST);
        }

        if (!Validator.isValidString(firstname) || !Validator.isValidString(lastname)) {
            return new Response("Firstname and lastname are required", Status.BAD_REQUEST);
        }

        if (!Validator.isValidBirthDate(birthDate)) {
            return new Response("Invalid birth date", Status.BAD_REQUEST);
        }

        if (!Validator.isValidPhoneCode(countryPhoneCode)) {
            return new Response("Invalid country phone code", Status.BAD_REQUEST);
        }

        if (!Validator.isValidPhoneNumber(phone)) {
            return new Response("Invalid phone number", Status.BAD_REQUEST);
        }

        if (!Validator.isValidString(country)) {
            return new Response("Country is required", Status.BAD_REQUEST);
        }

        if (storage.getPassengerById(id) != null) {
            return new Response("A passenger with this ID already exists", Status.BAD_REQUEST);
        }

        Passenger p = new Passenger(id, firstname, lastname, birthDate, countryPhoneCode, phone, country);
        storage.addPassenger(p);
        return new Response("Passenger registered successfully", Status.CREATED);
    }

    public Response editPassenger(long id, String firstname, String lastname, LocalDate birthDate,
            int countryPhoneCode, long phone, String country) {

        Passenger p = storage.getPassengerById(id);
        if (p == null) {
            return new Response("Passenger not found", Status.NOT_FOUND);
        }

        if (!Validator.isValidString(firstname) || !Validator.isValidString(lastname)) {
            return new Response("Firstname and lastname are required", Status.BAD_REQUEST);
        }

        if (!Validator.isValidBirthDate(birthDate)) {
            return new Response("Invalid birth date", Status.BAD_REQUEST);
        }

        if (!Validator.isValidPhoneCode(countryPhoneCode)) {
            return new Response("Invalid country phone code", Status.BAD_REQUEST);
        }

        if (!Validator.isValidPhoneNumber(phone)) {
            return new Response("Invalid phone number", Status.BAD_REQUEST);
        }

        if (!Validator.isValidString(country)) {
            return new Response("Country is required", Status.BAD_REQUEST);
        }

        p.setFirstname(firstname);
        p.setLastname(lastname);
        p.setBirthDate(birthDate);
        p.setCountryPhoneCode(countryPhoneCode);
        p.setPhone(phone);
        p.setCountry(country);

        return new Response("Passenger updated successfully", Status.OK);
    }

    public Response getPassengerById(long id) {
        Passenger p = storage.getPassengerById(id);
        if (p == null) {
            return new Response("Passenger not found", Status.NOT_FOUND);
        }
        return new Response("Passenger found", Status.OK, p.copy());
    }

    public Response getAllPassengers() {
        ArrayList<Passenger> passengers = storage.getAllPassengersCopy();
        return new Response("Passenger list loaded", Status.OK, passengers);
    }

//getAllPassengersOrderedByName redundante 
    public Response getAllPassengersOrderedByName() {
        ArrayList<Passenger> passengers = storage.getAllPassengersCopy();

        passengers.sort((a, b) -> a.getFullname().compareToIgnoreCase(b.getFullname()));

        return new Response("Passenger list ordered by name", Status.OK, passengers);
    }

    public Response getAllPassengersOrdered() {
        ArrayList<Passenger> passengers = storage.getAllPassengersCopy();
        passengers.sort(Comparator.comparingLong(Passenger::getId));
        return new Response("Passenger list ordered by ID", Status.OK, passengers);
    }

    public Response getFlightsOfPassenger(long id) {
        Passenger p = storage.getPassengerById(id);
        if (p == null) {
            return new Response("Passenger not found", Status.NOT_FOUND);
        }
        ArrayList<Flight> flights = new ArrayList<>(p.getFlights());
        flights.sort(Comparator.comparing(Flight::getDepartureDate));
        return new Response("Flights ordered", Status.OK, flights);
    }
}
