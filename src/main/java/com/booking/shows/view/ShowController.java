package com.booking.shows.view;

import com.booking.handlers.models.ErrorResponse;
import com.booking.shows.ShowService;
import com.booking.shows.view.models.ShowResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Shows")
@RestController
@RequestMapping("/shows")
public class ShowController {
    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    @ApiOperation(value = "Fetch all shows")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched shows successfully"),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    public List<ShowResponse> fetchAll(@Valid @RequestParam(name = "date") Date date) {
        return showService.fetchAll(date).stream().map(ShowResponse::new).collect(Collectors.toList());
    }
}
