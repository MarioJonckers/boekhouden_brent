package be.vermolen.boekhouden.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(String id) {
        super("Het land met id '" + id + "' werd niet teruggevonden.");
    }

    public CountryNotFoundException(String name, boolean isName) {
        super("Het land met naam '" + name + "' werd niet teruggevonden.");
    }
}
