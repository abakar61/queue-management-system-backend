package com.ali.queue_management_system.service;

import com.ali.queue_management_system.model.Counter;
import com.ali.queue_management_system.repository.CounterRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

    @Autowired
    private CounterRepository counterRepository;

    public String save(Counter counter) {
        counterRepository.save(counter);
        return "Counter saved successfully";
    }

    public List<Counter> getAll() {
        return counterRepository.findAll();
    }

    public Optional<Counter> getById(UUID id) {
        return counterRepository.findById(id);
    }

    public String update(UUID id, Counter newCounter) {
        Optional<Counter> optional = counterRepository.findById(id);

        if (optional.isEmpty()) {
            return "The counter with that id does not exist";
        }

        Counter existingCounter = optional.get();
        existingCounter.setCounterNumber(newCounter.getCounterNumber());
        existingCounter.setName(newCounter.getName());

        counterRepository.save(existingCounter);

        return "Counter updated successfully";
    }

    public String delete(UUID id) {
        Optional<Counter> optional = counterRepository.findById(id);

        if (optional.isEmpty()) {
            return "The counter with that id does not exist";
        }

        counterRepository.delete(optional.get());

        return "Counter deleted successfully";
    }
}
