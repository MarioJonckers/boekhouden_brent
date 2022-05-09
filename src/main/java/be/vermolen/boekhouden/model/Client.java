package be.vermolen.boekhouden.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String root;

    @Enumerated(value = EnumType.STRING)
    private Salutation salutation;
    private String name;

    private String address;
    private String address2;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    private String tel;
    private String mobile;
    private String email;

    private String btwNumber;
}
