package com.booking.users;

import com.booking.exceptions.PasswordMismatchException;
import com.booking.handlers.models.ErrorResponse;
import com.booking.validations.ValidPassword;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "Users")
@RestController
public class UserController {

    UserPrincipalService userPrincipalService;

    public UserController(UserPrincipalService userPrincipalService) {
        this.userPrincipalService = userPrincipalService;
    }

    @GetMapping("/login")
    Map<String, Object> login(Principal principal) {
        String username = principal.getName();
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", username);
        return userDetails;
    }

    @PostMapping(value = "/user/changePassword")
    @ApiOperation(value = "Change password")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Password has been changed"),
            @ApiResponse(code = 404, message = "Record not found", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Server cannot process request due to client error", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    public String changePassword(Principal principal,
                                 @ValidPassword @RequestParam("newpassword") String newpassword,
                                 @RequestParam("oldpassword") String oldpassword) throws PasswordMismatchException {
        String username = principal.getName();
        userPrincipalService.changePassword(username,newpassword, oldpassword);
        return username;
    }






}
