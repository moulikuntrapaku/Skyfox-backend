package com.booking;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
@Component
public class BookingController {

    @GetMapping
    public String greetings() {
        return "greetings from booking. !";
    }
}
