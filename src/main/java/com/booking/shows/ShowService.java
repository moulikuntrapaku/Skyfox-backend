package com.booking.shows;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {

        this.showRepository = showRepository;
    }

    public Show save(Show show) {
        return showRepository.save(show);
    }

    public List<Show> fetchAll() {
        return showRepository.findAll();
    }
}
