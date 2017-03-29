package de.tse.example.sparkhibernatebeanrepository.server.technical.command;

public interface CommandExecutor<PAYLOAD_TYPE, RESULT_TYPE> {

    String getCommandName();
    Class<PAYLOAD_TYPE> getPayloadType();
    RESULT_TYPE execute(PAYLOAD_TYPE payload);
}
