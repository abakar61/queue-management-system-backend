package com.ali.queue_management_system.repository;

import com.ali.queue_management_system.model.Agent;
import com.ali.queue_management_system.model.QueueTicket;
import com.ali.queue_management_system.model.TicketStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueTicketRepository extends JpaRepository<QueueTicket, UUID> {

    List<QueueTicket> findByStatusOrderByTicketDateAsc(TicketStatus status);

    List<QueueTicket> findByTicketDateBetweenOrderByTicketDateAsc(LocalDate start, LocalDate end);

    boolean existsByAgentAndTicketDateAndStatusNot(Agent agent, LocalDate ticketDate, TicketStatus status);
}
