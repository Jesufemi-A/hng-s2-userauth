package com.hng_s2_userauth.service;

import com.hng_s2_userauth.dto.AddUserToOrgDto;
import com.hng_s2_userauth.dto.CreateOrganisationDto;
import com.hng_s2_userauth.dto.OrgResponse;
import com.hng_s2_userauth.model.Organisation;
import com.hng_s2_userauth.model.UserEntity;
import com.hng_s2_userauth.repository.OrganisationRepository;
import com.hng_s2_userauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


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


    public ResponseEntity<?> addUserToOrganisation(String orgId, String userId) {

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

        var dataDto = new OrgResponse.Data(orgId, org.getName(), org.getDescription());
        var orgDto = new OrgResponse("success", "Organisation record retrieval successful", dataDto);


        return ResponseEntity.status(200).body(orgDto);
    }
}

