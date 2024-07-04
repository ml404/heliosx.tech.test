package org.ml404.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ml404.dtos.Subscription;
import org.ml404.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "Subscription", description = "Subscription management APIs")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    @Operation(summary = "Create a new subscription")
    public ResponseEntity<Subscription> createSubscription(
            @RequestParam Long consultationId,
            @RequestParam String status) {
        Subscription createdSubscription = subscriptionService.createSubscription(consultationId, status);
        return ResponseEntity.ok(createdSubscription);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a subscription by ID")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        return ResponseEntity.ok(subscription);
    }

    @GetMapping
    @Operation(summary = "Get all subscriptions")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a subscription by ID")
    public ResponseEntity<Subscription> updateSubscription(
            @PathVariable Long id,
            @RequestBody Subscription subscription) {
        Subscription updatedSubscription = subscriptionService.updateSubscriptionStatus(id, subscription.getActive());
        return ResponseEntity.ok(updatedSubscription);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subscription by ID")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}
