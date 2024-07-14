package com.hng_s2_userauth.dto;




import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;
}
