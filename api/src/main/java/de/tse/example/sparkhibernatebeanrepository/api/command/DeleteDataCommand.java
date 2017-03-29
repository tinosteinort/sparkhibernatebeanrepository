package de.tse.example.sparkhibernatebeanrepository.api.command;

import de.tse.example.sparkhibernatebeanrepository.api.base.Command;

public class DeleteDataCommand implements Command<Long, Void> {

    public static final String COMMAND_NAME = "deletedata";

    private final long dataId;

    public DeleteDataCommand(final long dataId) {
        this.dataId = dataId;
    }

    @Override public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override public Long getPayload() {
        return dataId;
    }

    @Override public Class<Void> getResultType() {
        return Void.class;
    }
}
