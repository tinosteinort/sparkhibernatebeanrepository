package de.tse.example.sparkhibernatebeanrepository.server.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;

public class ResponseLogger implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseLogger.class);

    @Override public void handle(final Request request, final Response response) throws Exception {
        LOG.info("<< " + System.lineSeparator() + (response.body() == null ? "" : response.body()));
    }
}
