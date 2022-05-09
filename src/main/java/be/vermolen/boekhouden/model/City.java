package be.vermolen.boekhouden.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postalCode;
    private String city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
