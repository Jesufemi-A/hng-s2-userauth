package com.hng_s2_userauth.service;

import com.hng_s2_userauth.dto.*;
import com.hng_s2_userauth.model.Organisation;
import com.hng_s2_userauth.model.UserEntity;
import com.hng_s2_userauth.repository.OrganisationRepository;
import com.hng_s2_userauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;
    final OrganisationRepository organisationRepository;


    public Organisation createOrganisation(CreateOrganisationDto createOrg) {
        Organisation organisation = new Organisation();
        organisation.setName(createOrg.getName());
        organisation.setDescription(createOrg.getDescription());

        return organisation;
    }


    public ResponseEntity<AddUserToOrgDto> addUserToOrganisation(String orgId, String userId) {

        Organisation org = organisationRepository.findByOrgId(orgId);

        Optional<UserEntity> loadUser = userRepository.findById(userId);


        if (loadUser.isEmpty()) {
            throw new UsernameNotFoundException("user does not exist");
        }

        org.getUsers().add(loadUser.get());

        return ResponseEntity.status(200).body(new AddUserToOrgDto("success",
                "User added to organisation successfully"));

    }


    public ResponseEntity<OrgResponse> getOrganisationRecord(String orgId, UserEntity user) {

        Organisation org = organisationRepository.findByOrgId(orgId);

        if (!org.getUsers().contains(user)) {
            return null;
        }
        System.out.println("vjskndlss==============================");
        var dataDto = new OrgResponse.Data(orgId, org.getName(), org.getDescription());
        var orgDto = new OrgResponse("success", "Organisation record retrieval successful", dataDto);


        return ResponseEntity.status(200).body(orgDto);
    }


    public ResponseEntity<?> getUserRecord(String id, UserEntity user) {

        if (id.equals(user.getUserId())) {
            var datDto = new UserRecordDto.Data(user.getUserId(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), user.getPhone());

            var reg = new UserRecordDto("success", "user record retrieval successful", datDto);

            return ResponseEntity.status(200).body(reg);

        }

        Optional<UserEntity> loadUser = userRepository.findById(id);

        if (loadUser.isEmpty()) {
            return null;
        }

        UserEntity userEntity = loadUser.get();

        boolean inOrg = false;
        for (Organisation org : userEntity.getOrganisations()) {
            // Check if the user belongs to the organisation
            if (org.getUsers().stream().anyMatch(u -> u.getUserId().equals(user.getUserId()))) {
                inOrg = true;
                break;
            }
        }

        if (!inOrg) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }


        var datDto = new UserRecordDto.Data(userEntity.getUserId(), userEntity.getFirstName(), userEntity.getLastName(),
                userEntity.getEmail(), userEntity.getPhone());

        var reg = new UserRecordDto("success", "user record retrieval successful", datDto);

        return ResponseEntity.status(200).body(reg);
    }


    public ResponseEntity<?> getOrganisation(@AuthenticationPrincipal UserEntity user) {

        Optional<UserEntity> loadU = userRepository.findByEmail(user.getEmail());

        if (!loadU.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        UserEntity u = loadU.get();
        Set<Organisation> organisations = u.getOrganisations();

        for (var o : organisations){
            System.out.println(o.toString());
        }

        List<GetOrganisationDto.Data.Organisation> orgList = organisations.stream()
                .map(o -> new GetOrganisationDto.Data.Organisation(o.getOrgId(), o.getName(), o.getDescription()))
                .collect(Collectors.toList());

        for (var o : orgList){
            System.out.println(o.toString());
        }

        GetOrganisationDto.Data dataDto = new GetOrganisationDto.Data(orgList);
        GetOrganisationDto getOrgDto = new GetOrganisationDto("success", "Organisation list retrieval successful", dataDto);

        return ResponseEntity.status(HttpStatus.OK).body(getOrgDto);

    }
}
