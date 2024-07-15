package com.hng_s2_userauth.controller;


import com.hng_s2_userauth.dto.CreateOrgResponse;
import com.hng_s2_userauth.dto.CreateOrganisationDto;
import com.hng_s2_userauth.model.Organisation;
import com.hng_s2_userauth.model.UserEntity;
import com.hng_s2_userauth.repository.OrganisationRepository;
import com.hng_s2_userauth.repository.UserRepository;
import com.hng_s2_userauth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrganisationController {


    final UserRepository userRepository;
    final OrganisationRepository organisationRepository;
    final UserService userService;


    @PostMapping("/organisations")
    public ResponseEntity<CreateOrganisationDto> createOrganisation(@Valid @RequestBody CreateOrganisationDto createOrg,
                                                @AuthenticationPrincipal UserEntity user) {


        Organisation org = userService.createOrganisation(createOrg);
        org.getUsers().add(user);

        organisationRepository.save(org);


      var dataDto =  new CreateOrgResponse.Data(org.getOrgId(), org.getName(), org.getDescription());
       var createOrgDto = new CreateOrgResponse("success", "Organisation created successfully", dataDto);

        return ResponseEntity.status(201).body(createOrg);



    }
}
