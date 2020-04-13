package com.booking.shows;

import com.jcabi.manifests.Manifests;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    @GetMapping("/version")
    public String getVersion() {
        return Manifests.read("Booking-Implementation-Version");
    }
}
