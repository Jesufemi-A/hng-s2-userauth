package com.hng_s2_userauth.controller;


import com.hng_s2_userauth.dto.LoginDto;
import com.hng_s2_userauth.dto.RegisterDto;
import com.hng_s2_userauth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    final AuthService authService;


    @PostMapping("/register")
    @Operation(description = "Registers a user and creates a default organisation")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerdto) {

        return authService.register(registerdto);
    }

    @PostMapping("/login")
    @Operation(description = "Endpoint to authenticate user")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        return authService.login(loginDto);
    }
}
