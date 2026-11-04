package com.resolveit.backend.controller;

import com.resolveit.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/count")
    public Map<String, Long> getTotalUsers() {

        long totalUsers = userService.getTotalUsers();

        return Map.of("totalUsers", totalUsers);
    }
}

