package com.ali.queue_management_system.repository;

import com.ali.queue_management_system.model.Agent;
import com.ali.queue_management_system.model.QueueTicket;
import com.ali.queue_management_system.model.TicketStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QueueTicketRepository extends JpaRepository<QueueTicket, UUID> {

    List<QueueTicket> findByStatusOrderByTicketDateAsc(TicketStatus status);

    List<QueueTicket> findByTicketDateBetweenOrderByTicketDateAsc(LocalDate start, LocalDate end);

    boolean existsByAgentAndTicketDateAndStatusNot(Agent agent, LocalDate ticketDate, TicketStatus status);

    long countByStatus(TicketStatus status);

    long countByTicketDate(LocalDate ticketDate);

    @Query("SELECT t.status, COUNT(t) FROM QueueTicket t GROUP BY t.status")
    List<Object[]> countTicketsByStatus();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE QueueTicket t SET t.status = :newStatus WHERE t.agent.id = :agentId AND t.ticketDate = :ticketDate AND t.status <> :keepStatus")
    int cancelTicketsOfAgentOnDate(
            @Param("agentId") UUID agentId,
            @Param("ticketDate") LocalDate ticketDate,
            @Param("newStatus") TicketStatus newStatus,
            @Param("keepStatus") TicketStatus keepStatus);
}
