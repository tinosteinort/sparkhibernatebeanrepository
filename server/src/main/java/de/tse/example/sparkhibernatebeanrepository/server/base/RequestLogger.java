package de.tse.example.sparkhibernatebeanrepository.server.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;

public class RequestLogger implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestLogger.class);

    @Override public void handle(final Request request, final Response response) throws Exception {
        if (request.url().endsWith("login")) {
            LOG.info(">> Requested URL: " + request.url());
        }
        else {
            final String body = request.body() == null || "".equals(request.body()) ? "" : request.body();
            LOG.info(">> Requested URL: " + request.url() + System.lineSeparator() + body);
        }
    }
}
