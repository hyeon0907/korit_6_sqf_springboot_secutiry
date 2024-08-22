package com.study.SpringSecurity.service;

import com.study.SpringSecurity.aspect.annotation.TimeAop;
import com.study.SpringSecurity.domain.entity.Role;
import com.study.SpringSecurity.domain.entity.User;
import com.study.SpringSecurity.dto.request.ReqSignupDto;
import com.study.SpringSecurity.repository.RoleRepository;
import com.study.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // @Service는 ioc컨테이너에 등록되있어서 passwordEncoder를 사용할수 있음
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @TimeAop
    public User signup(ReqSignupDto dto) {
        User user = dto.toEntity(passwordEncoder);
        Role role = roleRepository.findByName("ROLE_USER").orElseGet(
                () -> roleRepository.save(Role.builder().name("ROLE_NAME").build())
        );
        user.setRoles(Set.of(role));
        return userRepository.save(user);
    }

    public boolean isDuplicatedUsername(String username) {
        // username을 가져옴
        // isPresent() : null인지 체크
        return userRepository.findByUsername(username).isPresent();
    }

}
