package de.tse.example.sparkhibernatebeanrepository.server.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tinosteinort.beanrepository.Factory;

public class ObjectMapperFactory implements Factory<ObjectMapper> {

    @Override public ObjectMapper createInstance() {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }
}
