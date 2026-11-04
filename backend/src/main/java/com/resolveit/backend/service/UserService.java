package com.resolveit.backend.service;

import com.resolveit.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long getTotalUsers() {
        return userRepository.count();
    }
}
