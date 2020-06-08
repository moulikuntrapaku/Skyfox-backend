package com.booking.shows.respository;

import com.booking.slots.repository.Slot;
import com.booking.utilities.serializers.date.DateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "show")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    @ApiModelProperty(name = "id", value = "The show id", example = "0", position = 1)
    protected Long id;

    @Column(nullable = false)
    @JsonProperty
    @JsonSerialize(using = DateSerializer.class)
    @NotNull(message = "Date must be provided")
    @ApiModelProperty(name = "date", value = "Date of the show (yyyy-MM-dd)", dataType = "java.lang.String", required = true, example = "2020-01-01", position = 2)
    protected Date date;

    @OneToOne
    @JoinColumn(name = "slot_id")
    @JsonIgnore
    protected Slot slot;

    @Column(nullable = false)
    @JsonProperty
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost should be greater than {value}")
    @Digits(integer = 4, fraction = 2, message = "Cost can have at most {integer} integral digits, and {fraction} fractional digits")
    @ApiModelProperty(name = "cost", value = "Cost of the show", required = true, example = "249.99", position = 3)
    protected BigDecimal cost;

    @Column(nullable = false)
    @NotBlank(message = "Movie id has to be provided")
    @JsonIgnore
    protected String movieId;

    public Show() {
    }

    public Show(Date date, Slot slot, BigDecimal cost, String movieId) {
        this.date = date;
        this.slot = slot;
        this.cost = cost;
        this.movieId = movieId;
    }

    public Show(Show show) {
        this.id = show.id;
        this.movieId = show.movieId;
        this.date = show.date;
        this.slot = show.slot;
        this.cost = show.cost;
    }

    public Long getId() {
        return id;
    }

    public Slot getSlot() {
        return slot;
    }

    public String getMovieId() {
        return movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return Objects.equals(id, show.id) &&
                Objects.equals(date, show.date) &&
                Objects.equals(slot, show.slot) &&
                Objects.equals(cost, show.cost) &&
                Objects.equals(movieId, show.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, slot, cost, movieId);
    }

    public BigDecimal costFor(int noOfSeats) {
        return cost.multiply(BigDecimal.valueOf(noOfSeats));
    }
}
