package airport.core.validators;

import airport.core.ports.IValidator;

public class FlightIdValidator implements IValidator<String> {

    @Override
    public boolean isValid(String id) {
        return id != null && id.matches("^[A-Z]{3}\\d{3}$");
    }

    @Override
    public String getMessage() {
        return "Invalid flight ID (expected: FLY123)";
    }
}
