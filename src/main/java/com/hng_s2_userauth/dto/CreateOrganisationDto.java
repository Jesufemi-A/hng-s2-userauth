package com.hng_s2_userauth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateOrganisationDto {

    @NotBlank
   private  String  name;

   private String description;

}
