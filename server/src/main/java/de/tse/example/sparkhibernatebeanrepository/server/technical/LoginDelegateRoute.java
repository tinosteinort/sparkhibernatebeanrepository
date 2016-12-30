package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.github.tinosteinort.beanrepository.BeanAccessor;
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

//        final String name = request.cookie("name");
        final String name = request.queryParams("name");
        return contextExecutor.execute(name, () -> route.handle(request, response));
    }
}
