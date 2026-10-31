package com.ali.queue_management_system.service;

import com.ali.queue_management_system.model.Agent;
import com.ali.queue_management_system.model.Counter;
import com.ali.queue_management_system.model.ServiceType;
import com.ali.queue_management_system.repository.AgentRepository;
import com.ali.queue_management_system.repository.CounterRepository;
import com.ali.queue_management_system.repository.ServiceTypeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private CounterRepository counterRepository;

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

    public List<Agent> getByServiceType(String name) {
        return agentRepository.findByServiceTypeName(name);
    }

    @Transactional
    public String addServiceType(UUID agentId, UUID serviceTypeId) {
        Optional<Agent> optionalAgent = agentRepository.findById(agentId);

        if (optionalAgent.isEmpty()) {
            return "The agent with that id does not exist";
        }

        Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(serviceTypeId);

        if (optionalServiceType.isEmpty()) {
            return "That service type does not exist";
        }

        Agent agent = optionalAgent.get();
        ServiceType serviceType = optionalServiceType.get();

        if (agent.getServiceTypes() == null) {
            agent.setServiceTypes(new ArrayList<>());
        }

        if (agent.getServiceTypes().contains(serviceType)) {
            return "The agent already has that service type";
        }

        agent.getServiceTypes().add(serviceType);
        agentRepository.save(agent);

        return "Service type added to the agent";
    }

    public String changeCounter(UUID agentId, int counterNumber) {
        Optional<Agent> optionalAgent = agentRepository.findById(agentId);

        if (optionalAgent.isEmpty()) {
            return "The agent with that id does not exist";
        }

        Optional<Counter> optionalCounter = counterRepository.findByCounterNumber(counterNumber);

        if (optionalCounter.isEmpty()) {
            return "The counter with that counterNumber does not exist";
        }

        Agent agent = optionalAgent.get();
        Counter newCounter = optionalCounter.get();

        boolean counterIsTaken = agentRepository.existsByCounter(newCounter);
        boolean agentAlreadyHasIt = agent.getCounter() != null
                && agent.getCounter().getId().equals(newCounter.getId());

        if (counterIsTaken && !agentAlreadyHasIt) {
            return "That counter is taken by another agent";
        }

        agent.setCounter(newCounter);
        agentRepository.save(agent);

        return "Counter changed successfully";
    }
}
