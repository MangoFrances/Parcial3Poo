package airport.core.validators;

import airport.core.ports.IValidator;

public class NumericIdValidator implements IValidator<Long> {

    private final int maxDigits;

    public NumericIdValidator(int maxDigits) {
        this.maxDigits = maxDigits;
    }

    @Override
    public boolean isValid(Long id) {
        return id != null && id >= 0 && String.valueOf(id).length() <= maxDigits;
    }

    @Override
    public String getMessage() {
        return "ID must be positive and ≤ " + maxDigits + " digits";
    }
}
