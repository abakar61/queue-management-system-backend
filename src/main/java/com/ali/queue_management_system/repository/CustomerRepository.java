package com.ali.queue_management_system.repository;

import com.ali.queue_management_system.model.Customer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT t.customer FROM QueueTicket t WHERE t.agent.id = :agentId")
    List<Customer> findCustomersOfAgent(@Param("agentId") UUID agentId);
}
