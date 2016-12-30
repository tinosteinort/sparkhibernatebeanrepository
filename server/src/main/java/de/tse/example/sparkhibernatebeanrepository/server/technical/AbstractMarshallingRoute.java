package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tinosteinort.beanrepository.BeanAccessor;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class AbstractMarshallingRoute<T, R> implements Route{

    private final Class<T> requestClass;
    private final ObjectMapper objectMapper;

    protected AbstractMarshallingRoute(final Class<T> requestClass, final BeanAccessor beans) {
        this.requestClass = requestClass;
        this.objectMapper = beans.getBean(ObjectMapper.class);
    }

    @Override public Object handle(final Request request, final Response response) throws Exception {
        final T obj = objectMapper.readValue(request.bodyAsBytes(), requestClass);
        return handleRequest(obj);
    }

    protected abstract R handleRequest(T obj);
}
