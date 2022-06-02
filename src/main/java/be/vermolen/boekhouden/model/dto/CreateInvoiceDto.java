package be.vermolen.boekhouden.model.dto;

import be.vermolen.boekhouden.model.Client;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateInvoiceDto {

    private Long id;
    private Date docDate;
    private Client client;
    private List<LineDto> lines;
    private String notes;
    private String paymentMethod;

    @JsonSetter
    public void setId(String id) {
        if (id != null) {
            this.id = Long.valueOf(id.replace("F-", ""));
        }
    }
}
