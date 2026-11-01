package com.ali.queue_management_system.repository;

import com.ali.queue_management_system.model.QueueTicket;
import com.ali.queue_management_system.model.ServiceNote;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceNoteRepository extends JpaRepository<ServiceNote, UUID> {

    @Query("SELECT d FROM ServiceNote d WHERE d.queueTicket.customer.nationalId = :nationalId ORDER BY d.recordedDate DESC")
    List<ServiceNote> findByCustomerNationalId(@Param("nationalId") String nationalId);

    boolean existsByQueueTicket(QueueTicket queueTicket);
}
