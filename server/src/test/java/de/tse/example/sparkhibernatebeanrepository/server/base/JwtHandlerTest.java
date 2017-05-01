package de.tse.example.sparkhibernatebeanrepository.server.base;

import com.github.tinosteinort.beanrepository.BeanRepository;
import io.jsonwebtoken.SignatureException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JwtHandlerTest {

    private JwtHandler jwtHandler;

    @Before public void setup() {
        final Configuration config = new Configuration();
        config.setJwtPassword("MockPassword");
        final BeanRepository repo = new BeanRepository.BeanRepositoryBuilder()
                .instance(config)
                .singleton(JwtPasswordProvider.class, JwtPasswordProvider::new, Configuration.class)
                .singleton(JwtHandler.class, JwtHandler::new, JwtPasswordProvider.class)
                .build();
        jwtHandler = repo.getBean(JwtHandler.class);
    }

    @Test public void generateAndParse() {

        final String jwt = jwtHandler.generateToken("User123");
        final String user = jwtHandler.determineUserFromToken(jwt);

        assertEquals("User123", user);
    }

    @Test(expected = SignatureException.class)
    public void parseManipulatedToken() {

        // original:               "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMTIzIn0.YFozqSGtRPzms6MM8lUNfUgMhC9BQC1EI3oC3nEZBan10hoBHNBOXDV9Sr1HfUw8PVzx7uVNxYgdsDb48d-JVg";
        // Difference:                                   ^
        final String manipulated = "eyJhbGciOiJIUzUxMiJ9.EyJzdWIiOiJVc2VyMTIzIn0.YFozqSGtRPzms6MM8lUNfUgMhC9BQC1EI3oC3nEZBan10hoBHNBOXDV9Sr1HfUw8PVzx7uVNxYgdsDb48d-JVg";

        jwtHandler.determineUserFromToken(manipulated);
    }
}
