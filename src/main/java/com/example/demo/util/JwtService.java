package com.example.demo.util;

import com.example.demo.res.UserResDTO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

    @Value("${jwt.salt}")
    private String secretKey;

    @Value("${jwt.expireMin}")
    private Long expireMin;

    public String setAuthToken(UserResDTO user){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",user.getUserId());
        map.put("passWord",user.getPassWord());

        Date now = new Date();
        final JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setHeaderParam("typ","JWT")
                .setClaims(map)
                //.setSubject(user.getUserId())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 30 * 60 * 1000L)) // 30분
                .signWith(SignatureAlgorithm.HS256,secretKey.getBytes())
                .compact();

        String jwt = jwtBuilder.compact();
        System.out.println("token:"+jwt);

        return jwt;
    }

    public boolean checkJwt(String jwt){
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwt);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public Claims getJwtBody(String jwt) throws ExpiredJwtException {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwt).getBody();
    }

    // 토큰 만료 확인
    public Boolean getExpToken(String jwt){
        try {
            Claims claims = getJwtBody(jwt);

            Date expiration = claims.getExpiration();
            Date now = new Date();
            System.out.println(expiration);
            System.out.println(now);

            if(expiration.after(now)) return true;
            return false;

        }catch (Exception e){
            return false;
        }
    }

    public Map<String,Object> getUserInfo(String jwt){
        Claims claims = getJwtBody(jwt);
        Map<String, Object> value = claims;

        return value;
    }

}
