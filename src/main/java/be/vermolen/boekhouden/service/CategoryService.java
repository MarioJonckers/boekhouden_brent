package be.vermolen.boekhouden.service;

import be.vermolen.boekhouden.exception.CategoryNotFoundException;
import be.vermolen.boekhouden.exception.CreateException;
import be.vermolen.boekhouden.model.Category;
import be.vermolen.boekhouden.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static be.vermolen.boekhouden.util.MyStringUtil.trim;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new CategoryNotFoundException(id);
        }
        return category;
    }

    public Category findByName(String name) {
        Category category = categoryRepository.findByName(name).orElse(null);
        if (category == null) {
            throw new CategoryNotFoundException(name);
        }
        return category;
    }

    public Category update(Category category) {
        Category original = getById(category.getId());
        return updateAndSaveCategory(original, category);
    }

    public Category create(Category category) {
        return updateAndSaveCategory(new Category(), category);
    }

    private Category updateAndSaveCategory(Category original, Category category) {
        try {
            original = findByName(category.getName());

            return original;
        } catch (CategoryNotFoundException ignore) {
            if (category.getName() == null || category.getName().isBlank()) {
                throw new CreateException("categorie", "Naam mag niet leeg zijn.");
            }

            original.setName(trim(category.getName(), true));

            return categoryRepository.save(original);
        }
    }
}
