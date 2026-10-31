package com.ali.queue_management_system.repository;

import com.ali.queue_management_system.model.Counter;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CounterRepository extends JpaRepository<Counter, UUID> {

    @Query("SELECT c FROM Counter c WHERE NOT EXISTS (SELECT a FROM Agent a WHERE a.counter = c) ORDER BY c.counterNumber")
    List<Counter> findFreeCounters();
}
