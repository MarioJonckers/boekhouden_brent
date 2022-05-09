package be.vermolen.boekhouden.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "country")
public class Country {

    @Id
    private String id;
    private String name;
}
