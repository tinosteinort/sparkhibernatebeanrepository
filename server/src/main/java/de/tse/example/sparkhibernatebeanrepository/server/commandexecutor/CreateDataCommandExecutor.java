package de.tse.example.sparkhibernatebeanrepository.server.commandexecutor;

import de.tse.example.sparkhibernatebeanrepository.api.command.CreateDataCommand;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoTO;
import de.tse.example.sparkhibernatebeanrepository.server.services.InputInfoQueryService;
import de.tse.example.sparkhibernatebeanrepository.server.services.InputService;
import de.tse.example.sparkhibernatebeanrepository.server.bo.SavedInputBO;
import de.tse.example.sparkhibernatebeanrepository.server.base.Context;
import de.tse.example.sparkhibernatebeanrepository.server.base.command.CommandExecutor;

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
