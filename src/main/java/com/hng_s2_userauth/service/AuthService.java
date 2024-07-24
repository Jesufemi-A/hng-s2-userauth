package com.hng_s2_userauth.service;


import com.hng_s2_userauth.exceptions.EmailAlreadyExistException;
import com.hng_s2_userauth.dto.LoginDto;
import com.hng_s2_userauth.dto.RegisterDto;
import com.hng_s2_userauth.dto.RegisterUserSuccessDto;
import com.hng_s2_userauth.exceptions.InvalidCredentialException;
import com.hng_s2_userauth.exceptions.RegistrationUnsuccessfulException;
import com.hng_s2_userauth.model.Organisation;
import com.hng_s2_userauth.model.UserEntity;
import com.hng_s2_userauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;
    final JwtService jwtService;


    public ResponseEntity<?> register(RegisterDto registerDto) {

        try {
            if (userRepository.existsByEmail(registerDto.getEmail())) {
                throw new EmailAlreadyExistException("must be unique and must not be null");
            }

            UserEntity user = mapUser(registerDto);
            String hashPwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPwd);
            user.getOrganisations().add(createOrganisation(user.getFirstName()));

            userRepository.save(user);

            String token = jwtService.generateToken(user);

            var userdto = new RegisterUserSuccessDto.Data.User();
            userdto.setUserId(user.getUserId());

            userdto.setPhone(user.getPhone());
            userdto.setEmail(user.getEmail());
            userdto.setLastName(user.getLastName());
            userdto.setFirstName(user.getFirstName());


            var datadto = new RegisterUserSuccessDto.Data();
            datadto.setAccessToken(token);
            datadto.setUser(userdto);

            var reg = new RegisterUserSuccessDto("success", "Registration succesful", datadto);

            return ResponseEntity.status(201).body(reg);
        } catch (RegistrationUnsuccessfulException err) {
            throw new RegistrationUnsuccessfulException("Registration unsuccessful");
        }

    }


    public ResponseEntity<?> login(LoginDto loginDto) {

        Optional<UserEntity> loadUser = userRepository.findByEmail(loginDto.getEmail());

        if (loadUser.isEmpty()) {
            throw new InvalidCredentialException("Authentication failed");
        }

        UserEntity user = loadUser.get();


        boolean matches = passwordEncoder.matches(loginDto.getPassword(), user.getPassword());

        if (!matches) {
            throw new InvalidCredentialException("Authentication failed");
        }


        String token = jwtService.generateToken(user);

        var userdto = new RegisterUserSuccessDto.Data.User();
        userdto.setUserId(user.getUserId());

        userdto.setPhone(user.getPhone());
        userdto.setEmail(user.getEmail());
        userdto.setLastName(user.getLastName());
        userdto.setFirstName(user.getFirstName());


        var datadto = new RegisterUserSuccessDto.Data();
        datadto.setAccessToken(token);
        datadto.setUser(userdto);

        var reg = new RegisterUserSuccessDto("success", "Login succesful", datadto);

        return ResponseEntity.status(200).body(reg);


    }


    public UserEntity mapUser(RegisterDto registerDto) {
        UserEntity user = new UserEntity();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setPhone(registerDto.getPhone());

        return user;
    }


    public Organisation createOrganisation(String name) {
        Organisation org = new Organisation();
        org.setName(name + "'s " + "Organisation");
        return org;
    }
}
