package com.ali.queue_management_system.controller;

import com.ali.queue_management_system.model.QueueTicket;
import com.ali.queue_management_system.model.TicketStatus;
import com.ali.queue_management_system.service.QueueTicketService;
import java.time.LocalDate;
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

        if (message.equals("Queue ticket date cannot be in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        if (message.equals("Agent already has an open ticket on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QueueTicket>> getAll() {
        return ResponseEntity.ok(queueTicketService.getAll());
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getStatsByStatus() {
        return ResponseEntity.ok(queueTicketService.getStatsByStatus());
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID agentId, @RequestParam String date) {
        LocalDate ticketDate = LocalDate.parse(date);
        return ResponseEntity.ok(queueTicketService.cancelDay(agentId, ticketDate));
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<QueueTicket>> getByStatus(@RequestParam TicketStatus status) {
        return ResponseEntity.ok(queueTicketService.getByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<QueueTicket>> getBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(queueTicketService.getBetween(startDate, endDate));
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

        if (message.equals("That queue ticket has service notes, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam TicketStatus status) {
        String message = queueTicketService.updateStatus(id, status);

        if (message.equals("The queue ticket with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        if (message.startsWith("Cannot change status")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        return ResponseEntity.ok(message);
    }
}
