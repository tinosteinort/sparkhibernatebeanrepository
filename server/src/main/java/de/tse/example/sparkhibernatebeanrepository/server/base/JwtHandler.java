package de.tse.example.sparkhibernatebeanrepository.server.base;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class JwtHandler {

    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;
    private final JwtPasswordProvider passwordProvider;

    public JwtHandler(final JwtPasswordProvider passwordProvider) {
        this.passwordProvider = passwordProvider;
    }

    public String generateToken(final String user) {

        final Key secretKey = new SecretKeySpec(passwordProvider.password(), ALGORITHM.getJcaName());

        final JwtBuilder builder = Jwts.builder()
                .setSubject(user)
                .signWith(ALGORITHM, secretKey);

        return builder.compact();
    }

    public String determineUserFromToken(final String token) {

        final Jws<Claims> jwt = Jwts.parser()
                .setSigningKey(passwordProvider.password())
                .parseClaimsJws(token);

        validateAlgorithm(jwt);

        final Claims claims = jwt.getBody();

        return claims.getSubject();
    }

    private void validateAlgorithm(final Jws<Claims> jwt) {
        if (algorithmOf(jwt) != ALGORITHM) {
            throw new RuntimeException("Invalid Token");
        }
    }

    private SignatureAlgorithm algorithmOf(final Jws<Claims> jwt) {
        final String algorithm = jwt.getHeader().getAlgorithm();
        if (algorithm == null || "".equals(algorithm)) {
            return null;
        }
        return SignatureAlgorithm.valueOf(algorithm);
    }
}
