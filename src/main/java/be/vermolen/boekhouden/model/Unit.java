package be.vermolen.boekhouden.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Unit {
    ST("Stuk"),
    DAG("Dag"),
    MAAND("Maand"),
    UUR("Uur");

    @JsonValue
    private String name;

    public static Unit compare(String name) {
        if (name == null) {
            throw new NullPointerException();
        }

        for(Unit unit : Unit.values()) {
            if (unit.getName().equals(name)) {
                return unit;
            }
        }

        throw new IllegalArgumentException();
    }
}
