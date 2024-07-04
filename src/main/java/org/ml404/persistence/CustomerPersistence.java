package org.ml404.persistence;

import org.ml404.dtos.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerPersistence extends JpaRepository<Customer, Long> {
}
