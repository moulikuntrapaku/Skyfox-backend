package com.booking.users;

import com.booking.validations.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
@DynamicInsert(value = true)
@Entity
@Table(name = "usertable")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonProperty
    @NotBlank(message = "User name must be provided")
    @Column(name= "USERNAME", nullable = false, unique = true)
    @ApiModelProperty(name = "username", value = "Name of user (must be unique)", required = true, example = "user_name", position = 1)
    private String username;

    @JsonProperty
    @ValidPassword(message = "Password must be valid")
    @Column(nullable = false)
    @ApiModelProperty(name = "password", value = "Password of the user", required = true, example = "password", position = 2)
    private String password;


    @JsonProperty
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'CUSTOMER'")
    private String role;

    public List<PasswordHistory> getPasswordHistories() {
        return passwordHistories;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "USERID", referencedColumnName = "id")
    private List<PasswordHistory> passwordHistories;


    public User() {
    }

    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, List<PasswordHistory> passwordHistories) {
        this.username = username;
        this.password = password;
        this.passwordHistories = passwordHistories;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    public String getRole() {
        return role;
    }
}
