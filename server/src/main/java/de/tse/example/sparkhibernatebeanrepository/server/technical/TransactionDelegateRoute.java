package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import spark.Request;
import spark.Response;
import spark.Route;

public class TransactionDelegateRoute implements Route {

    private final TransactionExecutor transactionExecutor;
    private final Route route;

    public TransactionDelegateRoute(final BeanAccessor beans, final Route route) {
        this.transactionExecutor = beans.getBean(TransactionExecutor.class);
        this.route = route;
    }

    @Override public Object handle(final Request request, final Response response) throws Exception {
        return transactionExecutor.executeWithinTransaction(() -> route.handle(request, response));
    }
}
