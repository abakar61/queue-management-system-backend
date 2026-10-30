package com.ali.queue_management_system.service;

import com.ali.queue_management_system.model.Agent;
import com.ali.queue_management_system.repository.AgentRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    public String save(Agent agent) {
        agentRepository.save(agent);
        return "Agent saved successfully";
    }

    public List<Agent> getAll() {
        return agentRepository.findAll();
    }

    public Optional<Agent> getById(UUID id) {
        return agentRepository.findById(id);
    }

    public String update(UUID id, Agent newAgent) {
        Optional<Agent> optional = agentRepository.findById(id);

        if (optional.isEmpty()) {
            return "The agent with that id does not exist";
        }

        Agent existingAgent = optional.get();
        existingAgent.setFirstName(newAgent.getFirstName());
        existingAgent.setLastName(newAgent.getLastName());
        existingAgent.setBadgeNumber(newAgent.getBadgeNumber());
        existingAgent.setCounter(newAgent.getCounter());
        existingAgent.setServiceTypes(newAgent.getServiceTypes());

        agentRepository.save(existingAgent);

        return "Agent updated successfully";
    }

    public String delete(UUID id) {
        Optional<Agent> optional = agentRepository.findById(id);

        if (optional.isEmpty()) {
            return "The agent with that id does not exist";
        }

        agentRepository.delete(optional.get());

        return "Agent deleted successfully";
    }
}
