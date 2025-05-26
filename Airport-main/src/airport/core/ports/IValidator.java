package airport.core.ports;

/**
 *
 * @author becer
 * @param <T>
 */
public interface IValidator<T> {

    boolean isValid(T value);

    String getMessage();
}
