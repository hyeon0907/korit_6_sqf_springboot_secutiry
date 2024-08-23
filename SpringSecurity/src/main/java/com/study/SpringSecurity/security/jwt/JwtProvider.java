package com.study.SpringSecurity.security.jwt;

import com.study.SpringSecurity.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
    // application에 있는 secret을 바로 사용하지 않고 decode해서 사용해야 함
    public JwtProvider(@Value("${jwt.secret}") String secret) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        // secret을 key로 만들어서 사용하게 함
    }

    public String removeBearer(String token){
        return token.substring("Bearer ".length());
    }

    // jwt: json wep token
    // String 사용이유: json웹 토큰은 전체가 문자열이기 때문에 String으로 사용
    public String generateUserToken(User user){ // 사용자의 정보로 토큰을 만듬
        Date expireDate = new Date(new Date().getTime() + (1000L * 60 * 60 * 24 * 30)); // 1초 == 1000, 1초 * 60초 * 60분 * 24시간 * 30일

        String token = Jwts.builder()
                .claim("userId", user.getId()) // map
                .expiration(expireDate) // 토큰 유혀시간 설정
                .signWith(key, SignatureAlgorithm.HS256) // 이 key를 가지고 인증
                .compact();

        return token;
    }


    // 토큰에
    public Claims parseToken(String token){
        JwtParser jwtParser = Jwts.parser()
                        .setSigningKey(key)
                                .build();

        return jwtParser.parseClaimsJws(token).getPayload();
    }

    /**
     * 세션과 토큰의 차이점
     * 세션은 사용자의 데이터를 서버가 가지고 있음
     * 토큰은 클라이언트가 데이터를 가지고 있음
     *
     * ex) 로그인 // 세션
     * 1. 서버에 요청(아이디, 비밀번호)
     * 2. 세션에 저장
     * 3. 클라이언트한테 세션아이디만 반환
     * 4. 다음에 다시 로그인할때 세션아이디로 세션을 판단해서 로그인 유무판단 가능
     *
     * ex) 로그인 // 토큰
     * 1. 서버에 요청
     * 2. 토큰을 발급(암호화된 토큰을 발급)
     * 3. 사용자한테 토큰을 반환
     * 4. 다시 로그인 할때 사용자가 가지고 있는 토큰을 받아서
     * 서버에 있는 비밀번호로 해제 시도
     * 단) 토큰안에 중요정보는 넣으면 안됨(jwt사이트에서 토큰 넣으면 안에 정보를 볼수있음)
     * 안되는 것들: 사용자 이름, 생년월일, 비밀번호 등등
     * 들어갈수 것: *userId*
     * 5. 서버에서 토큰 해체후 토큰 안에있는 userId를 사용자 리스트에서 매칭
     * 매칭 성공하면 로그인 성공된 페이지로 전환
     * 안되면 로그인 페이지로 전환
     */
}
