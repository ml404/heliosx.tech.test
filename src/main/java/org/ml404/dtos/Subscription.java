package org.ml404.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Schema(description = "Details about the subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "The unique ID of the subscription")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @Schema(description = "The customer associated with the subscription")
    private Customer customer;

    @Column(name = "card_details", nullable = false)
    @Schema(description = "The card details associated with the subscription. Not nullable")
    private String cardDetails;

    @Column(name = "active")
    @Schema(description = "field denoting whether the subscription is active or not")
    private Boolean active;

    @Column(name = "start_date")
    @Schema(description = "The start date associated with the subscription")
    private LocalDateTime startDate;

    // Constructors, getters, and setters

    public Subscription() {
    }

    public Subscription(Customer customer, String cardDetails, Boolean active, LocalDateTime startDate) {
        this.customer = customer;
        this.cardDetails = cardDetails;
        this.active = active;
        this.startDate = startDate;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(String cardDetails) {
        this.cardDetails = cardDetails;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}

