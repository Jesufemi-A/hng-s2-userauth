package com.hng_s2_userauth.repository;

import com.hng_s2_userauth.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, String> {

    Organisation findByOrgId(String orgId);
}
