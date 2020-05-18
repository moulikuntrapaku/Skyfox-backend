package com.booking.shows;

import com.booking.handlers.models.ErrorResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Shows")
@RestController
@RequestMapping("/shows")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public List<Show> fetchAll() {
        return showService.fetchAll();
    }

    @PostMapping
    @ApiOperation(value = "Create a show")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Show created successfully"),
            @ApiResponse(code = 400, message = "Request isn't valid", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    public Show book(@Valid @RequestBody @ApiParam(value = "The show payload") Show show) {
        return showService.save(show);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete a show")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Show deleted successfully"),
            @ApiResponse(code = 400, message = "Request isn't valid", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Show not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    public void delete(@PathVariable("id") @ApiParam(value = "The show id") long id) {
        showService.delete(id);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Update a show")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Show updated successfully"),
            @ApiResponse(code = 400, message = "Request isn't valid", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Show not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    public Show update(@PathVariable("id") @ApiParam(value = "The show id") long id,
                       @Valid @RequestBody @ApiParam(value = "The updated show payload") Show updatedShow) {
        return showService.update(id, updatedShow);
    }
}
