package airport.core.controllers;

import airport.core.controllers.utils.Response;
import airport.core.controllers.utils.Status;
import airport.core.models.Flight;
import airport.core.models.Passenger;
import airport.core.models.storage.Storage;
import airport.core.ports.IStorage;
import airport.core.ports.IValidator;
import airport.core.validators.BirthDateValidator;
import airport.core.validators.NumericIdValidator;
import airport.core.validators.PhoneCodeValidator;
import airport.core.validators.PhoneNumberValidator;
import airport.core.validators.StringNotBlankValidator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PassengerController {

    /* ================== DEPENDENCIAS ================== */
    private final IStorage storage;

    private final IValidator<Long> idValidator;
    private final IValidator<String> stringValidator;
    private final IValidator<LocalDate> birthDateValidator;
    private final IValidator<Integer> phoneCodeValidator;
    private final IValidator<Long> phoneNumberValidator;

    /* ---------- Constructor por inyección (tests) ---------- */
    public PassengerController(IStorage storage,
            IValidator<Long> idValidator,
            IValidator<String> stringValidator,
            IValidator<LocalDate> birthDateValidator,
            IValidator<Integer> phoneCodeValidator,
            IValidator<Long> phoneNumberValidator) {

        this.storage = storage;
        this.idValidator = idValidator;
        this.stringValidator = stringValidator;
        this.birthDateValidator = birthDateValidator;
        this.phoneCodeValidator = phoneCodeValidator;
        this.phoneNumberValidator = phoneNumberValidator;
    }

    /* ---------- Constructor por defecto (aplicación) ---------- */
    public PassengerController() {
        this(Storage.getInstance(),
                new NumericIdValidator(15),
                new StringNotBlankValidator(),
                new BirthDateValidator(),
                new PhoneCodeValidator(),
                new PhoneNumberValidator());
    }

    /* ===================================================== */
 /* ===================  CASOS DE USO  ================== */
 /* ===================================================== */
    public Response createPassenger(long id, String firstname, String lastname,
            LocalDate birthDate, int countryPhoneCode,
            long phone, String country) {

        if (!idValidator.isValid(id)) {
            return new Response(idValidator.getMessage(), Status.BAD_REQUEST);
        }
        if (!stringValidator.isValid(firstname) || !stringValidator.isValid(lastname)) {
            return new Response("Firstname and lastname are required", Status.BAD_REQUEST);
        }
        if (!birthDateValidator.isValid(birthDate)) {
            return new Response(birthDateValidator.getMessage(), Status.BAD_REQUEST);
        }
        if (!phoneCodeValidator.isValid(countryPhoneCode)) {
            return new Response(phoneCodeValidator.getMessage(), Status.BAD_REQUEST);
        }
        if (!phoneNumberValidator.isValid(phone)) {
            return new Response(phoneNumberValidator.getMessage(), Status.BAD_REQUEST);
        }
        if (!stringValidator.isValid(country)) {
            return new Response("Country is required", Status.BAD_REQUEST);
        }
        if (storage.getPassengerById(id) != null) {
            return new Response("A passenger with this ID already exists", Status.BAD_REQUEST);
        }

        Passenger p = new Passenger(id, firstname, lastname, birthDate,
                countryPhoneCode, phone, country);
        storage.addPassenger(p);
        return new Response("Passenger registered successfully", Status.CREATED);
    }

    public Response editPassenger(long id, String firstname, String lastname,
            LocalDate birthDate, int countryPhoneCode,
            long phone, String country) {

        Passenger p = storage.getPassengerById(id);
        if (p == null) {
            return new Response("Passenger not found", Status.NOT_FOUND);
        }

        if (!stringValidator.isValid(firstname) || !stringValidator.isValid(lastname)) {
            return new Response("Firstname and lastname are required", Status.BAD_REQUEST);
        }
        if (!birthDateValidator.isValid(birthDate)) {
            return new Response(birthDateValidator.getMessage(), Status.BAD_REQUEST);
        }
        if (!phoneCodeValidator.isValid(countryPhoneCode)) {
            return new Response(phoneCodeValidator.getMessage(), Status.BAD_REQUEST);
        }
        if (!phoneNumberValidator.isValid(phone)) {
            return new Response(phoneNumberValidator.getMessage(), Status.BAD_REQUEST);
        }
        if (!stringValidator.isValid(country)) {
            return new Response("Country is required", Status.BAD_REQUEST);
        }

        p.setFirstname(firstname);
        p.setLastname(lastname);
        p.setBirthDate(birthDate);
        p.setCountryPhoneCode(countryPhoneCode);
        p.setPhone(phone);
        p.setCountry(country);

        storage.passengerUpdated();
        return new Response("Passenger updated successfully", Status.OK);
    }

    /* --------- Consultas --------- */
    public Response getPassengerById(long id) {
        Passenger p = storage.getPassengerById(id);
        if (p == null) {
            return new Response("Passenger not found", Status.NOT_FOUND);
        }
        return new Response("Passenger found", Status.OK, p.copy());
    }

    public Response getAllPassengers() {
        List<Passenger> passengers = storage.getAllPassengersCopy();
        return new Response("Passenger list loaded", Status.OK, passengers);
    }

    public Response getAllPassengersOrderedByName() {
        List<Passenger> passengers = storage.getAllPassengersCopy();
        passengers.sort((a, b) -> a.getFullname()
                .compareToIgnoreCase(b.getFullname()));
        return new Response("Passenger list ordered by name", Status.OK, passengers);
    }

    public Response getAllPassengersOrdered() {
        List<Passenger> passengers = storage.getAllPassengersCopy();
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
