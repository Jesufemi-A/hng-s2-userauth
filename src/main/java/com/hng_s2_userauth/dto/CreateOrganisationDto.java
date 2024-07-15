package com.hng_s2_userauth.dto;

import jakarta.validation.constraints.NotBlank;





public class createOrganisationDto {


    @NotBlank
   private  String  name;

   private String description;

}
