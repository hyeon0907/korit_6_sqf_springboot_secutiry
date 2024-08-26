package com.study.SpringSecutiryMybatis.service;

import com.study.SpringSecutiryMybatis.dto.request.ReqSignupDto;
import com.study.SpringSecutiryMybatis.dto.response.RespSinupDto;
import com.study.SpringSecutiryMybatis.entity.Role;
import com.study.SpringSecutiryMybatis.entity.User;
import com.study.SpringSecutiryMybatis.entity.UserRoles;
import com.study.SpringSecutiryMybatis.repository.RoleMapper;
import com.study.SpringSecutiryMybatis.repository.UserMapper;
import com.study.SpringSecutiryMybatis.repository.UserRolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRolesMapper userRolesMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Boolean isDuplicateUsername(String username){
        return Optional.ofNullable(userMapper.findByUserName(username)).isPresent(); // username이 빈값인지 체크
    }

    public RespSinupDto insertUserAndUserRoles(ReqSignupDto dto){
        User user = dto.toEntity(passwordEncoder);
        userMapper.save(user);

        Role role = roleMapper.findByName("ROLE_USER");
        if(role == null){
            role = Role.builder().name("ROLE_USER").build();
            roleMapper.save(role);
        }
        UserRoles userRoles = UserRoles.builder()
                .userId(user.getId())
                .roleId(role.getId())
                .build();
        userRolesMapper.save(userRoles);

        user.setUserRoles(Set.of(userRoles));
        return RespSinupDto.builder()
                .message("회원가입 완료")
                .user(user)
                .build();
    }
}
