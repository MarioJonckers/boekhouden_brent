package be.vermolen.boekhouden.controller;

import be.vermolen.boekhouden.model.Article;
import be.vermolen.boekhouden.model.dto.CreateArticleDto;
import be.vermolen.boekhouden.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@AllArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public List<Article> getAll() {
        return articleService.getAll();
    }

    @GetMapping("/{id}")
    public Article getAll(@PathVariable String id) {
        Long lngId = Long.valueOf(id.replace("ART", ""));
        return articleService.getById(lngId);
    }

    @GetMapping("/category/{id}")
    public List<Article> getAllByCategory(@PathVariable Long id) {
        return articleService.getAllByCategory(id);
    }

    @PutMapping
    public Article update(@RequestBody CreateArticleDto article) {
        return articleService.update(article);
    }

    @PostMapping
    public Article create(@RequestBody CreateArticleDto article) {
        return articleService.create(article);
    }
}
