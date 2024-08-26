package com.study.SpringSecutiryMybatis.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoles {
    private Long id;
    private Long userId;
    private Long roleId;
}
