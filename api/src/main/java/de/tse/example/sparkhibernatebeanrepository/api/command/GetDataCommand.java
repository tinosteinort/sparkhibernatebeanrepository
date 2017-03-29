package de.tse.example.sparkhibernatebeanrepository.api.command;

import de.tse.example.sparkhibernatebeanrepository.api.base.Command;
import de.tse.example.sparkhibernatebeanrepository.api.to.FilterTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoListTO;

public class GetDataCommand implements Command<FilterTO, InputInfoListTO> {

    public static final String COMMAND_NAME = "getdata";

    private final FilterTO filter;

    public GetDataCommand(final FilterTO filter) {
        this.filter = filter;
    }

    @Override public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override public FilterTO getPayload() {
        return filter;
    }

    @Override public Class<InputInfoListTO> getResultType() {
        return InputInfoListTO.class;
    }
}
