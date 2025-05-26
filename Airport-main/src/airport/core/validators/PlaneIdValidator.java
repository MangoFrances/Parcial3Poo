package airport.core.validators;

import airport.core.ports.IValidator;

public class PlaneIdValidator implements IValidator<String> {

    @Override
    public boolean isValid(String id) {
        return id != null && id.matches("^[A-Z]{2}\\d{5}$");
    }

    @Override
    public String getMessage() {
        return "Invalid plane ID (expected: AA12345)";
    }
}
