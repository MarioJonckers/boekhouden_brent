package be.vermolen.boekhouden.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Unit {
    LM("LM"),
    M2("M²"),
    SK("SK"),
    ST("Stuk"),
    UUR("Uur");

    @JsonValue
    private String name;
}
