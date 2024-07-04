package org.ml404.persistence;

import org.ml404.dtos.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPersistence extends JpaRepository<Subscription, Long> {
}
