package com.ali.queue_management_system.service;

import com.ali.queue_management_system.model.TicketStatus;
import com.ali.queue_management_system.repository.AgentRepository;
import com.ali.queue_management_system.repository.CounterRepository;
import com.ali.queue_management_system.repository.CustomerRepository;
import com.ali.queue_management_system.repository.QueueTicketRepository;
import com.ali.queue_management_system.repository.ServiceNoteRepository;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private QueueTicketRepository queueTicketRepository;

    @Autowired
    private ServiceNoteRepository serviceNoteRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("customers", customerRepository.count());
        summary.put("agents", agentRepository.count());
        summary.put("freeCounters", (long) counterRepository.findFreeCounters().size());
        summary.put("ticketsToday", queueTicketRepository.countByTicketDate(LocalDate.now()));
        summary.put("waiting", queueTicketRepository.countByStatus(TicketStatus.WAITING));
        summary.put("serviceNotes", serviceNoteRepository.count());

        return summary;
    }
}
