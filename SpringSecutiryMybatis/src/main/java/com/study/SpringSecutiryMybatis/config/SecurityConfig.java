package com.study.SpringSecutiryMybatis.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable(); // form로그인 사용하지 않음
        http.httpBasic().disable(); // Basic로그인 사용하지 않음
        http.csrf().disable(); // csrf토큰을 검증하지 않음
        http.headers().frameOptions().disable(); // iframe비활성화(h2-console이 안뜸)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //기존 세션과 새로운 세션을 만들지 않고사용하지 않겠다
        http.cors(); // 크로스 오리진 설정(다른 도메인엥서도 접근 허용)
        http.authorizeRequests()
                .antMatchers("/auth/**", "/h2-console/**")
                .permitAll() // 위 설정 경로는 인증없이 접근을 허용함
                .anyRequest()
                .authenticated(); // 그 외의 모든 요청은 인증이 필요함
    }
}
