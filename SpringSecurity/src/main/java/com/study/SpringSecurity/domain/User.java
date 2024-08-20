package com.study.SpringSecurity.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
}
