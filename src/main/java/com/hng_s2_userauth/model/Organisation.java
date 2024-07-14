package com.hng_s2_userauth.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "organisations")
public class Organisation {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orgId;


    @Column(nullable = false)
    private String name;
    private String description;


    @ManyToMany(mappedBy = "organisations")
    Set<UserEntity> users = new HashSet<>();
}
