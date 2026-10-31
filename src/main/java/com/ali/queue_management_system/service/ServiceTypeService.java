package com.ali.queue_management_system.service;

import com.ali.queue_management_system.model.ServiceType;
import com.ali.queue_management_system.repository.ServiceTypeRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeService {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    public String save(ServiceType serviceType) {
        serviceTypeRepository.save(serviceType);
        return "Service type saved successfully";
    }

    public List<ServiceType> getAll() {
        return serviceTypeRepository.findAll();
    }

    public Optional<ServiceType> getById(UUID id) {
        return serviceTypeRepository.findById(id);
    }

    public String update(UUID id, ServiceType newServiceType) {
        Optional<ServiceType> optional = serviceTypeRepository.findById(id);

        if (optional.isEmpty()) {
            return "The service type with that id does not exist";
        }

        ServiceType existingServiceType = optional.get();
        existingServiceType.setName(newServiceType.getName());

        serviceTypeRepository.save(existingServiceType);

        return "Service type updated successfully";
    }

    public String delete(UUID id) {
        Optional<ServiceType> optional = serviceTypeRepository.findById(id);

        if (optional.isEmpty()) {
            return "The service type with that id does not exist";
        }

        serviceTypeRepository.delete(optional.get());

        return "Service type deleted successfully";
    }

    public List<ServiceType> getUnused() {
        return serviceTypeRepository.findUnusedServiceTypes();
    }
}
