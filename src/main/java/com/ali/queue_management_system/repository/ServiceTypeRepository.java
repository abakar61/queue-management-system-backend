package com.ali.queue_management_system.repository;

import com.ali.queue_management_system.model.ServiceType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, UUID> {

    @Query("SELECT s FROM ServiceType s WHERE s.agents IS EMPTY")
    List<ServiceType> findUnusedServiceTypes();
}
