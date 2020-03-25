package com.booking;

public class ShowService {
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {

        this.showRepository = showRepository;
    }

    public Show save(Show show) {
        return showRepository.save(show);
    }
}
