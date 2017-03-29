package de.tse.example.sparkhibernatebeanrepository.server.technical.command;

import com.github.tinosteinort.beanrepository.BeanRepository;
import com.github.tinosteinort.beanrepository.PostConstructible;
import de.tse.example.sparkhibernatebeanrepository.server.technical.RequestUnmarshaller;
import spark.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandExecutorPool implements PostConstructible {

    private final Map<String, CommandExecutor> commandExecutors = new HashMap<>();
    private final RequestUnmarshaller unmarshaller;

    public CommandExecutorPool(final RequestUnmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public <RETURN_TYPE, PAYLOAD_TYPE> RETURN_TYPE execute(final String commandName, final Request request) {
        final CommandExecutor<PAYLOAD_TYPE, RETURN_TYPE> executor = commandExecutors.get(commandName);
        final PAYLOAD_TYPE payload = getPayloadFromRequest(request, executor.getPayloadType());
        return executor.execute(payload);
    }

    private <PAYLOAD_TYPE> PAYLOAD_TYPE getPayloadFromRequest(
            final Request request, final Class<PAYLOAD_TYPE> payloadType) {
        try {
            return unmarshaller.unmarshall(request, payloadType);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override public void onPostConstruct(final BeanRepository beanRepository) {
        final Set<CommandExecutor> executors = beanRepository.getBeansOfType(CommandExecutor.class);
        for (CommandExecutor executor : executors) {
            final CommandExecutor prevValue = commandExecutors.put(executor.getCommandName(), executor);
            if (prevValue != null) {
                throw new RuntimeException("Try to register two Commands for " + executor.getCommandName());
            }
        }
    }
}
