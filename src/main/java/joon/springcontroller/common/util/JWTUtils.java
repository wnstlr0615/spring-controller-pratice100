package joon.springcontroller.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.model.UserLoginToken;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Date;

@UtilityClass
public class JWTUtils {
    public static final String key="joonjoon";
    private static final String CLAIM_USER_ID = "user_id";


    public static String getIssuer(String token) {
        return JWT.require(Algorithm.HMAC512(key.getBytes()))
                .build()
                .verify(token)
                .getIssuer();
    }

    public static UserLoginToken createToken(User user) {
        if(user==null)return null;
        LocalDateTime expireDateTime=LocalDateTime.now().plusDays(1);
        Date expireDate=java.sql.Timestamp.valueOf(expireDateTime);
        String token=JWT.create()
                .withExpiresAt(expireDate)
                .withClaim(CLAIM_USER_ID, user.getUsername())
                .withSubject(user.getUsername())
                .withIssuer(user.getEmail())
                .sign(Algorithm.HMAC512(key.getBytes()));

        return UserLoginToken.builder()
                .token(token)
                .build();
    }
}
