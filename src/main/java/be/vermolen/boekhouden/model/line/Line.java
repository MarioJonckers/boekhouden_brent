package be.vermolen.boekhouden.model.line;

import be.vermolen.boekhouden.model.document.Document;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "line")
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "line_type",
        discriminatorType = DiscriminatorType.STRING, length = 4)
@DiscriminatorValue("CSTM")
public class  Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    private String customArticleDescription;
    private int orderInDocument;
}
