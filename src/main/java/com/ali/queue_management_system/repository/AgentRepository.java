package com.ali.queue_management_system.repository;

import com.ali.queue_management_system.model.Agent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, UUID> {
}
