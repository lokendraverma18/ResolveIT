package com.resolveit.backend.controller;

import com.resolveit.backend.dto.UserRegistrationRequest;
import com.resolveit.backend.dto.UserResponse;
import com.resolveit.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class  UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/count")
    public Map<String, Long> getTotalUsers() {

        long totalUsers = userService.getTotalUsers();

        return Map.of("totalUsers", totalUsers);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(
            @Valid @RequestBody UserRegistrationRequest request){
        UserResponse response = userService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}

