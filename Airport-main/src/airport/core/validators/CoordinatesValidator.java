package airport.core.validators;

import airport.core.ports.IValidator;

public class CoordinatesValidator implements IValidator<double[]> {

    @Override
    public boolean isValid(double[] coords) {
        if (coords == null || coords.length != 2) return false;
        double lat = coords[0], lon = coords[1];
        return lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180;
    }

    @Override
    public String getMessage() {
        return "Latitude must be –90..90 and longitude –180..180";
    }
}
