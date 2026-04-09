package com.codelab.auth_service.serviceImpl;

import com.codelab.auth_service.dto.AuthResponse;
import com.codelab.auth_service.dto.LoginRequest;
import com.codelab.auth_service.dto.RegisterRequest;
import com.codelab.auth_service.entity.User;
import com.codelab.auth_service.repository.UserRepository;
import com.codelab.auth_service.security.JwtUtil;
import com.codelab.auth_service.service.AutheService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AutheService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);

    @Override
    public String register(RegisterRequest request) {
        logger.info("Registering user with email: {}", request.getEmail());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        try {
            repo.save(user);
            logger.info("User registered successfully with email: {}", request.getEmail());
        } catch (Exception e) {
            logger.error("Error registering user with email {}: {}", request.getEmail(), e.getMessage(), e);
            throw e;
        }

        return "User Registered Successfully";
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());
        User user;

        try {
            user = repo.findByEmail(request.getEmail())
                    .orElseThrow(() -> {
                        logger.warn("Login failed: User not found with email {}", request.getEmail());
                        return new RuntimeException("User not found");
                    });

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                logger.warn("Login failed: Invalid credentials for email {}", request.getEmail());
                throw new RuntimeException("Invalid credentials");
            }

            String token = jwtUtil.generateToken(user.getEmail());
            logger.info("Login successful for email: {}", request.getEmail());
            return new AuthResponse(token);
        } catch (Exception e) {
            logger.error("Login error for email {}: {}", request.getEmail(), e.getMessage(), e);
            throw e;
        }
    }
}





//package com.codelab.auth_service.serviceImpl;
//
//import com.codelab.auth_service.dto.AuthResponse;
//import com.codelab.auth_service.dto.LoginRequest;
//import com.codelab.auth_service.dto.RegisterRequest;
//import com.codelab.auth_service.entity.User;
//import com.codelab.auth_service.repository.UserRepository;
//import com.codelab.auth_service.security.JwtUtil;
//import com.codelab.auth_service.service.AutheService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthServiceImpl implements AutheService {
//
//    private final UserRepository repo;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//
////    public AuthServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
////        this.repo = repo;
////        this.passwordEncoder = passwordEncoder;
////        this.jwtUtil = jwtUtil;
////    }
//
//    @Override
//    public String register(RegisterRequest request) {
//        User user = new User();
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRole("USER");
//
//        repo.save(user);
//
//        return "User Registered Successfully";
//    }
//
//    @Override
//    public AuthResponse login(LoginRequest request) {
//        User user = repo.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        String token = jwtUtil.generateToken(user.getEmail());
//
//        return new AuthResponse(token);
//    }
//}
