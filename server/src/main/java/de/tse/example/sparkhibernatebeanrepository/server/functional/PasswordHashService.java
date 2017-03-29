package de.tse.example.sparkhibernatebeanrepository.server.functional;

import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.UserBO;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordHashService {

    private static final int ITERATIONS = 2;
    private static final int KEY_LENGTH = 512;

    public String calculateHash(final UserBO user, final String password) {
        return calculateHash(password.toCharArray(), user.getName().getBytes(StandardCharsets.UTF_8), ITERATIONS,
                KEY_LENGTH);
    }

    private String calculateHash(final char[] password, final byte[] salt, final int iterations,
            final int keyLength) {

        try {
            final SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            final PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            final SecretKey key = skf.generateSecret(spec);
            return Base64.getEncoder().encodeToString(key.getEncoded());
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException("Error while hashing Password", ex);
        }
    }
}
