package com.ali.queue_management_system.repository;

import com.ali.queue_management_system.model.QueueTicket;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueTicketRepository extends JpaRepository<QueueTicket, UUID> {
}
