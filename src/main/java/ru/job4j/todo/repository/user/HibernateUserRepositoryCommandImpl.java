package ru.job4j.todo.repository.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.task.CrudRepository;

import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Repository
public class HibernateUserRepositoryCommandImpl implements UserRepository {

    private final CrudRepository crudRepository;


    @Override
    public Optional<User> save(User user) {
        crudRepository.run(session -> session.persist(user));
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "from User as u where u.password = :fPassword and u.login = :fLogin", User.class,
                Map.of("fLogin", login,
                        "fPassword", password)
        );
    }
}
