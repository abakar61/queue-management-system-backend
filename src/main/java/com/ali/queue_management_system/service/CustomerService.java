package com.ali.queue_management_system.service;

import com.ali.queue_management_system.model.Customer;
import com.ali.queue_management_system.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public String save(Customer customer) {
        customerRepository.save(customer);
        return "Customer saved successfully";
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getById(UUID id) {
        return customerRepository.findById(id);
    }

    public String update(UUID id, Customer newCustomer) {
        Optional<Customer> optional = customerRepository.findById(id);

        if (optional.isEmpty()) {
            return "The customer with that id does not exist";
        }

        Customer existingCustomer = optional.get();
        existingCustomer.setFirstName(newCustomer.getFirstName());
        existingCustomer.setLastName(newCustomer.getLastName());
        existingCustomer.setNationalId(newCustomer.getNationalId());
        existingCustomer.setPhone(newCustomer.getPhone());

        customerRepository.save(existingCustomer);

        return "Customer updated successfully";
    }

    public String delete(UUID id) {
        Optional<Customer> optional = customerRepository.findById(id);

        if (optional.isEmpty()) {
            return "The customer with that id does not exist";
        }

        customerRepository.delete(optional.get());

        return "Customer deleted successfully";
    }

    public List<Customer> getByLastName(String lastName) {
        return customerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public List<Customer> getCustomersOfAgent(UUID agentId) {
        return customerRepository.findCustomersOfAgent(agentId);
    }
}
