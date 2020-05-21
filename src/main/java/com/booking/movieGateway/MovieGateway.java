package com.booking.movieGateway;

import com.booking.movieGateway.models.Movie;
import org.springframework.stereotype.Component;

@Component
public interface MovieGateway {

    Movie getMovieFromId(String id);
}
