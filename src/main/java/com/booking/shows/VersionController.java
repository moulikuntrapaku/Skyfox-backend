package com.booking.shows;

import com.jcabi.manifests.Manifests;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class VersionController {

    @GetMapping("/version")
    public String getVersion() throws IOException {
        return Manifests.read("Booking-Implementation-Version");
    }
}
