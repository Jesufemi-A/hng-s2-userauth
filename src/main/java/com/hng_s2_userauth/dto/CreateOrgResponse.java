package com.hng_s2_userauth.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrgResponse {



    private String status;
    private String message;

    private Data data;


    public static  class Data{
            private String orgId;
            private String name;
            private String description;

    }


}
