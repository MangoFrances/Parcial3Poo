package airport.core.services;

import java.time.LocalDate;

/**
 *
 * @author becer
 */
public class Validator {

    public static boolean isValidId(long id, int maxDigits) {
        return id >= 0 && String.valueOf(id).length() <= maxDigits;
    }

    public static boolean isValidString(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isValidPhoneCode(int code) {
        return code >= 0 && String.valueOf(code).length() <= 3;
    }

    public static boolean isValidPhoneNumber(long phone) {
        return phone >= 0 && String.valueOf(phone).length() <= 11;
    }

    public static boolean isValidBirthDate(LocalDate date) {
        return date != null && !date.isAfter(LocalDate.now());
    }

    public static boolean isValidCapacity(int capacity) {
        return capacity > 0;
    }

    public static boolean isValidCoordinates(double latitude, double longitude) {
        return latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180;
    }

    public static boolean isValidPlaneId(String id) {
        return id.matches("^[A-Z]{2}\\d{5}$");
    }

    public static boolean isValidFlightId(String id) {
        return id.matches("^[A-Z]{3}\\d{3}$");
    }

}
