package com.study.SpringSecurity.repository;

import com.study.SpringSecurity.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository // ioc에 등록하려면 class여야하는데 jpa라서 인터페이스
public interface UserRepository extends JpaRepository<User, Long> {
    // findbyusername이 없어서 만들어줘야댐
    Optional<User> findByUsername(String username);
}
