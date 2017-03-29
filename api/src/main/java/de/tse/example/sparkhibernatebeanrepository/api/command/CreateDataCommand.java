package de.tse.example.sparkhibernatebeanrepository.api.command;

import de.tse.example.sparkhibernatebeanrepository.api.base.Command;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoTO;

public class CreateDataCommand implements Command<CreateInputTO, InputInfoTO> {

    public static final String COMMAND_NAME = "createdata";

    private final CreateInputTO data;

    public CreateDataCommand(final CreateInputTO data) {
        this.data = data;
    }

    @Override public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override public CreateInputTO getPayload() {
        return data;
    }

    @Override public Class<InputInfoTO> getResultType() {
        return InputInfoTO.class;
    }
}
