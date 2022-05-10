package be.vermolen.boekhouden.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(Long id) {
        super("De stad met id '" + id + "' werd niet teruggevonden.");
    }

    public CityNotFoundException(String postalCode, boolean isPostalCode) {
        super("De stad met postcode '" + postalCode + "' werd niet teruggevonden.");
    }

    public CityNotFoundException(String city) {
        super("De stad met naam '" + city + "' werd niet teruggevonden.");
    }
}
