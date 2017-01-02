package de.tse.example.sparkhibernatebeanrepository.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tinosteinort.beanrepository.BeanRepository;
import de.tse.example.sparkhibernatebeanrepository.server.functional.CreateDataRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.GetDataRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.InputInfoQueryService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.InputService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.UserService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.UserBO;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.server.technical.JsonResponseTransformer;
import de.tse.example.sparkhibernatebeanrepository.server.technical.ContextExecutor;
import de.tse.example.sparkhibernatebeanrepository.server.technical.LoginDelegateRoute;
import de.tse.example.sparkhibernatebeanrepository.server.technical.MyExceptionHandler;
import de.tse.example.sparkhibernatebeanrepository.server.technical.ObjectMapperFactory;
import de.tse.example.sparkhibernatebeanrepository.server.technical.SessionFactoryFactory;
import de.tse.example.sparkhibernatebeanrepository.server.technical.TransactionDelegateRoute;
import de.tse.example.sparkhibernatebeanrepository.server.technical.TransactionExecutor;
import de.tse.example.sparkhibernatebeanrepository.server.technical.UserFilterExecutor;
import org.hibernate.SessionFactory;
import spark.Route;
import spark.Spark;

public class Start {

    private final BeanRepository repo;

    public Start() {
        this.repo = new BeanRepository.BeanRepositoryBuilder()
                .singleton(InputService.class, InputService::new)
                .singleton(CreateDataRoute.class, CreateDataRoute::new)
                .singleton(GetDataRoute.class, GetDataRoute::new)
                .singletonFactory(SessionFactory.class, SessionFactoryFactory::new)
                .singleton(TransactionExecutor.class, TransactionExecutor::new)
                .singleton(ContextExecutor.class, ContextExecutor::new)
                .singleton(UserFilterExecutor.class, UserFilterExecutor::new)
                .singleton(UserService.class, UserService::new)
                .singleton(InputInfoQueryService.class, InputInfoQueryService::new)
                .singleton(MyExceptionHandler.class, MyExceptionHandler::new)
                .singletonFactory(ObjectMapper.class, ObjectMapperFactory::new)
                .singleton(JsonResponseTransformer.class, JsonResponseTransformer::new)
                .build();
    }

    public static void main(String[] args) throws Exception {
        new Start().start();
    }

    public void start() throws Exception {
        generateData();


        final String keystoreFile = System.getProperty("keystore");
        final String keystorePassword = System.getProperty("keystorePassword");
        final int port = Integer.valueOf(System.getProperty("port"));

        Spark.port(port);
        Spark.secure(keystoreFile, keystorePassword, null, null);

        final JsonResponseTransformer responseTransformer = repo.getBean(JsonResponseTransformer.class);

        Spark.get("/data", withTransactionAndUser(GetDataRoute.class), responseTransformer);
        Spark.post("/data", withTransactionAndUser(CreateDataRoute.class), responseTransformer);
        Spark.exception(Exception.class, repo.getBean(MyExceptionHandler.class));
    }

    private void generateData() throws Exception {
        final TransactionExecutor transactionExecutor = repo.getBean(TransactionExecutor.class);
        final ContextExecutor contextExecutor = repo.getBean(ContextExecutor.class);

        transactionExecutor.executeWithinTransaction(() -> contextExecutor.execute(() -> {

            final UserService userService = repo.getBean(UserService.class);
            final UserBO tino = new UserBO();
            tino.setName("tino");
            userService.save(tino);

            final UserBO donnie = new UserBO();
            donnie.setName("donnie");
            userService.save(donnie);


            final InputService inputService = repo.getBean(InputService.class);

            final CreateInputTO input1 = new CreateInputTO();
            input1.setInput("Abc");
            inputService.create(input1, "tino");

            final CreateInputTO input2 = new CreateInputTO();
            input2.setInput("Def");
            inputService.create(input2, "tino");

            final CreateInputTO input3 = new CreateInputTO();
            input3.setInput("123");
            inputService.create(input3, "donnie");

            return null;
        }));
    }

    public <T extends Route> Route withTransactionAndUser(final Class<T> routeClass) {
        final T route = repo.getBean(routeClass);
        final LoginDelegateRoute loginRoute = repo.getBean((beans) -> new LoginDelegateRoute(beans, route));
        return repo.getBean((beans) -> new TransactionDelegateRoute(beans, loginRoute));
    }
}
