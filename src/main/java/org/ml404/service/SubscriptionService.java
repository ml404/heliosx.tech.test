package org.ml404.service;

import org.ml404.dtos.Subscription;

import java.util.List;

public interface SubscriptionService {
    Subscription createSubscription(Long customerId, String cardDetails);
    Subscription getSubscriptionById(Long id);
    List<Subscription> getAllSubscriptions();
    Subscription updateSubscriptionStatus(Long id, Boolean active);
    void deleteSubscription(Long id);
}
