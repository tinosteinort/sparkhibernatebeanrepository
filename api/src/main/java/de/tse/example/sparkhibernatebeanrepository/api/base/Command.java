package de.tse.example.sparkhibernatebeanrepository.api.base;

public interface Command<PAYLOAD_TYPE, RESULT_TYPE> {

    String getCommandName();
    PAYLOAD_TYPE getPayload();
    Class<RESULT_TYPE> getResultType();
}
