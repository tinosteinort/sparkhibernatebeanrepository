package de.tse.example.sparkhibernatebeanrepository.server;

import com.github.tinosteinort.beanrepository.BeanRepository;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.server.base.BeanRepositoryBootstrap;
import de.tse.example.sparkhibernatebeanrepository.server.base.Configuration;
import de.tse.example.sparkhibernatebeanrepository.server.base.ContextExecutor;
import de.tse.example.sparkhibernatebeanrepository.server.base.SparkWrapper;
import de.tse.example.sparkhibernatebeanrepository.server.base.TransactionExecutor;
import de.tse.example.sparkhibernatebeanrepository.server.bo.UserBO;
import de.tse.example.sparkhibernatebeanrepository.server.services.InputService;
import de.tse.example.sparkhibernatebeanrepository.server.services.PasswordService;
import de.tse.example.sparkhibernatebeanrepository.server.services.UserService;

public class Start {

    private final BeanRepository repo;

    public Start() {
        final Configuration configuration = new Configuration();
        configuration.setKeystoreFile(System.getProperty("keystore"));
        configuration.setKeystorePassword(System.getProperty("keystorePassword"));
        configuration.setPort(Integer.valueOf(System.getProperty("port")));
        configuration.setJwtPassword("M31_C0mpl1kaetet#Pathwhaat");

        this.repo = new BeanRepositoryBootstrap().bootstrap(configuration);
    }

    public static void main(String[] args) throws Exception {
        new Start().start();
    }

    public void start() throws Exception {
        generateData();

        repo.getBean(SparkWrapper.class).configureAndRun();
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
}
