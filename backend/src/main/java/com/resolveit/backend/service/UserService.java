package com.resolveit.backend.service;

import com.resolveit.backend.dto.UserRegistrationRequest;
import com.resolveit.backend.dto.UserResponse;
import com.resolveit.backend.entity.User;
import com.resolveit.backend.enums.UserRole;
import com.resolveit.backend.exception.EmailAlreadyExistsException;
import com.resolveit.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public long getTotalUsers() {
        return userRepository.count();
    }

    public UserResponse registerUser(UserRegistrationRequest request){
        String email = request.getEmail().trim()
                .toLowerCase();

        String fullName = request.getFullName().trim();

        //Check Duplicate email
        if(userRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException(
                    "An account with this email already exists"
            );
        }

        //BCrypt supports a maximum of 72 UTF -8 bytes
        int passwordBytes = request.getPassword()
                .getBytes(StandardCharsets.UTF_8).length;

        if(passwordBytes>72){
            throw new IllegalArgumentException(
                    "Password must not exceed 72 UTF-8 bytes"
            );
        }
        //Hash the password
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        //Create User Entity

        User user = new User();

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPasswordHash(hashedPassword);
        user.setRole(UserRole.EMPLOYEE);
        user.setActive(true);

        //Save User
        User savedUser = userRepository.save(user);

        // Return safe response
        return  new UserResponse(savedUser.getId(), savedUser.getFullName(),
                savedUser.getEmail(), savedUser.getRole(), savedUser.isActive(),
                savedUser.getCreatedAt());
    }
}
