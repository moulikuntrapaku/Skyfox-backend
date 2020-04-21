package com.booking.shows;

import com.booking.validators.enumNamePattern.ValueOfEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "shows")
@ApiModel(value = "Show")
public class Show {

    @JsonProperty
    @ApiModelProperty(name = "id", value = "The show id", example = "0", position = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty
    @NotBlank(message = "Show name must be provided")
    @Size(min = 1, max = 30, message = "The show name must be {min} to {max} characters in length.")
    @ApiModelProperty(name = "name", value = "Name of the show (Between 1 and 30 characters)", required = true, example = "Movie", position = 2)
    private String name;

    @JsonProperty
    @NotBlank(message = "Show description must be provided")
    @Size(min = 5, max = 200, message = "The description must be {min} to {max} characters in length.")
    @ApiModelProperty(name = "description", value = "Description of the show (Between 5 and 200 characters)", required = true, example = "Movie Description", position = 3)
    private String description;

    @JsonProperty
    @DecimalMin(value = "0.1", message = "Price must at least be {value}")
    @ApiModelProperty(name = "price", value = "Price of the show (At least 0.1)", required = true, example = "100", position = 4)
    private double price;

    @JsonProperty
    @NotNull(message = "Show status must be provided")
    @ValueOfEnum(enumClass = ShowStatus.class)
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(name = "status", value = "Status of the show", required = true, example = "RUNNING", position = 5)
    private ShowStatus status;

    public Show() {
    }

    public Show(String name, String description, double price, ShowStatus status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public ShowStatus getStatus() {
        return status;
    }

    public void setStatus(ShowStatus status) {
        this.status = status;
    }

    public Show withId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return Double.compare(show.price, price) == 0 &&
                Objects.equals(id, show.id) &&
                Objects.equals(name, show.name) &&
                Objects.equals(description, show.description) &&
                Objects.equals(status, show.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price);
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
