package de.tse.example.sparkhibernatebeanrepository.server.routes;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.server.base.ContextExecutor;
import de.tse.example.sparkhibernatebeanrepository.server.base.JwtHandler;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginValidationRoute implements Route {

    private final ContextExecutor contextExecutor;
    private final JwtHandler jwtHandler;
    private final Route route;

    public LoginValidationRoute(final BeanAccessor beans, final Route route) {
        this.contextExecutor = beans.getBean(ContextExecutor.class);
        this.jwtHandler = beans.getBean(JwtHandler.class);
        this.route = route;
    }

    @Override public Object handle(final Request request, final Response response) throws Exception {

        final String token = request.cookie(LoginRoute.USER_ID);
        if (token == null) {
            throw new RuntimeException("Not authenticated");
        }

        final String username = jwtHandler.determineUserFromToken(token);

        return contextExecutor.execute(username, () -> route.handle(request, response));
    }
}
