package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.server.functional.LoginRoute;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginValidationRoute implements Route {

    private final ContextExecutor contextExecutor;
    private final Route route;

    public LoginValidationRoute(final BeanAccessor beans, final Route route) {
        this.contextExecutor = beans.getBean(ContextExecutor.class);
        this.route = route;
    }

    @Override public Object handle(final Request request, final Response response) throws Exception {

        final String token = request.cookie(LoginRoute.USER_ID);
        if (token == null) {
            throw new RuntimeException("Not authenticated");
        }

        final String username = getUserFromToken(token);

        return contextExecutor.execute(username, () -> route.handle(request, response));
    }

    private String getUserFromToken(final String token) {

        final Claims claims = Jwts.parser()
                .setSigningKey("MyKey".getBytes())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
