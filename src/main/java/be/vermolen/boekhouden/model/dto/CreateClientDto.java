package be.vermolen.boekhouden.model.dto;

import be.vermolen.boekhouden.model.City;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateClientDto implements Serializable {

    private Long id;

    private String salutation;
    private String name;

    private String address;
    private String address2;

    private City city;

    private String tel;
    private String mobile;
    private String email;

    private String btwNumber;
}
