package airport.core.validators;

import airport.core.ports.IValidator;

public class PhoneCodeValidator implements IValidator<Integer> {

    @Override
    public boolean isValid(Integer code) {
        return code != null && code >= 0 && String.valueOf(code).length() <= 3;
    }

    @Override
    public String getMessage() {
        return "Invalid phone code (0‑999)";
    }
}
