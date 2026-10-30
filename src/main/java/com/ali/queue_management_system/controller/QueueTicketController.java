package com.ali.queue_management_system.controller;

import com.ali.queue_management_system.model.QueueTicket;
import com.ali.queue_management_system.service.QueueTicketService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queue-tickets")
public class QueueTicketController {

    @Autowired
    private QueueTicketService queueTicketService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody QueueTicket queueTicket) {
        String message = queueTicketService.save(queueTicket);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QueueTicket>> getAll() {
        return ResponseEntity.ok(queueTicketService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<QueueTicket> result = queueTicketService.getById(id);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The queue ticket with that id does not exist");
        }

        return ResponseEntity.ok(result.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody QueueTicket queueTicket) {
        String message = queueTicketService.update(id, queueTicket);

        if (message.equals("The queue ticket with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = queueTicketService.delete(id);

        if (message.equals("The queue ticket with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }
}
