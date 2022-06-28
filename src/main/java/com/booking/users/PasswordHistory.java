package com.booking.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "passwordhistory")
public class PasswordHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JsonProperty
    @Column(name = "USERID")
    private Long userId;

    @JsonProperty
    @Column(name = "PASSWORD")
    @ApiModelProperty(name = "password", value = "Password of the user", required = true, example = "password", position = 2)
    private String password;


    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    private Timestamp entrytime;

    public Long getId() {
        return id;
    }

    public Timestamp getEntrytime() {
        return entrytime;
    }

    public PasswordHistory(Long userid, String password) {
        this.userId = userid;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}