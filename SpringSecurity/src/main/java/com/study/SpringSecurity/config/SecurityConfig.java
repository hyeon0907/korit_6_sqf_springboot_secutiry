package com.study.SpringSecurity.config;

import com.study.SpringSecurity.security.filter.JwtAccessTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// JPA
// SecurityConfigurerAdapter 추상클래스
@Configuration
// ioc 컨테이너에 bean으로 생성되어 저장댐(bean등록을 할수있음)
@EnableWebSecurity // 우리가 만든 SecurityConfig를 적용시키겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAccessTokenFilter jwtAccessTokenFilter;

    // 암호화를 하기 위한 객체 생성
    // 생성된 객체 하나가 메소드 명으로 passwordEncoder ioc 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    // protected 같은 패키지 또는 상속 자식 클래스에서 사용할수 있다
    // 같은 패키지 안에 클래스만
    // JWT 기본세팅
    protected void configure(HttpSecurity http) throws Exception {
        // 상위클래스가 가지고있는 메소드 호출
        http.formLogin().disable();
        http.httpBasic().disable();
        http.csrf().disable();
        // 위조 방지 스티커 (토큰)

        // http.sessionManagement().disable();
        // form태그로 만들어진 로그인화면이 안나옴
        // 스프링 시큐리티가 세션을 생성하지도 않고 기존의 세션을 사용하지도 않겠다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 스프링 시큐리티가 세션을 생성하지 않겠다. 기존의 세션을 완전히 사용하지 않겠다는 뜻은 아님.
        // JWT 등의 토큰 인증방식을 사용할 때 설정하는 것
        http.cors(); // 서버가 다를때 요청 a라는서버에서 b서버에만 요청가능하게 하겠다
        // crosorigin 사용

        // JWT 토큰을 사용할거기 때문에
        // csrf - 서버사이드렌더링
        http.authorizeRequests();

        http.authorizeRequests()
                // ** 전부
                .antMatchers("/auth/**", "/h2-console/**", "/test/**")
                // 인증 및 권한 검사 - 로그인 , 회원가입
                .permitAll()
                //  모든 사용자의 접근 허용
                .anyRequest() // 모든 요청
                .authenticated() // 인증없이 요청할수있게끔
                // 인증을 요구하는 보안 설정을 적용
                .and()
                .headers()
                .frameOptions() //iframe
                .disable();
        http.addFilterBefore(jwtAccessTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
