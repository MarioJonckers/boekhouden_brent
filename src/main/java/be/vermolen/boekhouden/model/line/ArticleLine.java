package be.vermolen.boekhouden.model.line;

import be.vermolen.boekhouden.model.Article;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
@DiscriminatorValue("ART")
public class ArticleLine extends Line {

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    private double customArticlePrice;

    private double amount;
    private int discountPercentage;
}
