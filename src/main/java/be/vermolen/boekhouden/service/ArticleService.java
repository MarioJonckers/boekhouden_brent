package be.vermolen.boekhouden.service;

import be.vermolen.boekhouden.exception.ArticleNotFoundException;
import be.vermolen.boekhouden.exception.CreateException;
import be.vermolen.boekhouden.exception.UpdateException;
import be.vermolen.boekhouden.model.Article;
import be.vermolen.boekhouden.model.Category;
import be.vermolen.boekhouden.model.Unit;
import be.vermolen.boekhouden.model.dto.CreateArticleDto;
import be.vermolen.boekhouden.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static be.vermolen.boekhouden.util.MyStringUtil.trim;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryService categoryService;

    public Article getById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            throw new ArticleNotFoundException(id);
        }
        return article;
    }

    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    public List<Article> getAllByCategory(Long id) {
        return articleRepository.findAllByCategory_Id(id);
    }

    public Article update(CreateArticleDto article) {
        Article original = getById(article.getId());
        return updateAndSaveArticle(original, article);
    }

    public Article create(CreateArticleDto article) {
        return updateAndSaveArticle(new Article(), article);
    }

    private Article updateAndSaveArticle(Article original, CreateArticleDto article) {
        if (article.getName() == null || article.getName().isBlank()) {
            throw new CreateException("product", "Naam mag niet leeg zijn.");
        }

        String unit = article.getUnit();
        try {
            original.setUnit(Unit.compare(unit));
        } catch (IllegalArgumentException ex) {
            throw new UpdateException("artikel", "Unit '" + unit + "' bestaat niet.");
        } catch (NullPointerException ex) {
            original.setUnit(null);
        }

        original.setName(trim(article.getName(), true));
        Category category = article.getCategory();
        if (category == null || category.getId() == null || category.getId() == -1L) {
            original.setCategory(null);
        } else {
            original.setCategory(category);
        }

        original.setPrice(article.getPrice());
        original.setBtwPercentage(article.getBtwPercentage());
        original.setDescription(article.getDescription());
        original.setNotes(article.getNotes());

        return articleRepository.save(original);
    }
}
