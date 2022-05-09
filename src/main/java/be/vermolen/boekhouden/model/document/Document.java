package be.vermolen.boekhouden.model.document;

import be.vermolen.boekhouden.model.Client;
import be.vermolen.boekhouden.model.line.Line;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "document")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="document_type",
        discriminatorType = DiscriminatorType.STRING, length = 4)
@DiscriminatorValue("DOC")
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String root;

    private Date docDate;
    private Date expireDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "document")
    private List<Line> lines;

    private String notes;

    public String getId() {
        return id.toString();
    }
}
