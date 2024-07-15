package com.hng_s2_userauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
public class UserRecordDto {



    private String status;
    private String message;
    private Data data;

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static  class Data{
        private String userId;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;


    }


}
