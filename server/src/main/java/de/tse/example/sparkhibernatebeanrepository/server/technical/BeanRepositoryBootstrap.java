package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tinosteinort.beanrepository.BeanRepository;
import de.tse.example.sparkhibernatebeanrepository.server.command.CreateDataCommandExecutor;
import de.tse.example.sparkhibernatebeanrepository.server.command.DeleteDataCommandExecutor;
import de.tse.example.sparkhibernatebeanrepository.server.command.GetDataCommandExecutor;
import de.tse.example.sparkhibernatebeanrepository.server.functional.InputInfoQueryService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.InputService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.LoginRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.PasswordHashService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.PasswordService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.UserService;
import de.tse.example.sparkhibernatebeanrepository.server.technical.command.CommandExecutorPool;
import org.hibernate.SessionFactory;

public class BeanRepositoryBootstrap {

    private final BeanRepository.BeanRepositoryBuilder builder = new BeanRepository.BeanRepositoryBuilder();

    private void registerBeans() {

        registerTechnicalBeans();
        registerServices();
        registerRoutes();
        registerCommands();
    }

    private void registerTechnicalBeans() {
        builder.singleton(CommandExecutorPool.class, CommandExecutorPool::new, RequestUnmarshaller.class);
        builder.singletonFactory(SessionFactory.class, SessionFactoryFactory::new);
        builder.singleton(TransactionExecutor.class, TransactionExecutor::new);
        builder.singleton(ContextExecutor.class, ContextExecutor::new);
        builder.singleton(UserFilterExecutor.class, UserFilterExecutor::new);
        builder.singleton(MyExceptionHandler.class, MyExceptionHandler::new);
        builder.singletonFactory(ObjectMapper.class, ObjectMapperFactory::new);
        builder.singleton(RequestUnmarshaller.class, RequestUnmarshaller::new, ObjectMapper.class);
        builder.singleton(JsonResponseTransformer.class, JsonResponseTransformer::new);
        builder.singleton(JsonContentTypeFilter.class, JsonContentTypeFilter::new);
        builder.singleton(RequestLogger.class, RequestLogger::new);
        builder.singleton(ResponseLogger.class, ResponseLogger::new);
        builder.singleton(JwtHandler.class, JwtHandler::new);
    }

    private void registerServices() {
        builder.singleton(DbService.class, DbService::new, SessionFactory.class);
        builder.singleton(InputService.class, InputService::new, DbService.class);
        builder.singleton(UserService.class, UserService::new, DbService.class);
        builder.singleton(InputInfoQueryService.class, InputInfoQueryService::new);
        builder.singleton(PasswordService.class, PasswordService::new, DbService.class, PasswordHashService.class);
        builder.singleton(PasswordHashService.class, PasswordHashService::new);
    }

    private void registerRoutes() {
        builder.singleton(LoginRoute.class, LoginRoute::new, RequestUnmarshaller.class, JwtHandler.class, PasswordService.class);
        builder.singleton(CommandExecutorRoute.class, CommandExecutorRoute::new, CommandExecutorPool.class);
    }

    private void registerCommands() {
        builder.singleton(GetDataCommandExecutor.class, GetDataCommandExecutor::new, InputInfoQueryService.class);
        builder.singleton(CreateDataCommandExecutor.class, CreateDataCommandExecutor::new, InputService.class, InputInfoQueryService.class);
        builder.singleton(DeleteDataCommandExecutor.class, DeleteDataCommandExecutor::new, InputService.class);

    }

    public BeanRepository bootstrap() {
        registerBeans();
        return builder.build();
    }
}
