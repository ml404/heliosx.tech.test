package org.ml404.service.impl;

import org.ml404.dtos.Customer;
import org.ml404.dtos.Subscription;
import org.ml404.persistence.CustomerPersistence;
import org.ml404.persistence.SubscriptionPersistence;
import org.ml404.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    SubscriptionPersistence subscriptionPersistence;

    @Autowired
    CustomerPersistence customerPersistence;

    @Override
    public Subscription createSubscription(Long customerId, String cardDetails) {
        Customer customer = customerPersistence.findById(customerId).orElseThrow(() -> new IllegalArgumentException("No customer found for id: " + customerId));
        Subscription subscription = new Subscription(customer, cardDetails, false, LocalDateTime.now());
        return subscriptionPersistence.save(subscription);
    }

    @Override
    @Cacheable(value = "subscriptions", key = "#id")
    public Subscription getSubscriptionById(Long id) {
        return subscriptionPersistence.findById(id).orElseThrow(() -> new IllegalArgumentException("No subscription found for id: " + id));
    }

    @Override
    @Cacheable(value = "subscriptions")
    public List<Subscription> getAllSubscriptions() {
        return subscriptionPersistence.findAll();
    }

    @Override
    public Subscription updateSubscriptionStatus(Long id, Boolean active) {
        Subscription subscription = subscriptionPersistence.findById(id).orElseThrow(() -> new IllegalArgumentException("No subscription found for id: " + id));
        if (subscription != null) {
            subscription.setActive(active);
            subscriptionPersistence.save(subscription);
        }
        return subscription;
    }

    @Override
    public void deleteSubscription(Long id) {
        subscriptionPersistence.deleteById(id);
    }
}
