package be.vermolen.boekhouden.model.document;

import be.vermolen.boekhouden.model.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("FCT")
public class Invoice extends Document {
    @Transient
    @JsonIgnore
    private final String name = "Factuur";

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private boolean paid;

    public String getId() {
        return "F-" + getDocDate().getYear() + super.getId();
    }
}
