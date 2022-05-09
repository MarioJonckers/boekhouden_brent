package be.vermolen.boekhouden.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Salutation {
    DE_HEER("De heer"),
    MEVROUW("Mevrouw"),
    JUFFROUW("Juffrouw"),
    NV("NV"),
    VOF("VOF"),
    COMM_V("Comm. V"),
    BVBA("BVBA"),
    CVBA("CVBA"),
    CVOA("CVOA"),
    CVA("CVA"),
    ESV("ESV"),
    BVLV("BVLV"),
    EESV("EESV"),
    EBVBA("EBVBA"),
    FAMILIE("Familie"),
    FIRMA("Firma");

    private String name;
}
