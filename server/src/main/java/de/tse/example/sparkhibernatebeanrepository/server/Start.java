package de.tse.example.sparkhibernatebeanrepository.server;

import com.github.tinosteinort.beanrepository.BeanRepository;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.server.functional.CreateDataRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.DeleteDataRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.GetDataRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.InputService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.LoginRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.PasswordService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.SearchDataRoute;
import de.tse.example.sparkhibernatebeanrepository.server.functional.UserService;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.UserBO;
import de.tse.example.sparkhibernatebeanrepository.server.technical.BeanRepositoryBootstrap;
import de.tse.example.sparkhibernatebeanrepository.server.technical.ContextExecutor;
import de.tse.example.sparkhibernatebeanrepository.server.technical.JsonContentTypeFilter;
import de.tse.example.sparkhibernatebeanrepository.server.technical.JsonResponseTransformer;
import de.tse.example.sparkhibernatebeanrepository.server.technical.LoginValidationRoute;
import de.tse.example.sparkhibernatebeanrepository.server.technical.MyExceptionHandler;
import de.tse.example.sparkhibernatebeanrepository.server.technical.RequestLogger;
import de.tse.example.sparkhibernatebeanrepository.server.technical.ResponseLogger;
import de.tse.example.sparkhibernatebeanrepository.server.technical.TransactionDelegateRoute;
import de.tse.example.sparkhibernatebeanrepository.server.technical.TransactionExecutor;
import spark.Route;
import spark.Spark;

import static de.tse.example.sparkhibernatebeanrepository.server.functional.DeleteDataRoute.DELETE_ID;

public class Start {

    private final BeanRepository repo;

    public Start() {
        this.repo = new BeanRepositoryBootstrap().bootstrap();
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

        Spark.before(repo.getBean(RequestLogger.class));
        Spark.after(repo.getBean(ResponseLogger.class));

        Spark.post("/login", withTransaction(LoginRoute.class), responseTransformer);
        Spark.get("/data", withTransactionAndUser(GetDataRoute.class), responseTransformer);
        Spark.post("/data/filter", withTransactionAndUser(SearchDataRoute.class), responseTransformer);
        Spark.post("/data", withTransactionAndUser(CreateDataRoute.class), responseTransformer);
        Spark.delete("/data/" + DELETE_ID, withTransactionAndUser(DeleteDataRoute.class), responseTransformer);

        Spark.after("/login", repo.getBean(JsonContentTypeFilter.class));
        Spark.after("/data", repo.getBean(JsonContentTypeFilter.class));

        Spark.exception(Exception.class, repo.getBean(MyExceptionHandler.class));
    }

    private void generateData() throws Exception {
        final TransactionExecutor transactionExecutor = repo.getBean(TransactionExecutor.class);
        final ContextExecutor contextExecutor = repo.getBean(ContextExecutor.class);

        transactionExecutor.executeWithinTransaction(() -> contextExecutor.execute(() -> {

            final UserService userService = repo.getBean(UserService.class);
            final PasswordService passwordService = repo.getBean(PasswordService.class);
            final UserBO tino = new UserBO();
            tino.setName("tino");
            userService.save(tino);
            passwordService.createPassword(tino, "tinopw");

            final UserBO donnie = new UserBO();
            donnie.setName("donnie");
            userService.save(donnie);
            passwordService.createPassword(donnie, "donniepw");


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

    private <T extends Route> Route withTransactionAndUser(final Class<T> routeClass) {
        final T route = repo.getBean(routeClass);
        final LoginValidationRoute loginRoute = repo.getPrototypeBean(LoginValidationRoute::new, route);
        return repo.getPrototypeBean(TransactionDelegateRoute::new, loginRoute);
    }

    private <T extends Route> Route withTransaction(final Class<T> routeClass) {
        final T route = repo.getBean(routeClass);
        return repo.getPrototypeBean(TransactionDelegateRoute::new, route);
    }
}
