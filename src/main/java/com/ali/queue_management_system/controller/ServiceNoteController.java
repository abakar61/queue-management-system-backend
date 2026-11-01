package com.ali.queue_management_system.controller;

import com.ali.queue_management_system.model.ServiceNote;
import com.ali.queue_management_system.service.ServiceNoteService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-notes")
public class ServiceNoteController {

    @Autowired
    private ServiceNoteService serviceNoteService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID queueTicketId, @RequestBody ServiceNote serviceNote) {
        String message = serviceNoteService.save(queueTicketId, serviceNote);

        if (message.equals("The queue ticket with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        if (message.equals("Service notes are only for SERVED queue tickets")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/customer/{nationalId}")
    public ResponseEntity<List<ServiceNote>> getByCustomer(@PathVariable String nationalId) {
        return ResponseEntity.ok(serviceNoteService.getByCustomer(nationalId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = serviceNoteService.delete(id);

        if (message.equals("The service note with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }
}
