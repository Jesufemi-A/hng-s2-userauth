package com.hng_s2_userauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class GetOrganisationDto {




    private String status;
    private String message;

    private Data data;

    @lombok.Data
    @AllArgsConstructor
    public  static class Data {
        private List<Organisation> organisations;

        @lombok.Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static  class Organisation{
            private String orgId;
            private String name;
            private String description;

        }
    }



}
