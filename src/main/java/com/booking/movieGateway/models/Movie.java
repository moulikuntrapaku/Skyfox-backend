package com.booking.movieGateway.models;

import com.booking.utilities.serializers.duration.DurationSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.Duration;
import java.util.Objects;

@ApiModel(value = "Movie")
public class Movie {

    @JsonProperty
    @ApiModelProperty(name = "id", value = "The movie id", example = "title_1", position = 1)
    private final String id;

    @JsonProperty
    @ApiModelProperty(name = "name", value = "Name of the movie", required = true, example = "Movie", position = 2)
    private final String name;

    @JsonProperty
    @JsonSerialize(using = DurationSerializer.class)
    @ApiModelProperty(name = "name", dataType = "java.lang.String", value = "Duration of the movie", required = true, example = "1h 30m", position = 3)
    private final Duration duration;

    @JsonProperty
    @ApiModelProperty(name = "description", value = "Description of the movie", required = true, example = "Movie Description", position = 4)
    private final String plot;

    @JsonProperty
    @ApiModelProperty(name = "url", value = "poster url", required = true, example = "movie.com", position = 5)
    private final String url;

    public Movie(String id, String name, Duration duration, String plot, String url) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.plot = plot;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id.equals(movie.id) &&
                name.equals(movie.name) &&
                duration.equals(movie.duration) &&
                plot.equals(movie.plot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, plot);
    }
}
