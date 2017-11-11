package de.tse.example.sparkhibernatebeanrepository.server.base;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.server.routes.CommandExecutorRoute;
import de.tse.example.sparkhibernatebeanrepository.server.routes.LoginRoute;
import de.tse.example.sparkhibernatebeanrepository.server.routes.LoginValidationRoute;
import de.tse.example.sparkhibernatebeanrepository.server.routes.TransactionDelegateRoute;
import spark.Route;
import spark.Spark;

import static de.tse.example.sparkhibernatebeanrepository.server.routes.CommandExecutorRoute.COMMAND_PARAM;

public class SparkWrapper {

    private final BeanAccessor beans;

    public SparkWrapper(final BeanAccessor beans) {
        this.beans = beans;
    }

    public void configureAndRun() {

        final Configuration config = beans.getBean(Configuration.class);

        Spark.port(config.getPort());
        Spark.secure(config.getKeystoreFile(), config.getKeystorePassword(), null, null);

        final JsonResponseTransformer responseTransformer = beans.getBean(JsonResponseTransformer.class);

        Spark.before(beans.getBean(RequestLogger.class));
        Spark.after(beans.getBean(ResponseLogger.class));

        Spark.post("/command/" + COMMAND_PARAM, withTransactionAndUser(CommandExecutorRoute.class), responseTransformer);

        Spark.post("/login", withTransaction(LoginRoute.class), responseTransformer);
        Spark.after("/login", beans.getBean(JsonContentTypeFilter.class));

        Spark.exception(Exception.class, beans.getBean(MyExceptionHandler.class));
    }

    private <T extends Route> Route withTransactionAndUser(final Class<T> routeClass) {
        final T route = beans.getBean(routeClass);
        final LoginValidationRoute loginRoute = beans.getPrototypeBean(LoginValidationRoute::new, route);
        return withTransaction(loginRoute);
    }

    private <T extends Route> Route withTransaction(final Class<T> routeClass) {
        final T route = beans.getBean(routeClass);
        return withTransaction(route);
    }

    private <T extends Route> Route withTransaction(final T route) {
        return beans.getPrototypeBean(TransactionDelegateRoute::new, route);
    }
}
