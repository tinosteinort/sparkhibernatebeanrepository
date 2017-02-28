package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class AbstractMarshallingRoute<T, R> implements Route {

    private final Class<T> requestClass;
    private final RequestUnmarshaller requestUnmarshaller;

    protected AbstractMarshallingRoute(final Class<T> requestClass, final BeanAccessor beans) {
        this.requestClass = requestClass;
        this.requestUnmarshaller = beans.getBean(RequestUnmarshaller.class);
    }

    @Override public Object handle(final Request request, final Response response) throws Exception {
        final T obj = requestUnmarshaller.unmarshall(request, requestClass);
        return handleRequest(obj);
    }

    protected abstract R handleRequest(T obj);
}
