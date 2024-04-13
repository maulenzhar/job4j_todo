package ru.job4j.todo.service.user;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.user.UserRepository;

import java.util.Optional;

@Service
public class UserServiceSimple implements UserService {

    private UserRepository userRepository;

    public UserServiceSimple(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> findByEmailAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }
}
