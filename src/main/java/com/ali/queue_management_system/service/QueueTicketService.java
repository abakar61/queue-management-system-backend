package com.ali.queue_management_system.service;

import com.ali.queue_management_system.model.QueueTicket;
import com.ali.queue_management_system.model.TicketStatus;
import com.ali.queue_management_system.repository.QueueTicketRepository;
import java.time.LocalDate;
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
        if (queueTicket.getTicketDate().isBefore(LocalDate.now())) {
            return "Queue ticket date cannot be in the past";
        }

        boolean agentIsBusy = queueTicketRepository.existsByAgentAndTicketDateAndStatusNot(
                queueTicket.getAgent(), queueTicket.getTicketDate(), TicketStatus.SKIPPED);

        if (agentIsBusy) {
            return "Agent already has an open ticket on that date";
        }

        queueTicket.setStatus(TicketStatus.WAITING);
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
        existingQueueTicket.setTicketNumber(newQueueTicket.getTicketNumber());
        existingQueueTicket.setCustomer(newQueueTicket.getCustomer());
        existingQueueTicket.setAgent(newQueueTicket.getAgent());

        queueTicketRepository.save(existingQueueTicket);

        return "Queue ticket updated successfully";
    }

    public String updateStatus(UUID id, TicketStatus newStatus) {
        Optional<QueueTicket> optional = queueTicketRepository.findById(id);

        if (optional.isEmpty()) {
            return "The queue ticket with that id does not exist";
        }

        QueueTicket queueTicket = optional.get();
        TicketStatus currentStatus = queueTicket.getStatus();
        boolean allowed = false;

        if (currentStatus == TicketStatus.WAITING) {
            if (newStatus == TicketStatus.CALLED || newStatus == TicketStatus.SKIPPED) {
                allowed = true;
            }
        }

        if (currentStatus == TicketStatus.CALLED) {
            if (newStatus == TicketStatus.SERVED || newStatus == TicketStatus.SKIPPED) {
                allowed = true;
            }
        }

        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }

        queueTicket.setStatus(newStatus);
        queueTicketRepository.save(queueTicket);

        return "Queue ticket status updated successfully";
    }

    public String delete(UUID id) {
        Optional<QueueTicket> optional = queueTicketRepository.findById(id);

        if (optional.isEmpty()) {
            return "The queue ticket with that id does not exist";
        }

        queueTicketRepository.delete(optional.get());

        return "Queue ticket deleted successfully";
    }

    public List<QueueTicket> getByStatus(TicketStatus status) {
        return queueTicketRepository.findByStatusOrderByTicketDateAsc(status);
    }

    public List<QueueTicket> getBetween(LocalDate start, LocalDate end) {
        return queueTicketRepository.findByTicketDateBetweenOrderByTicketDateAsc(start, end);
    }
}
