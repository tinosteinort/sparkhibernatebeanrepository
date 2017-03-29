package de.tse.example.sparkhibernatebeanrepository.server.routes;

import de.tse.example.sparkhibernatebeanrepository.api.base.AuthenticationStatus;
import de.tse.example.sparkhibernatebeanrepository.server.services.PasswordService;
import de.tse.example.sparkhibernatebeanrepository.server.base.JwtHandler;
import de.tse.example.sparkhibernatebeanrepository.server.base.RequestUnmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginRoute implements Route {

    private static final Logger LOG = LoggerFactory.getLogger(LoginRoute.class);

    public static final String USER_ID = "USERID";

    private final RequestUnmarshaller requestUnmarshaller;
    private final JwtHandler jwtHandler;
    private final PasswordService passwordService;

    public LoginRoute(final RequestUnmarshaller requestUnmarshaller,
            final JwtHandler jwtHandler, final PasswordService passwordService) {
        this.requestUnmarshaller = requestUnmarshaller;
        this.jwtHandler = jwtHandler;
        this.passwordService = passwordService;
    }

    @Override public AuthenticationStatus handle(final Request request, final Response response) throws Exception {
        final String namePasswordCombination = requestUnmarshaller.unmarshall(request, String.class);
        final String[] credentials = credentials(namePasswordCombination);

        final String name = credentials[0];
        final String password = credentials[1];

        if (passwordService.credentialsAreValid(name, password)) {
            LOG.debug("User %s logged id", name);
            response.cookie(USER_ID, jwtHandler.generateToken(name));
            return AuthenticationStatus.AUTHENTICATED;
        }
        return AuthenticationStatus.NOT_AUTHENTICATED;
    }

    private String[] credentials(final String namePasswordCombination) {
        return namePasswordCombination.split("\\:");
    }
}
