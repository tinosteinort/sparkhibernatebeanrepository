package de.tse.example.sparkhibernatebeanrepository.server.technical;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

public class MyExceptionHandler implements ExceptionHandler {

    @Override public void handle(final Exception ex, final Request request, final Response response) {
        response.status(500);
        response.body("Es ist ein Fehler aufgetreten: " + ex.getMessage());
        ex.printStackTrace();
    }
}
