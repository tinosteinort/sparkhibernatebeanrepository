package de.tse.example.sparkhibernatebeanrepository.server.routes;

import de.tse.example.sparkhibernatebeanrepository.api.base.AuthenticationStatus;
import de.tse.example.sparkhibernatebeanrepository.api.base.FormParams;
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

        // The Method 'request.body()' must not be called before request.queryParams() was
        //  accessed, because queryParams could not be read after that. If 'request.body()'
        //  was called, the values are NULL.
        final String name = request.queryParams(FormParams.USERNAME);
        final String password = request.queryParams(FormParams.PASSWORD);

        if (passwordService.credentialsAreValid(name, password)) {
            LOG.debug("User %s logged id", name);
            response.cookie(USER_ID, jwtHandler.generateToken(name));
            return AuthenticationStatus.AUTHENTICATED;
        }
        return AuthenticationStatus.NOT_AUTHENTICATED;
    }
}
