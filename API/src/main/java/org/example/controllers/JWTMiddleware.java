package org.example.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
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

    /**
     * Handles the verification of JWT tokens in requests
     * @param ctx the context of the request made from a client
     *
     * @author Kasper Schröder
     */
    @Override
    public void handle(Context ctx) throws Exception {
        String authHeader = ctx.header("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedResponse("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        try {
            JWTUtil.validateToken(token);
        } catch (JWTVerificationException e) {
            throw new UnauthorizedResponse("Invalid token");
        }
    }
}
