package be.vermolen.boekhouden.model.document;

import be.vermolen.boekhouden.model.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@DiscriminatorValue("FCT")
public class Invoice extends Document {
    @Transient
    private final String name = "Factuur";

    private Date deliverDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public String getId() {
        return "F-" + super.getId();
    }
}
