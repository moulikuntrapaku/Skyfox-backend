package com.booking.shows;

import org.springframework.dao.EmptyResultDataAccessException;
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

    public void delete(long id) {
        showRepository.deleteById(id);
    }

    public Show update(long id, Show updatedShow) {
        if (!showRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Show with id=" + id + " not found", 1);
        }

        return showRepository.save(updatedShow.withId(id));
    }
}
