package com.study.SpringSecurity.controller;

import com.study.SpringSecurity.aspect.annotation.ParamsAop;
import com.study.SpringSecurity.aspect.annotation.ValidAop;
import com.study.SpringSecurity.dto.request.ReqSigninDto;
import com.study.SpringSecurity.dto.request.ReqSignupDto;
import com.study.SpringSecurity.service.SigninService;
import com.study.SpringSecurity.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    // 어노테이션은 순서 상관 없음

    @Autowired
    private SignupService signupService;

    @Autowired
    private SigninService signinService;

    // 순서를 정하려면 order를 정해줘야함
    // 인증에 관련된 요청은 다 이쪽으로 들어옴
    @ValidAop
    @ParamsAop // 커스텀한 annotation
    @PostMapping("/signup")
    // @Valid  BindingResult 한 쌍임
    //  dto가들어오면 ReqSignupDto @Valid 가 에러가 있으면 필드에러를 생성시킴 (생성된 객체가 BeanPropertyBindingResult(빈 객체)에 담음)
    // 서블릿디스패처가 resp ,req 가지고옴 보고 매개변수가 머가필요한지 체크함
    // json이 dto를 만들어줌
    public ResponseEntity<?> signup(@Valid @RequestBody ReqSignupDto dto , BeanPropertyBindingResult bindingResult) {
        // boolean isDuplicate = signupService.isDuplicatedUsername(dto.getUsername());// true / false를 가져옴
        return ResponseEntity.created(null).body(signupService.signup(dto));
    }

    @ValidAop
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody ReqSigninDto dto, BeanPropertyBindingResult bindingResult) {
        return ResponseEntity.ok().body(signinService.signin(dto));
    }
}
