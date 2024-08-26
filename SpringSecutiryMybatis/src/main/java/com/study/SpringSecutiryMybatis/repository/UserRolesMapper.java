package com.study.SpringSecutiryMybatis.repository;

import com.study.SpringSecutiryMybatis.entity.UserRoles;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRolesMapper {
    int save(UserRoles userRoles);
}
