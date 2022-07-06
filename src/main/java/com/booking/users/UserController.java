package com.booking.users;

import com.booking.exceptions.OldThreePasswordMatchException;
import com.booking.exceptions.OldPasswordIncorrectException;
import com.booking.exceptions.UserNameNotFoundException;
import com.booking.handlers.models.ErrorResponse;
import com.booking.validations.ValidPassword;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Validated
@Api(tags = "Users")
@RestController
public class UserController {
    UserPrincipalService userPrincipalService;
    PasswordHistoryRepository passwordHistoryRepository;

    public UserController(UserPrincipalService userPrincipalService, PasswordHistoryRepository passwordHistoryRepository) {
        this.userPrincipalService = userPrincipalService;
        this.passwordHistoryRepository = passwordHistoryRepository;
    }


    @GetMapping("/login")
    ResponseEntity<String> login(Principal principal) {
        String username = principal.getName();
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", username);
        return ResponseEntity.ok("Login successful!!" + userDetails);
    }
    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<User> role(Principal principal) {
        String username = principal.getName();
        User res = userPrincipalService.findUserByUsername(username);

        return ResponseEntity.ok(res);
    }

    @RequestMapping(value = "/user/password", method = RequestMethod.PUT)
    @ApiOperation(value = "Change password")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Password has been changed"),
            @ApiResponse(code = 404, message = "Record not found", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "Server cannot process request due to client error", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    public  ResponseEntity<String> changePassword(@RequestBody UserDTO userDTO) throws UserNameNotFoundException,OldPasswordIncorrectException, OldThreePasswordMatchException {
        userPrincipalService.changePassword(userDTO);
        return ResponseEntity.ok("Success! Login with new password ");
    }

   @RequestMapping(value = "/lists", method = RequestMethod.GET)
    List<PasswordHistory> getPass(Principal principal){
        String username = principal.getName();
        return userPrincipalService.findPassHisById(username);
    }


}
