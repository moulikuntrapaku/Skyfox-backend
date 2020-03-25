package com.booking.shows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/shows")
public class ShowController {
    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    public String greetings() {
        return "greetings from booking. !";
    }

    @PostMapping
    public Show book(@RequestBody Show show) {
        return showService.save(show);
    }
}
