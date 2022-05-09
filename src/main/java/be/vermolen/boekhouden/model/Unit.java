package be.vermolen.boekhouden.model;

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

    private String name;
}
