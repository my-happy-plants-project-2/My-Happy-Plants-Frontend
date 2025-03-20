package org.example.model;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.github.cdimascio.dotenv.Dotenv;


import java.util.Date;

/**
 * Utility class for handling JWT tokens
 * Contains methods for generating and validating JWT tokens
 * using the Auth0 Java JWT library
 *
 * @author Kasper Schröder
 */
public class JWTUtil {
    static Dotenv dotenv = Dotenv.load();
    private static final String SECRET_KEY = dotenv.get("SECRET_KEY");
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    /**
     * Generates a JWT token for a given email address
     * @param email the email address to generate a token for
     * @return a JWT token as a string
     *
     * @author Kasper Schröder
     */
    public static String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(ALGORITHM);
    }

    /**
     * Validates a JWT token
     * @param token the token to validate
     * @return a DecodedJWT object representing the token
     * @throws JWTVerificationException if the token is invalid
     *
     * @author Kasper Schröder
     */
    public static DecodedJWT validateToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(token);
    }
}
