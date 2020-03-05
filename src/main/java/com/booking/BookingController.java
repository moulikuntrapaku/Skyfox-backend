package com.booking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class BookingController {

    @GetMapping
    public String greetings() {
        return "greetings from com.booking. !";
    }
}
