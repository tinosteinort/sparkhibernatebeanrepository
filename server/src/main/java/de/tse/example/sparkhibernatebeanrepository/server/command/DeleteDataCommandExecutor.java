package de.tse.example.sparkhibernatebeanrepository.server.command;

import de.tse.example.sparkhibernatebeanrepository.api.command.DeleteDataCommand;
import de.tse.example.sparkhibernatebeanrepository.server.functional.InputService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.SavedInputBO;
import de.tse.example.sparkhibernatebeanrepository.server.technical.command.CommandExecutor;

public class DeleteDataCommandExecutor implements CommandExecutor<Long, Void> {

    private final InputService inputService;

    public DeleteDataCommandExecutor(final InputService inputService) {
        this.inputService = inputService;
    }

    @Override public Void execute(final Long dataId) {

        final SavedInputBO load = inputService.load(dataId);
        if (load != null) {
            inputService.delete(load);
        }

        return null;
    }

    @Override public String getCommandName() {
        return DeleteDataCommand.COMMAND_NAME;
    }

    @Override public Class<Long> getPayloadType() {
        return Long.class;
    }
}
