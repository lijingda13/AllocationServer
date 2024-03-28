package com.project.allocation.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.project.allocation.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for handling JWT (JSON Web Tokens) operations such as creation, validation,
 * and extraction of claims. This class is used for securing the application by authenticating
 * users and authorizing requests.
 */
@Component
public class JwtUtil {

    private static final String SECRET_KEY = "CS5031-P3";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    /**
     * Creates a JWT token for the specified user.
     *
     * @param user The user for whom the token is created.
     * @return A JWT token as a String.
     */
    public String createToken(User user) {

        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(algorithm);
    }

    /**
     * Validates a given JWT token's signature and structure.
     *
     * @param token The JWT token to validate.
     * @return true if the token is valid, false otherwise.
     */
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

    /**
     * Extracts the user ID from a JWT token.
     *
     * @param token The JWT token from which to extract the user ID.
     * @return The user ID as a Long, or null if the token is invalid or does not contain a user ID.
     */
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

    /**
     * Extracts the username from a JWT token.
     *
     * @param token The JWT token from which to extract the username.
     * @return The username as a String, or null if the token is invalid or does not contain a username.
     */
    public String getUsernameFromToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("username").asString();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    /**
     * Extracts the role from a JWT token.
     *
     * @param token The JWT token from which to extract the user role.
     * @return The user role as a {@link User.Role}, or null if the token is invalid or does not contain a role.
     */
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
