package com.heshaowei.myproj.utils.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtil {

    // 过期时间5分钟
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static void verify(String token, String username, String secret) throws InvalidClaimException, TokenExpiredException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
        } catch (InvalidClaimException exception) {
            throw new InvalidClaimException("非法的Token值！");
        } catch (TokenExpiredException exception) {
            throw new TokenExpiredException("Token已过期！");
        } catch (Exception exception) {
            throw new RuntimeException("Token验证发生异常！");
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static TokenResponse sign(String username, String secret, Long expireTime) {
        try {
            if (null == expireTime) {
                expireTime = EXPIRE_TIME;
            }
            Date date = new Date(System.currentTimeMillis() + expireTime);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            String accessToken = JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(date)
                    .sign(algorithm);
            return new TokenResponse(accessToken, date.getTime());
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 生成签名,默认超时时间5min
     *
     * @param username
     * @param secret
     * @return
     */
    public static TokenResponse sign(String username, String secret) {
        return sign(username, secret, null);
    }
}