package de.tse.example.sparkhibernatebeanrepository.server.commandexecutor;

import de.tse.example.sparkhibernatebeanrepository.api.command.GetDataCommand;
import de.tse.example.sparkhibernatebeanrepository.api.to.FilterTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoListTO;
import de.tse.example.sparkhibernatebeanrepository.server.services.InputInfoQueryService;
import de.tse.example.sparkhibernatebeanrepository.server.base.command.CommandExecutor;

public class GetDataCommandExecutor implements CommandExecutor<FilterTO, InputInfoListTO> {

    private final InputInfoQueryService inputInfoQueryService;

    public GetDataCommandExecutor(final InputInfoQueryService inputInfoQueryService) {
        this.inputInfoQueryService = inputInfoQueryService;
    }

    @Override public InputInfoListTO execute(final FilterTO filter) {
        if (filter.getSearchValue() == null || "".equals(filter.getSearchValue())) {
            return inputInfoQueryService.listAll();
        }
        return inputInfoQueryService.findAllContaining(filter.getSearchValue());
    }

    @Override public Class<FilterTO> getPayloadType() {
        return FilterTO.class;
    }

    @Override public String getCommandName() {
        return GetDataCommand.COMMAND_NAME;
    }
}
