package com.project.allocation.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.project.allocation.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "CS5031-P3";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public String createToken(User user) {

        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(algorithm);
    }

    public boolean validateToken(String token) {
        token = token.replace("Bearer ", "");
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        token = token.replace("Bearer ", "");
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return Long.valueOf(jwt.getSubject());
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("username").asString();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public User.Role getRoleFromToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return User.Role.valueOf(jwt.getClaim("role").asString());
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
