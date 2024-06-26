package com.yc.bilibili.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yc.bilibili.daomin.exception.ConditionException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class TokenUtil {

    //签发者
    private static  final String ISSUER = "月初不加班";

    // 时间戳
    private LocalDateTime localDateTime;
    public static String generateToken(Long userId) throws Exception{
        //算法
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey());
        //时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //过期时间30秒
        calendar.add(Calendar.HOUR,1);
        return JWT.create().withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    // 用户修改密码或者退出，token失效

    public static Long verifyToken(String token) {
        try{
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String userId = jwt.getKeyId();
            return Long.valueOf(userId);
        } catch (TokenExpiredException e){
            throw new ConditionException("555","toen过期！");
        }catch (Exception e){
            throw new ConditionException("非法用户Token！");
        }

    }

    /**
     *  RefreshToken  7天
     * @param id  用户Id
     * @return refreshToken
     * @throws Exception 异常
     */
    public static String generateRefreshToken(Long id) throws Exception{
        //算法
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(),RSAUtil.getPrivateKey());
        //时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //过期时间30秒
        calendar.add(Calendar.DAY_OF_MONTH,7);
        return JWT.create().withKeyId(String.valueOf(id))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    public static void verifyRefreshToken(String refreshToken) {
        TokenUtil.verifyToken(refreshToken);
    }
}
