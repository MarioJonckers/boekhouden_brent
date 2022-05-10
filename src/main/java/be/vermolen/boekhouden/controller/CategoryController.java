package be.vermolen.boekhouden.controller;

import be.vermolen.boekhouden.model.Category;
import be.vermolen.boekhouden.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @PutMapping
    public Category update(@RequestBody Category category) {
        return categoryService.update(category);
    }
}
