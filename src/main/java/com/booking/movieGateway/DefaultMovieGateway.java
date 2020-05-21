package com.booking.movieGateway;

import com.booking.movieGateway.models.Movie;
import com.booking.movieGateway.models.MovieStatus;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Primary
public class DefaultMovieGateway implements MovieGateway {

    @Override
    public Movie getMovieFromId(String id) {
        return new Movie(id, "Movie 1", Duration.ofHours(1), "Description", MovieStatus.RUNNING);
    }
}
