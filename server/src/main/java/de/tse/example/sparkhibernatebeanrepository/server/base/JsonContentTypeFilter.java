package de.tse.example.sparkhibernatebeanrepository.server.base;

import spark.Filter;
import spark.Request;
import spark.Response;

public class JsonContentTypeFilter implements Filter {

    @Override public void handle(final Request request, final Response response) throws Exception {
        response.type("application/json");
    }
}
