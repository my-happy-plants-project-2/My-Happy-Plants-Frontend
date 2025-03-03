package org.example.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import org.example.model.JWTUtil;

/**
 * Middleware for verifying JWT tokens
 * Contains a method for handling JWT token verification
 * in requests
 *
 * @author Kasper Schröder
 */
public class JWTMiddleware implements Handler {

    // TODO: Improve class to make sure JWT is properly used, also add improved error handling,
    //  see https://medium.com/@victoronu/implementing-jwt-authentication-in-a-simple-spring-boot-application-with-java-b3135dbdb17b for more
    //  alternatively update JWTUtil.java and use only that class for JWT handling

    /**
     * Handles the verification of JWT tokens in requests
     * @param context the context of the request made from a client
     *
     * @author Kasper Schröder
     */
    @Override
    public void handle(Context context) {
        String authHeader = context.header("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedResponse("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        try {
            DecodedJWT decodedJWT = JWTUtil.validateToken(token);
            String email = decodedJWT.getSubject();
            context.attribute("userEmail", email);
        } catch (JWTVerificationException e) {
            throw new UnauthorizedResponse("Invalid token");
        }
    }
}
