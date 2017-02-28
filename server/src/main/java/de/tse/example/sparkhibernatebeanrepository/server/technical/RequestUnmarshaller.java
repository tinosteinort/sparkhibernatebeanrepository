package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;

import java.io.IOException;

public class RequestUnmarshaller {

    private final ObjectMapper objectMapper;

    public RequestUnmarshaller(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T unmarshall(final Request request, final Class<T> dataClass) throws IOException {
        return objectMapper.readValue(request.bodyAsBytes(), dataClass);
    }
}
