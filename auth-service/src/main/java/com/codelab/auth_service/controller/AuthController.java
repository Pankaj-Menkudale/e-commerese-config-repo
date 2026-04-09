package com.codelab.auth_service.controller;

import com.codelab.auth_service.dto.AuthResponse;
import com.codelab.auth_service.dto.LoginRequest;
import com.codelab.auth_service.dto.RegisterRequest;
import com.codelab.auth_service.service.AutheService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AutheService autheService;


    @GetMapping("/test")
    public String test() {
        System.out.println("Test endpoint hit!"); // Will log in console
        return "Controller is working!";
    }

    @PostMapping("/register")
    public ResponseEntity<String>register(@Valid @RequestBody RegisterRequest request){

        return ResponseEntity.ok(autheService.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@RequestBody LoginRequest request){

        return ResponseEntity.ok(autheService.login(request));
    }
}
