package de.tse.example.sparkhibernatebeanrepository.server.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tinosteinort.beanrepository.BeanAccessor;
import spark.ResponseTransformer;

public class JsonResponseTransformer implements ResponseTransformer {

    private final ObjectMapper objectMapper;

    public JsonResponseTransformer(final BeanAccessor beans) {
        this.objectMapper = beans.getBean(ObjectMapper.class);
    }

    @Override public String render(final Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }
}
