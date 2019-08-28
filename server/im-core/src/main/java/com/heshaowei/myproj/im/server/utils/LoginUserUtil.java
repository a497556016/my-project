package com.heshaowei.myproj.im.server.utils;

import javax.servlet.http.HttpServletRequest;

public class LoginUserUtil {
    public static String getUsername(HttpServletRequest request){
        String token = request.getHeader("token");
        if(null != token) {
            try {
                return JwtUtils.parseJWT(token).getSubject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
