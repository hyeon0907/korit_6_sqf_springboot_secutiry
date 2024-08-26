package com.study.SpringSecutiryMybatis.repository;

import com.study.SpringSecutiryMybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUserName(String username);
    int save(User user);
}
