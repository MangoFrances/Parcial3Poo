package airport.core.validators;

import airport.core.ports.IValidator;

public class PhoneNumberValidator implements IValidator<Long> {

    @Override
    public boolean isValid(Long phone) {
        return phone != null && phone >= 0 && String.valueOf(phone).length() <= 11;
    }

    @Override
    public String getMessage() {
        return "Invalid phone number (≤ 11 digits)";
    }
}
