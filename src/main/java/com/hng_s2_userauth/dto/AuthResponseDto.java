package com.hng_s2_userauth.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AuthResponseDto {

    private String status;
    private String message;
    private int statusCode;

}
