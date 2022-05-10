package be.vermolen.boekhouden.model.dto;

import be.vermolen.boekhouden.model.Category;
import lombok.Data;

@Data
public class CreateArticleDto {

    private String id;

    private String name;
    private String description;

    private Category category;

    private double price;
    private Integer btwPercentage;

    private String unit;

    private String notes;

    public Long getId() {
        return Long.valueOf(id.replace("ART", ""));
    }
}
