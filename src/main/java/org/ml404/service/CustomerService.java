package org.ml404.service;

import org.ml404.dtos.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(String name, String email, String password);
    Customer getCustomerById(Long id);
    List<Customer> getAllCustomers();
    Customer updateCustomer(Long id, String name, String email, String password);
    void deleteCustomer(Long id);
}
