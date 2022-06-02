package be.vermolen.boekhouden.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    FIVE_DAYS("5 DGN", "FCTR 5 DGN", 5),
    CO("CO", null, 0),
    CONT("CONT", "CONTANT", 0),
    FC_CO("FC/CO", "FCTR / CONT", 0),
    FCT("FCT", "FCTR 15 dgn", 15),
    FCTR("FCTR", "FCTR 30 dgn", 30),
    FCTR_6("FCTR_6", "FCTR 60 dgn", 60),
    ONMI("ONMI", "OV 5 DGN", 0),
    OV("OV", "OV 15 dagen", 15),
    SPONSO("SPONSO", "SPONSORING", 0),
    VD("VD", "VOLDAAN", 0),
    VIA_FC("VIA FC", "Via FCTR", 0),
    VOLD("VOLD", "VOLDAAN-kas", 0),
    VOLDAA("VOLDAA", "VOLDAAN", 0);

    private String name;
    private String description;
    private int days;

    public static PaymentMethod compare(String name) {
        if (name == null) {
            throw new NullPointerException();
        }

        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.getName().equals(name)) {
                return paymentMethod;
            }
        }

        throw new IllegalArgumentException();
    }
}
