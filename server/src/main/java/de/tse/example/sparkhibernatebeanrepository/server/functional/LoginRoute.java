package de.tse.example.sparkhibernatebeanrepository.server.functional;

import de.tse.example.sparkhibernatebeanrepository.api.to.AuthenticationStatus;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.UserBO;
import de.tse.example.sparkhibernatebeanrepository.server.technical.RequestUnmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginRoute implements Route {

    private static final Logger LOG = LoggerFactory.getLogger(LoginRoute.class);

    public static final String USER_ID = "USERID";

    private final UserService userService;
    private final RequestUnmarshaller requestUnmarshaller;

    public LoginRoute(final UserService userService, final RequestUnmarshaller requestUnmarshaller) {
        this.userService = userService;
        this.requestUnmarshaller = requestUnmarshaller;
    }

    @Override public AuthenticationStatus handle(final Request request, final Response response) throws Exception {
        final String name = requestUnmarshaller.unmarshall(request, String.class);
        final UserBO user = userService.loadByName(name);

        if (user == null) {
            return AuthenticationStatus.NOT_AUTHENTICATED;
        }

        LOG.debug("User %s logged id", user.getName());
        response.cookie(USER_ID, user.getName());
        return AuthenticationStatus.AUTHENTICATED;
    }
}