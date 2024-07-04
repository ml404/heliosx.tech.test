package org.ml404.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "Details about the customer")
public class Customer {

    public Customer(){}
    public Customer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique ID of the customer")
    private Long id;

    @Column(name = "name")
    @Schema(description = "The name of the customer")
    private String name;

    @Column(name = "email")
    @Schema(description = "The email of the customer")
    private String email;

    @Column(name = "password")
    @Schema(description = "The password of the customer")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
