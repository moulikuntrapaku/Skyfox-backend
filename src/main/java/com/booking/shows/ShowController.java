package com.booking.shows;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/shows")
public class ShowController {
    private final ShowRepository showRepository;

    public ShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @GetMapping
    public String greetings() {
        return "greetings from booking. !";
    }

    @PostMapping
    public Show book(@RequestBody Show show) {
        return showRepository.save(show);
    }
}
