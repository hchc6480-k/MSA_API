package com.example.demo.util;

import com.example.demo.res.UserResDTO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

        final JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setHeaderParam("typ","JWT")
                .setSubject("loginToken").setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
                .setClaims(map).signWith(SignatureAlgorithm.HS256,secretKey.getBytes());

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
}
