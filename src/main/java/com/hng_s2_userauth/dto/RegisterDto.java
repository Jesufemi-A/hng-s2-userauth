package com.hng_s2_userauth.dto;




import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {



    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;


    @NotBlank
    @Email
//    @Column(unique = true)
    private String email;


    @NotBlank
    private String password;


    @NotBlank
    private String phone;
}
