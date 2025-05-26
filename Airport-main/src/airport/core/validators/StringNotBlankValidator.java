package airport.core.validators;

import airport.core.ports.IValidator;

public class StringNotBlankValidator implements IValidator<String> {

    @Override
    public boolean isValid(String value) {
        return value != null && !value.trim().isEmpty();
    }

    @Override
    public String getMessage() {
        return "Value cannot be blank";
    }
}
