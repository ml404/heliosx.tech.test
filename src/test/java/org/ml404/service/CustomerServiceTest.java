package org.ml404.service;

import org.junit.jupiter.api.Test;
import org.ml404.dtos.Customer;
import org.ml404.persistence.CustomerPersistence;
import org.ml404.service.impl.CustomerServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerPersistence customerPersistence;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void testUpdateCustomer() {
        // Mock data
        Long customerId = 1L;
        String newName = "New Name";
        String newEmail = "newemail@example.com";
        String newPassword = "newPassword123";

        Customer existingCustomer = new Customer("Old Name", "oldemail@example.com", "oldPassword456");
        Customer updatedCustomer = new Customer(newName, newEmail, newPassword);

        // Mock repository behavior
        when(customerPersistence.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerPersistence.save(any(Customer.class))).thenReturn(updatedCustomer);

        // Call service method
        Customer result = customerService.updateCustomer(customerId, newName, newEmail, newPassword);

        // Verify
        assertEquals(newName, result.getName());
        assertEquals(newEmail, result.getEmail());
        assertEquals(newPassword, result.getPassword());
        verify(customerPersistence, times(1)).findById(customerId);
        verify(customerPersistence, times(1)).save(any(Customer.class));
    }

    @Test
    public void testGetCustomerById() {
        // Mock data
        Long customerId = 1L;
        Customer expectedCustomer = new Customer("John Doe", "johndoe@example.com", "password123");

        // Mock repository behavior
        when(customerPersistence.findById(customerId)).thenReturn(Optional.of(expectedCustomer));

        // Call service method
        Customer result = customerService.getCustomerById(customerId);

        // Verify
        assertEquals(expectedCustomer.getName(), result.getName());
        assertEquals(expectedCustomer.getEmail(), result.getEmail());
        assertEquals(expectedCustomer.getPassword(), result.getPassword());
        verify(customerPersistence, times(1)).findById(customerId);
    }

    @Test
    public void testGetAllCustomers() {
        // Mock data
        List<Customer> customers = List.of(
                new Customer("John Doe", "johndoe@example.com", "password123"),
                new Customer("Jane Smith", "janesmith@example.com", "smithpass")
        );

        // Mock repository behavior
        when(customerPersistence.findAll()).thenReturn(customers);

        // Call service method
        List<Customer> result = customerService.getAllCustomers();

        // Verify
        assertEquals(customers.size(), result.size());
        assertEquals(customers.get(0).getName(), result.get(0).getName());
        assertEquals(customers.get(1).getEmail(), result.get(1).getEmail());
        verify(customerPersistence, times(1)).findAll();
    }

    @Test
    public void testDeleteCustomer() {
        // Mock data
        Long customerId = 1L;

        // Call service method
        customerService.deleteCustomer(customerId);

        // Verify
        verify(customerPersistence, times(1)).deleteById(customerId);
    }
}

