package com.booking.users;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/user/changePassword")
    public String changePassword(Principal principal,
                                 @RequestParam("newpassword") String newpassword,
                                 @RequestParam("oldpassword") String oldpassword){
        String username = principal.getName();
        userPrincipalService.changePassword(username,newpassword, oldpassword);
        return username;
    }


}
