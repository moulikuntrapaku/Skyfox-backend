package com.booking.customers;

import com.booking.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Table(name = "customer")
public class Customer {


    public Customer(String name, String phoneNumber, String email, User user) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.user = user;

    }

    public void setId(Long id) {
        this.id = id;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @ApiModelProperty(name = "id", value = "The customer id", example = "0", position = 1)
    private Long id;

    @Column(nullable = false)
    @JsonProperty
    @NotBlank(message = "Customer name must be provided")
    @ApiModelProperty(name = "customer name", value = "Name of customer", required = true, example = "Customer name", position = 2)
    private String name;


    @Column(name = "phone_number", nullable = false)
    @JsonProperty
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must have exactly 10 digits")
    @NotBlank(message = "Phone number must be provided")
    @ApiModelProperty(name = "phone number", value = "Phone number of the customer", required = true, example = "9933221100", position = 3)
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    @JsonProperty
    @Pattern(regexp = "^(.+)@(\\S+)$", message = "Email id should be valid")
    @ApiModelProperty(name = "customer email", value = "Email of customer", required = true, example = "alex@gmail.com", position = 4)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public User getUser() {
        return user;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }


    public Customer() {

    }

    public Customer(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(phoneNumber, customer.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneNumber);
    }
}
