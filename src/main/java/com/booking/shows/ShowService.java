package com.booking.shows;

import com.booking.movieGateway.MovieGateway;
import com.booking.shows.respository.Show;
import com.booking.shows.respository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {
    private final ShowRepository showRepository;
    private final MovieGateway movieGateway;

    @Autowired
    public ShowService(ShowRepository showRepository, MovieGateway movieGateway) {
        this.showRepository = showRepository;
        this.movieGateway = movieGateway;
    }

    public List<Show> fetchAll() {
        List<Show> shows = showRepository.findAll();
        shows.forEach(show -> show.setMovieGateway(movieGateway));
        return shows;
    }
}
