package airport.core.validators;

import airport.core.ports.IValidator;

public class CapacityValidator implements IValidator<Integer> {

    @Override
    public boolean isValid(Integer capacity) {
        return capacity != null && capacity > 0;
    }

    @Override
    public String getMessage() {
        return "Capacity must be a positive integer";
    }
}
