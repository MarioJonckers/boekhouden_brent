package be.vermolen.boekhouden.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Recurrence {
    WEEKLY,
    MONTHLY,
    TRIMESTERIAL,
    QUARTERLY,
    SEMESTER,
    YEARLY
}
