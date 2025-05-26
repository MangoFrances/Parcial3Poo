package airport.core.json;

import airport.core.models.Flight;
import airport.core.models.Location;
import airport.core.models.Passenger;
import airport.core.models.Plane;
import airport.core.models.storage.Storage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JsonLoader {

    public void loadPassengers(String filepath) {
        try (InputStream is = new FileInputStream(filepath)) {
            JSONArray array = new JSONArray(new JSONTokener(is));

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                long id = obj.getLong("id");
                String firstname = obj.getString("firstname");
                String lastname = obj.getString("lastname");
                LocalDate birthDate = LocalDate.parse(obj.getString("birthDate"));
                int countryCode = obj.getInt("countryPhoneCode");
                long phone = obj.getLong("phone");
                String country = obj.getString("country");

                Passenger p = new Passenger(id, firstname, lastname, birthDate, countryCode, phone, country);
                Storage.getInstance().addPassenger(p);
            }

            System.out.println("✓ Pasajeros cargados correctamente desde JSON.");

        } catch (Exception e) {
            System.err.println("Error al cargar pasajeros: " + e.getMessage());
        }
    }

    public void loadPlanes(String filepath) {
        try (InputStream is = new FileInputStream(filepath)) {
            JSONArray array = new JSONArray(new JSONTokener(is));

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                String id = obj.getString("id");
                String brand = obj.getString("brand");
                String model = obj.getString("model");
                int maxCapacity = obj.getInt("maxCapacity");
                String airline = obj.getString("airline");

                Plane plane = new Plane(id, brand, model, maxCapacity, airline);
                Storage.getInstance().addPlane(plane);
            }

            System.out.println("✓ Aviones cargados correctamente desde JSON.");

        } catch (Exception e) {
            System.err.println("Error al cargar aviones: " + e.getMessage());
        }
    }

    public void loadLocations(String filepath) {
        try (InputStream is = new FileInputStream(filepath)) {
            JSONArray array = new JSONArray(new JSONTokener(is));

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                String id = obj.getString("airportId");
                String name = obj.getString("airportName");
                String city = obj.getString("airportCity");
                String country = obj.getString("airportCountry");
                double latitude = obj.getDouble("airportLatitude");
                double longitude = obj.getDouble("airportLongitude");

                Location location = new Location(id, name, city, country, latitude, longitude);
                Storage.getInstance().addLocation(location);
            }

            System.out.println("✓ Ubicaciones cargadas correctamente desde JSON.");

        } catch (Exception e) {
            System.err.println("Error al cargar ubicaciones: " + e.getMessage());
        }
    }

    public void loadFlights(String filepath) {
        try (InputStream is = new FileInputStream(filepath)) {
            JSONArray array = new JSONArray(new JSONTokener(is));

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                String id = obj.getString("id");
                String planeId = obj.getString("plane");
                String departureId = obj.getString("departureLocation");
                String arrivalId = obj.getString("arrivalLocation");
                String scaleId = obj.isNull("scaleLocation") ? null : obj.getString("scaleLocation");
                LocalDateTime departureDate = LocalDateTime.parse(obj.getString("departureDate"));
                int hArr = obj.getInt("hoursDurationArrival");
                int mArr = obj.getInt("minutesDurationArrival");
                int hScale = obj.getInt("hoursDurationScale");
                int mScale = obj.getInt("minutesDurationScale");

                Plane plane = Storage.getInstance().getPlaneById(planeId);
                Location departure = Storage.getInstance().getLocationById(departureId);
                Location arrival = Storage.getInstance().getLocationById(arrivalId);
                Location scale = scaleId == null ? null : Storage.getInstance().getLocationById(scaleId);

                if (plane == null || departure == null || arrival == null || (scaleId != null && scale == null)) {
                    System.err.println("✗ Datos incompletos para vuelo " + id + ", se omite.");
                    continue;
                }

                Flight flight;
                if (scale == null) {
                    flight = new Flight(id, plane, departure, arrival, departureDate, hArr, mArr);
                } else {
                    flight = new Flight(id, plane, departure, scale, arrival, departureDate, hArr, mArr, hScale, mScale);
                }

                Storage.getInstance().addFlight(flight);
            }

            System.out.println("✓ Vuelos cargados correctamente desde JSON.");

        } catch (Exception e) {
            System.err.println("Error al cargar vuelos: " + e.getMessage());
        }
    }

    public static void loadAll(String resourcesjson) {
        JsonLoader loader = new JsonLoader();

        String basePath = "src/json/";
        String passengersFile = basePath + "passengers.json";
        String planesFile = basePath + "planes.json";
        String locationsFile = basePath + "locations.json";
        String flightsFile = basePath + "flights.json";

        System.out.println("== Iniciando carga de datos desde archivos JSON ==");

        loader.loadPassengers(passengersFile);
        loader.loadPlanes(planesFile);
        loader.loadLocations(locationsFile);
        loader.loadFlights(flightsFile);

        System.out.println("== Carga completa ✅ ==");
    }

}
