package com.zmm.helmet;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.HashMap;


class HelmetApplicationTests {

    @Test
    void contextLoads() {

        HashMap<String, Object> map = new HashMap<>();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,120);
        String token = JWT.create()
//                .withHeader(map) //header
                .withClaim("user_id", 21) //payload
                .withClaim("username", "zmm")
                .withExpiresAt(instance.getTime()) //指定令牌的过期时间
                .sign(Algorithm.HMAC256("!@9870"));
        System.out.println(token);

    }

    @Test
    public void test(){
        //创建验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!@9870")).build();

        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMSwiZXhwIjoxNTk4NjcyMTAwLCJ1c2VybmFtZSI6InptbSJ9.QjQtgnijg8qqk-fSWJr0ov6pNrmVhsVP5SUTK_1ly5w");
        System.out.println(verify.getClaim("user_id").asInt());
        System.out.println(verify.getClaim("username").asString());
        System.out.println("过期时间" + verify.getExpiresAt());
    }

}
