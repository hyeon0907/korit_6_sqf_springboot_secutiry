package com.study.SpringSecutiryMybatis.dto.response;

import com.study.SpringSecutiryMybatis.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
@Builder
public class RespSinupDto {
    private String message;
    private User user;
}
