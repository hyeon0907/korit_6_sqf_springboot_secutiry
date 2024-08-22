package com.study.SpringSecurity.init;

import com.study.SpringSecurity.domain.entity.Role;
import com.study.SpringSecurity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;

@Component
public class RoleDataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.findByName("ROLE_USER").isEmpty()){
            roleRepository.save(Role.builder().name("ROLE_USER").build());
        }
        if(roleRepository.findByName("ROLE_MANAGER").isEmpty()){
            roleRepository.save(Role.builder().name("ROLE_MANAGER").build());
        }
        if(roleRepository.findByName("ROLE_ADMIN").isEmpty()){
            roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
        }
    }
}
