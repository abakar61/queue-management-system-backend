package com.ali.queue_management_system.repository;

import com.ali.queue_management_system.model.Agent;
import com.ali.queue_management_system.model.Counter;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgentRepository extends JpaRepository<Agent, UUID> {

    @Query("SELECT DISTINCT a FROM Agent a JOIN a.serviceTypes s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Agent> findByServiceTypeName(@Param("name") String name);

    boolean existsByCounter(Counter counter);
}
