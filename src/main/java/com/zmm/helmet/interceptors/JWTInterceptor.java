package com.zmm.helmet.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zmm.helmet.utils.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取 请求头Header 中的令牌
        String token = request.getHeader("token");

        Map<String,Object> map = new HashMap<>();

        try {
            JWTUtils.verify(token);
            return true;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg","无效签名！");
        }catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("msg","token过期！");
        }catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("msg","token算法不一致！");
        }catch (Exception e) {
            e.printStackTrace();
            map.put("msg","token无效！！");
        }
        map.put("code",400);
        //将map转为json jackson
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);

        return false;
    }
}
