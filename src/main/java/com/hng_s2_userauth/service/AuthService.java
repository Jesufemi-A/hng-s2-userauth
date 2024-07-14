package com.hng_s2_userauth.service;


import com.hng_s2_userauth.dto.RegisterDto;
import com.hng_s2_userauth.dto.RegisterUserSuccessDto;
import com.hng_s2_userauth.model.UserEntity;
import com.hng_s2_userauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;
    final JwtService jwtService;


    public ResponseEntity<?> register(RegisterDto registerDto) {

        UserEntity user = mapUser(registerDto);
       String hashPwd = passwordEncoder.encode(user.getPassword());
       user.setPhone(hashPwd);

       userRepository.save(user);

     String token =  jwtService.generateToken(user);

      var userdto =  new RegisterUserSuccessDto.Data.User();
     user.setUserId(user.getUserId());
      userdto.setPhone(user.getPhone());
      userdto.setEmail(user.getEmail());
      userdto.setLastName(user.getLastName());
      userdto.setFirstName(user.getFirstName());


      var datadto = new RegisterUserSuccessDto.Data();
      datadto.setAccessToken(token);
      datadto.setUser(userdto);

     var reg = new RegisterUserSuccessDto("success", "Registration succesful", datadto);

      return ResponseEntity.status(201).body(reg);




    }



    public UserEntity mapUser(RegisterDto registerDto) {
       UserEntity user =  new UserEntity();
       user.setFirstName(registerDto.getFirstName());
       user.setLastName(registerDto.getLastName());
       user.setEmail(registerDto.getEmail());
       user.setPassword(registerDto.getPassword());
       user.setPhone(registerDto.getPhone());

       return user;
    }
}
