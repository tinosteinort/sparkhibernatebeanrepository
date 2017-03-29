package de.tse.example.sparkhibernatebeanrepository.client.services;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.api.base.Command;
import de.tse.example.sparkhibernatebeanrepository.client.base.HttpService;
import de.tse.example.sparkhibernatebeanrepository.client.base.ServiceUrlProvider;

import java.io.IOException;

public class CommandService {

    private final HttpService httpService;
    private final ServiceUrlProvider urlProvider;

    public CommandService(final BeanAccessor beans) {
        this.httpService = beans.getBean(HttpService.class);
        this.urlProvider = beans.getPrototypeBean(ServiceUrlProvider::new, "command");
    }

    public <PAYLOAD, RETURN_TYPE, COMMAND_TYPE extends Command<PAYLOAD, RETURN_TYPE>> RETURN_TYPE execute(final COMMAND_TYPE cmd) {
        try {
            return httpService.post(urlProvider.provide(cmd.getCommandName()), cmd.getPayload(), cmd.getResultType());
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
