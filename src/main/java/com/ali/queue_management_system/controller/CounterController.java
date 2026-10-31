package com.ali.queue_management_system.controller;

import com.ali.queue_management_system.model.Counter;
import com.ali.queue_management_system.service.CounterService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/counters")
public class CounterController {

    @Autowired
    private CounterService counterService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Counter counter) {
        String message = counterService.save(counter);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Counter>> getAll() {
        return ResponseEntity.ok(counterService.getAll());
    }

    @GetMapping("/free")
    public ResponseEntity<List<Counter>> getFree() {
        return ResponseEntity.ok(counterService.getFree());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Counter> result = counterService.getById(id);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The counter with that id does not exist");
        }

        return ResponseEntity.ok(result.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Counter counter) {
        String message = counterService.update(id, counter);

        if (message.equals("The counter with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = counterService.delete(id);

        if (message.equals("The counter with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }
}
