package com.hng_s2_userauth.controller;


import com.hng_s2_userauth.dto.OrgResponse;
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
import org.springframework.web.bind.annotation.*;

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

        var dataDto = new OrgResponse.Data(org.getOrgId(), org.getName(), org.getDescription());
        var createOrgDto = new OrgResponse("success", "Organisation created successfully", dataDto);

        return ResponseEntity.status(201).body(createOrg);

    }


    // add user to an organisation
    @PostMapping("/organisations/{orgId}/users")
    public ResponseEntity<?> addUserToOrganisation(@PathVariable("orgId") String orgId,
                                                   @RequestBody String userId) {

        return userService.addUserToOrganisation(orgId, userId);
    }


    @GetMapping("/organisations/{orgId} ")
    public ResponseEntity<OrgResponse> getOrganisationRecord(@PathVariable("orgId") String orgId,
                                                             @AuthenticationPrincipal UserEntity user) {

        return userService.getOrganisationRecord(orgId, user);
    }
}
