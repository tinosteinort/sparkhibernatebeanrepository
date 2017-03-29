package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tinosteinort.beanrepository.BeanRepository;
import de.tse.example.sparkhibernatebeanrepository.server.functional.CreateDataRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.DeleteDataRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.GetDataRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.InputInfoQueryService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.InputService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.LoginRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.PasswordService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.SearchDataRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.UserService;
import org.hibernate.SessionFactory;

public class BeanRepositoryBootstrap {

    private final BeanRepository.BeanRepositoryBuilder builder = new BeanRepository.BeanRepositoryBuilder();

    private void registerBeans() {
        builder.singleton(DbService.class, DbService::new, SessionFactory.class)
                .singleton(InputService.class, InputService::new, DbService.class)
                .singleton(LoginRoute.class, LoginRoute::new, UserService.class, RequestUnmarshaller.class, JwtHandler.class, PasswordService.class)
                .singleton(CreateDataRoute.class, CreateDataRoute::new)
                .singleton(GetDataRoute.class, GetDataRoute::new)
                .singleton(SearchDataRoute.class, SearchDataRoute::new)
                .singleton(DeleteDataRoute.class, DeleteDataRoute::new)
                .singletonFactory(SessionFactory.class, SessionFactoryFactory::new)
                .singleton(TransactionExecutor.class, TransactionExecutor::new)
                .singleton(ContextExecutor.class, ContextExecutor::new)
                .singleton(UserFilterExecutor.class, UserFilterExecutor::new)
                .singleton(UserService.class, UserService::new, DbService.class)
                .singleton(InputInfoQueryService.class, InputInfoQueryService::new)
                .singleton(MyExceptionHandler.class, MyExceptionHandler::new)
                .singletonFactory(ObjectMapper.class, ObjectMapperFactory::new)
                .singleton(RequestUnmarshaller.class, RequestUnmarshaller::new, ObjectMapper.class)
                .singleton(JsonResponseTransformer.class, JsonResponseTransformer::new)
                .singleton(JsonContentTypeFilter.class, JsonContentTypeFilter::new)
                .singleton(RequestLogger.class, RequestLogger::new)
                .singleton(ResponseLogger.class, ResponseLogger::new)
                .singleton(JwtHandler.class, JwtHandler::new)
                .singleton(PasswordService.class, PasswordService::new, DbService.class);
    }

    public BeanRepository bootstrap() {
        registerBeans();
        return builder.build();
    }
}
