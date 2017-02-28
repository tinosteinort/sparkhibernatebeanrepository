package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.server.functional.LoginRoute;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginDelegateRoute implements Route {

    private final ContextExecutor contextExecutor;
    private final Route route;

    public LoginDelegateRoute(final BeanAccessor beans, final Route route) {
        this.contextExecutor = beans.getBean(ContextExecutor.class);
        this.route = route;
    }

    @Override public Object handle(final Request request, final Response response) throws Exception {

        final String userId = request.cookie(LoginRoute.USER_ID);
        if (userId == null) {
            throw new RuntimeException("Not authenticated");
        }

        return contextExecutor.execute(userId, () -> route.handle(request, response));
    }
}
