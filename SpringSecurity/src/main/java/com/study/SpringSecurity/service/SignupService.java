package com.study.SpringSecurity.service;

import com.study.SpringSecurity.aspect.annotation.TimeAop;
import com.study.SpringSecurity.domain.entity.Role;
import com.study.SpringSecurity.domain.entity.User;
import com.study.SpringSecurity.dto.request.ReqSignupDto;
import com.study.SpringSecurity.repository.RoleRepository;
import com.study.SpringSecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    // @Service는 ioc컨테이너에 등록되있어서 passwordEncoder를 사용할수 있음

    @TimeAop
    @Transactional(rollbackFor = Exception.class)
    public User signup(ReqSignupDto dto) {
        User user = dto.toEntity(passwordEncoder);
        Role role = roleRepository.findByName("ROLE_USER").orElseGet(
                () -> roleRepository.save(Role.builder().name("ROLE_NAME").build())
        );
        user.setRoles(Set.of(role));
        user = userRepository.save(user);
//        UserRole userRole = UserRole.builder()
//                .user(user)
//                .role(role)
//                .build();

//        userRole = userRoleRepository.save(userRole);
        return user;
    }

    public boolean isDuplicatedUsername(String username) {
        // username을 가져옴
        // isPresent() : null인지 체크
        return userRepository.findByUsername(username).isPresent();
    }
}
