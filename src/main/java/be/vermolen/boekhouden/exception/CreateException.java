package be.vermolen.boekhouden.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CreateException extends RuntimeException {
    public CreateException(String item, String message) {
        super(String.format("Kon %s niet aanmaken: %s", item, message));
    }
}
