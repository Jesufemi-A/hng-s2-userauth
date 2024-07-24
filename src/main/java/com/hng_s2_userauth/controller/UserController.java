package com.hng_s2_userauth.controller;


import com.hng_s2_userauth.model.UserEntity;
import com.hng_s2_userauth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {


    final UserService userService;


    @GetMapping("/user/{id}")
    @Operation(description = "user gets their own record or user record in organisations they belong to or created [PROTECTED]")
    public ResponseEntity<?> getUserRecord(@PathVariable("id") String id, @AuthenticationPrincipal UserEntity user) {

        return userService.getUserRecord(id, user);

    }


}

