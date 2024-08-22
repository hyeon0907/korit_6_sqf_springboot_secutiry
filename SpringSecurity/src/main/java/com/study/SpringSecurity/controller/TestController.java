package com.study.SpringSecurity.controller;

import com.study.SpringSecurity.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public ResponseEntity<?> get() {
        System.out.println(testService.aopTest()); // pointcut에 등록하면 -> 이 메소드 호출 시 TestAspect에 around가 실행
        testService.aopTest2("김병규" , 28);
        testService.aopTest3("010-9988-1916" , "부산 동래구");

        return ResponseEntity.ok("확인");

    }
}
