package com.ali.queue_management_system.controller;

import com.ali.queue_management_system.model.Customer;
import com.ali.queue_management_system.service.AgentService;
import com.ali.queue_management_system.service.CustomerService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AgentService agentService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Customer customer) {
        String message = customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Customer>> getByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(customerService.getByLastName(lastName));
    }

    @GetMapping("/of-agent/{agentId}")
    public ResponseEntity<?> getCustomersOfAgent(@PathVariable UUID agentId) {
        if (agentService.getById(agentId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The agent with that id does not exist");
        }

        return ResponseEntity.ok(customerService.getCustomersOfAgent(agentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Customer> result = customerService.getById(id);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The customer with that id does not exist");
        }

        return ResponseEntity.ok(result.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Customer customer) {
        String message = customerService.update(id, customer);

        if (message.equals("The customer with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = customerService.delete(id);

        if (message.equals("The customer with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }
}
