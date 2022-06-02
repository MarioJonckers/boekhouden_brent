package be.vermolen.boekhouden.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Data
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private double price;
    private Integer btwPercentage;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private String notes;

    @JsonGetter
    private String getId() {
        return "ART" + StringUtils.leftPad(id.toString(), 5, "0");
    }

    @JsonSetter
    private void setId(String id) {
        this.id = Long.valueOf(id.replace("ART", ""));
    }
}
