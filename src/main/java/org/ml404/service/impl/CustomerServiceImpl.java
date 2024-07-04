package org.ml404.service.impl;

import org.ml404.dtos.Customer;
import org.ml404.persistence.CustomerPersistence;
import org.ml404.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerPersistence customerPersistence;

    @Override
    public Customer createCustomer(String name, String email, String password) {
        Customer customer = new Customer(name, email, password);
        return customerPersistence.save(customer);
    }

    @Override
    @Cacheable(value = "customers", key = "#id")
    public Customer getCustomerById(Long id) {
        return customerPersistence.findById(id).orElse(null);
    }

    @Override
    @Cacheable(value = "customers")
    public List<Customer> getAllCustomers() {
        return customerPersistence.findAll();
    }

    @Override
    public Customer updateCustomer(Long id, String name, String email, String password) {
        Customer customer = customerPersistence.findById(id).orElse(null);
        if (customer != null) {
            customer.setName(name);
            customer.setEmail(email);
            customer.setPassword(password);
            customerPersistence.save(customer);
        }
        return customer;
    }

    @Override
    public void deleteCustomer(Long id) {
        customerPersistence.deleteById(id);
    }
}
