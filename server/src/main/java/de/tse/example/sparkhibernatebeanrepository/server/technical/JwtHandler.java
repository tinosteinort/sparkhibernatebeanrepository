package de.tse.example.sparkhibernatebeanrepository.server.technical;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class JwtHandler {

    private final byte[] key = "MyKey".getBytes();

    public String generateToken(final String user) {

        final SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;

        final Key secretKey = new SecretKeySpec(key, algorithm.getJcaName());

        final JwtBuilder builder = Jwts.builder()
                .setSubject(user)
                .signWith(algorithm, secretKey);

        return builder.compact();
    }

    public String determineUserFromToken(final String token) {

        final Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
