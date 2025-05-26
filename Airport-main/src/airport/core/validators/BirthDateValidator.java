package airport.core.validators;

import airport.core.ports.IValidator;
import java.time.LocalDate;

public class BirthDateValidator implements IValidator<LocalDate> {

    @Override
    public boolean isValid(LocalDate date) {
        return date != null && !date.isAfter(LocalDate.now());
    }

    @Override
    public String getMessage() {
        return "Birth date cannot be in the future";
    }
}
