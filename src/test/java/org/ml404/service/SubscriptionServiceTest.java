package org.ml404.service;

import org.junit.jupiter.api.Test;
import org.ml404.dtos.Customer;
import org.ml404.dtos.Subscription;
import org.ml404.persistence.CustomerPersistence;
import org.ml404.persistence.SubscriptionPersistence;
import org.ml404.service.impl.SubscriptionServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SubscriptionServiceTest {

    @Mock
    private SubscriptionPersistence subscriptionPersistence;

    @Mock
    private CustomerPersistence customerPersistence;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    public SubscriptionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSubscription() {
        // Mock data
        Long customerId = 1L;
        String cardDetails = "1234-5678-9876-5432";
        Customer customer = new Customer("John Doe", "john.doe@example.com", "password123");
        customer.setId(customerId);
        Subscription createdSubscription = new Subscription(customer, cardDetails, false, LocalDateTime.now());

        // Mock repository behavior
        when(customerPersistence.findById(customerId)).thenReturn(Optional.of(customer));
        when(subscriptionPersistence.save(any(Subscription.class))).thenReturn(createdSubscription);

        // Call service method
        Subscription result = subscriptionService.createSubscription(customerId, cardDetails);

        // Verify
        assertEquals(createdSubscription, result);
        verify(customerPersistence, times(1)).findById(customerId);
        verify(subscriptionPersistence, times(1)).save(any(Subscription.class));
    }

    @Test
    public void testCreateSubscriptionCustomerNotFound() {
        // Mock data
        Long customerId = 1L;
        String cardDetails = "1234-5678-9876-5432";

        // Mock repository behavior
        when(customerPersistence.findById(customerId)).thenReturn(Optional.empty());

        // Call service method and verify exception
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            subscriptionService.createSubscription(customerId, cardDetails);
        });

        assertEquals("No customer found for id: " + customerId, exception.getMessage());
        verify(customerPersistence, times(1)).findById(customerId);
        verify(subscriptionPersistence, times(0)).save(any(Subscription.class));
    }

    @Test
    public void testGetSubscriptionById() {
        // Mock data
        Long subscriptionId = 1L;
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);

        // Mock repository behavior
        when(subscriptionPersistence.findById(subscriptionId)).thenReturn(Optional.of(subscription));

        // Call service method
        Subscription result = subscriptionService.getSubscriptionById(subscriptionId);

        // Verify
        assertEquals(subscription, result);
        verify(subscriptionPersistence, times(1)).findById(subscriptionId);
    }

    @Test
    public void testGetAllSubscriptions() {
        // Mock data
        List<Subscription> subscriptions = List.of(
                new Subscription(new Customer(), "1234", false, LocalDateTime.now()),
                new Subscription(new Customer(), "5678", false, LocalDateTime.now())
        );

        // Mock repository behavior
        when(subscriptionPersistence.findAll()).thenReturn(subscriptions);

        // Call service method
        List<Subscription> result = subscriptionService.getAllSubscriptions();

        // Verify
        assertEquals(subscriptions.size(), result.size());
        assertEquals(subscriptions, result);
        verify(subscriptionPersistence, times(1)).findAll();
    }

    @Test
    public void testDeleteSubscription() {
        // Mock data
        Long subscriptionId = 1L;

        // Call service method
        subscriptionService.deleteSubscription(subscriptionId);

        // Verify
        verify(subscriptionPersistence, times(1)).deleteById(subscriptionId);
    }
}
