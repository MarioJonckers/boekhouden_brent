package be.vermolen.boekhouden.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UpdateException extends RuntimeException {
    public UpdateException(String item, String message) {
        super(String.format("Kon %s niet updaten: %s", item, message));
    }
}
