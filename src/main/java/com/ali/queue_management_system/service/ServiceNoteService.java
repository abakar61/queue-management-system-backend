package com.ali.queue_management_system.service;

import com.ali.queue_management_system.model.QueueTicket;
import com.ali.queue_management_system.model.ServiceNote;
import com.ali.queue_management_system.model.TicketStatus;
import com.ali.queue_management_system.repository.QueueTicketRepository;
import com.ali.queue_management_system.repository.ServiceNoteRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceNoteService {

    @Autowired
    private ServiceNoteRepository serviceNoteRepository;

    @Autowired
    private QueueTicketRepository queueTicketRepository;

    public String save(UUID queueTicketId, ServiceNote serviceNote) {
        Optional<QueueTicket> optional = queueTicketRepository.findById(queueTicketId);

        if (optional.isEmpty()) {
            return "The queue ticket with that id does not exist";
        }

        QueueTicket queueTicket = optional.get();

        if (queueTicket.getStatus() != TicketStatus.SERVED) {
            return "Service notes are only for SERVED queue tickets";
        }

        serviceNote.setId(null);
        serviceNote.setRecordedDate(LocalDate.now());
        serviceNote.setQueueTicket(queueTicket);
        serviceNoteRepository.save(serviceNote);

        return "Service note saved successfully";
    }

    public List<ServiceNote> getByCustomer(String nationalId) {
        return serviceNoteRepository.findByCustomerNationalId(nationalId);
    }

    public String delete(UUID id) {
        Optional<ServiceNote> optional = serviceNoteRepository.findById(id);

        if (optional.isEmpty()) {
            return "The service note with that id does not exist";
        }

        serviceNoteRepository.delete(optional.get());

        return "Service note deleted successfully";
    }
}
