package be.vermolen.boekhouden.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long id) {
        super("Het artikel met id '" + id + "' werd niet teruggevonden.");
    }
    public CategoryNotFoundException(String name) {
        super("De categorie met naam '" + name + "' werd niet teruggevonden.");
    }
}
