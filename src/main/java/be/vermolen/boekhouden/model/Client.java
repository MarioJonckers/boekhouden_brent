package be.vermolen.boekhouden.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Salutation salutation;
    private String name;

    private String address;
    private String address2;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonIgnoreProperties({"id"})
    private City city;

    private String tel;
    private String mobile;
    private String email;

    private String btwNumber;
}
