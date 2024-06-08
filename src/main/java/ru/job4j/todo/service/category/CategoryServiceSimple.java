package ru.job4j.todo.service.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.category.CategoryRepository;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceSimple implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public Collection<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findByIds(List<Integer> categoriesId) {
        return categoryRepository.findByIds(categoriesId);
    }
}
