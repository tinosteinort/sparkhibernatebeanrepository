package de.tse.example.sparkhibernatebeanrepository.server.command;

import de.tse.example.sparkhibernatebeanrepository.api.command.CreateDataCommand;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoTO;
import de.tse.example.sparkhibernatebeanrepository.server.functional.InputInfoQueryService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.InputService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.SavedInputBO;
import de.tse.example.sparkhibernatebeanrepository.server.technical.Context;
import de.tse.example.sparkhibernatebeanrepository.server.technical.command.CommandExecutor;

public class CreateDataCommandExecutor implements CommandExecutor<CreateInputTO, InputInfoTO> {

    private final InputService inputService;
    private final InputInfoQueryService inputInfoQueryService;

    public CreateDataCommandExecutor(final InputService inputService, final InputInfoQueryService inputInfoQueryService) {
        this.inputService = inputService;
        this.inputInfoQueryService = inputInfoQueryService;
    }

    @Override public InputInfoTO execute(final CreateInputTO input) {
        final SavedInputBO savedInput = inputService.create(input, Context.get().getName());
        return inputInfoQueryService.map(savedInput);
    }

    @Override public String getCommandName() {
        return CreateDataCommand.COMMAND_NAME;
    }

    @Override public Class<CreateInputTO> getPayloadType() {
        return CreateInputTO.class;
    }
}
