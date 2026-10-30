package com.ali.queue_management_system.repository;

import com.ali.queue_management_system.model.ServiceType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, UUID> {
}
