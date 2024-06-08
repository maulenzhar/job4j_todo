package ru.job4j.todo.repository.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernateCategoryRepositoryCommandImpl implements CategoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        return crudRepository.query("from Category order by id asc", Category.class);
    }

    @Override
    public List<Category> findByIds(List<Integer> categoriesId) {
        return crudRepository.query("from Category  where id in (:categoriesId)",
                Category.class,
                Map.of("categoriesId", categoriesId)
                );
    }
}
