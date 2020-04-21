package com.booking.shows.helpers;

import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class ShowApiHelper {

    public static ResultMatcher isEmptyBody() {
        return content().bytes(new byte[0]);
    }
}
