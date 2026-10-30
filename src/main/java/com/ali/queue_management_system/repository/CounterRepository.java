package com.ali.queue_management_system.repository;

import com.ali.queue_management_system.model.Counter;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterRepository extends JpaRepository<Counter, UUID> {
}
