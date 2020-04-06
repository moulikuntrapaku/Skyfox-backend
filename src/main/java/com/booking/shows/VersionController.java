package com.booking.shows;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

@RestController
public class VersionController {

    @GetMapping("/version")
    public HashMap<String, String> getVersion() throws IOException {
        HashMap<String, String> result = new HashMap<>();
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("/META-INF/MANIFEST.MF");
        Manifest manifest = new Manifest(resourceAsStream);
        Attributes attr = manifest.getMainAttributes();

        result.put("version", attr.getValue("Implementation-Version"));

        return result;
    }
}
