package ru.job4j.todo.service.priority;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.priority.PriorityRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class PriorityServiceSimple implements PriorityService {

    private PriorityRepository priorityRepository;

    public PriorityServiceSimple(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public Collection<Priority> findAll() {
        return priorityRepository.findAll();
    }

    @Override
    public Optional<Priority> findById(int priorityId) {
        return priorityRepository.findById(priorityId);
    }
}
