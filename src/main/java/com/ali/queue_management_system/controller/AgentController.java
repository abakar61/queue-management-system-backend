package com.ali.queue_management_system.controller;

import com.ali.queue_management_system.model.Agent;
import com.ali.queue_management_system.service.AgentService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Agent agent) {
        String message = agentService.save(agent);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Agent>> getAll() {
        return ResponseEntity.ok(agentService.getAll());
    }

    @GetMapping("/by-service-type")
    public ResponseEntity<List<Agent>> getByServiceType(@RequestParam String name) {
        return ResponseEntity.ok(agentService.getByServiceType(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Agent> result = agentService.getById(id);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The agent with that id does not exist");
        }

        return ResponseEntity.ok(result.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Agent agent) {
        String message = agentService.update(id, agent);

        if (message.equals("The agent with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = agentService.delete(id);

        if (message.equals("The agent with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @PutMapping("/{agentId}/service-types/{serviceTypeId}")
    public ResponseEntity<String> addServiceType(@PathVariable UUID agentId, @PathVariable UUID serviceTypeId) {
        String message = agentService.addServiceType(agentId, serviceTypeId);

        if (message.equals("The agent with that id does not exist")
                || message.equals("That service type does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        if (message.equals("The agent already has that service type")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{agentId}/counter")
    public ResponseEntity<String> changeCounter(@PathVariable UUID agentId, @RequestParam int counterNumber) {
        String message = agentService.changeCounter(agentId, counterNumber);

        if (message.equals("The agent with that id does not exist")
                || message.equals("The counter with that counterNumber does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        if (message.equals("That counter is taken by another agent")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        return ResponseEntity.ok(message);
    }
}
