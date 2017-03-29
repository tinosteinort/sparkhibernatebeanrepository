package de.tse.example.sparkhibernatebeanrepository.server.technical;

import de.tse.example.sparkhibernatebeanrepository.server.technical.command.CommandExecutorPool;
import spark.Request;
import spark.Response;
import spark.Route;

public class CommandExecutorRoute implements Route {

    public static final String COMMAND_PARAM = ":COMMAND";

    private final CommandExecutorPool commandExecutorPool;

    public CommandExecutorRoute(final CommandExecutorPool commandExecutorPool) {
        this.commandExecutorPool = commandExecutorPool;
    }

    @Override public Object handle(final Request request, final Response response) throws Exception {
        final String command = request.params(COMMAND_PARAM);
        return commandExecutorPool.execute(command, request);
    }
}
