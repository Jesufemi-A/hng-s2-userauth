package com.hng_s2_userauth.controller;


import com.hng_s2_userauth.dto.OrgResponse;
import com.hng_s2_userauth.dto.CreateOrganisationDto;
import com.hng_s2_userauth.model.Organisation;
import com.hng_s2_userauth.model.UserEntity;
import com.hng_s2_userauth.repository.OrganisationRepository;
import com.hng_s2_userauth.repository.UserRepository;
import com.hng_s2_userauth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrganisationController {


    final UserRepository userRepository;
    final OrganisationRepository organisationRepository;
    final UserService userService;


    @PostMapping("/organisations")
    @Operation(description = "Endpoint to authenticate user")
    public ResponseEntity<OrgResponse> createOrganisation(@Valid @RequestBody CreateOrganisationDto createOrg,
                                                          @AuthenticationPrincipal UserEntity user) {


        Organisation org = userService.createOrganisation(createOrg);
        org.getUsers().add(user);

        for (var o : org.getUsers()){
            System.out.println("Create Org-----------------" + o.toString());
        }
        organisationRepository.save(org);
        Optional<UserEntity> loadU = userRepository.findById(user.getEmail());

        if (loadU.isPresent()) {
            UserEntity u = loadU.get();
            u.getOrganisations().add(org);
            userRepository.save(u);
        }



        var dataDto = new OrgResponse.Data(org.getOrgId(), org.getName(), org.getDescription());
        var createOrgDto = new OrgResponse("success", "Organisation created successfully", dataDto);

        return ResponseEntity.status(201).body(createOrgDto);

    }


    @GetMapping("organisations")
    @Operation(description = "gets all your organisations the user belongs to or created. If a user is logged in properly, they can get all their organisations. " +
            "They should not get another user’s organisation [PROTECTED].")
    public ResponseEntity<?> getOrganisation(@AuthenticationPrincipal UserEntity user) {
        return userService.getOrganisation(user);


    }


    // add user to an organisation
    @PostMapping("/organisations/{orgId}/users")
    @Operation(description ="adds a user to a particular organisation [PROTECTED]" )
    public ResponseEntity<?> addUserToOrganisation(@PathVariable("orgId") String orgId,
                                                   @RequestBody String userId) {

        return userService.addUserToOrganisation(orgId, userId);
    }


    @GetMapping("/organisations/{orgId}")
    @Operation(description = "The logged in user gets a single organisation record [PROTECTED]")
    public ResponseEntity<OrgResponse> getOrganisationRecord(@PathVariable("orgId") String orgId,
                                                             @AuthenticationPrincipal UserEntity user) {

        return userService.getOrganisationRecord(orgId, user);
    }
}
