package com.ali.queue_management_system.service;

import com.ali.queue_management_system.model.QueueTicket;
import com.ali.queue_management_system.repository.QueueTicketRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueTicketService {

    @Autowired
    private QueueTicketRepository queueTicketRepository;

    public String save(QueueTicket queueTicket) {
        queueTicketRepository.save(queueTicket);
        return "Queue ticket saved successfully";
    }

    public List<QueueTicket> getAll() {
        return queueTicketRepository.findAll();
    }

    public Optional<QueueTicket> getById(UUID id) {
        return queueTicketRepository.findById(id);
    }

    public String update(UUID id, QueueTicket newQueueTicket) {
        Optional<QueueTicket> optional = queueTicketRepository.findById(id);

        if (optional.isEmpty()) {
            return "The queue ticket with that id does not exist";
        }

        QueueTicket existingQueueTicket = optional.get();
        existingQueueTicket.setTicketDate(newQueueTicket.getTicketDate());
        existingQueueTicket.setStatus(newQueueTicket.getStatus());
        existingQueueTicket.setTicketNumber(newQueueTicket.getTicketNumber());
        existingQueueTicket.setCustomer(newQueueTicket.getCustomer());
        existingQueueTicket.setAgent(newQueueTicket.getAgent());

        queueTicketRepository.save(existingQueueTicket);

        return "Queue ticket updated successfully";
    }

    public String delete(UUID id) {
        Optional<QueueTicket> optional = queueTicketRepository.findById(id);

        if (optional.isEmpty()) {
            return "The queue ticket with that id does not exist";
        }

        queueTicketRepository.delete(optional.get());

        return "Queue ticket deleted successfully";
    }
}
