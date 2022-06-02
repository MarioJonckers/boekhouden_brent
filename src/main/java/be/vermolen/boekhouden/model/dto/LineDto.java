package be.vermolen.boekhouden.model.dto;

import be.vermolen.boekhouden.model.Article;
import lombok.Data;

@Data
public class LineDto {

    private Long id;
    private String customArticleDescription;
    private int orderInDocument;
    private Article article;
    private double customArticlePrice;
    private int amount;
    private int discountPercentage;
}
