package com.booking.shows.view.models;

import com.booking.movieGateway.exceptions.FormatException;
import com.booking.movieGateway.models.Movie;
import com.booking.shows.respository.Show;
import com.booking.slots.repository.Slot;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.IOException;

@ApiModel("Show Response")
public class ShowResponse extends Show {
    public ShowResponse(Show show) {
        super(show);
    }

    @JsonProperty("movie")
    @ApiModelProperty(required = true, position = 4)
    public Movie getMovie() throws IOException, FormatException {
        return movieGateway.getMovieFromId(movieId);
    }

    @JsonProperty("slot")
    @ApiModelProperty(required = true, position = 5)
    public Slot getSlot() {
        return slot;
    }
}
