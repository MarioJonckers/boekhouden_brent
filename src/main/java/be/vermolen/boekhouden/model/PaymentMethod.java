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
    FCTR("FCTR", "FCTR", 0),
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
}
