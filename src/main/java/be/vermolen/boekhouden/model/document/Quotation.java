package be.vermolen.boekhouden.model.document;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Getter
@Setter
@Entity
@DiscriminatorValue("OFF")
public class Quotation extends Document {
    @Transient
    private final String name = "Offerte";

    public String getId() {
        return "OF" + super.getId();
    }
}
