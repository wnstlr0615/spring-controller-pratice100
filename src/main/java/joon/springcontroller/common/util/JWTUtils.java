package joon.springcontroller.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JWTUtils {
    public static String key="joonjoon";


    public static String getIssuer(String token) {
        return JWT.require(Algorithm.HMAC512(key.getBytes()))
                .build()
                .verify(token)
                .getIssuer();
    }
}
