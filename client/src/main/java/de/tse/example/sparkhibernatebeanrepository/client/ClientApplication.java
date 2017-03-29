package de.tse.example.sparkhibernatebeanrepository.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tinosteinort.beanrepository.BeanRepository;
import de.tse.example.sparkhibernatebeanrepository.client.base.*;
import de.tse.example.sparkhibernatebeanrepository.client.gui.LoginController;
import de.tse.example.sparkhibernatebeanrepository.client.gui.MainController;
import de.tse.example.sparkhibernatebeanrepository.client.services.CommandService;
import de.tse.example.sparkhibernatebeanrepository.client.services.LoginService;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.http.impl.client.CloseableHttpClient;

public class ClientApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(final Stage primaryStage) throws Exception {

        final Configuration config = new Configuration();
        config.setTrustStore(System.getProperty("truststore"));
        config.setTrustStorePassword(System.getProperty("truststorePassword").toCharArray());
        config.setBaseUrl(System.getProperty("baseUrl"));

        final BeanRepository repo = new BeanRepository.BeanRepositoryBuilder()
                .instance(primaryStage)
                .instance(config)
                .singleton(LoginController.class, LoginController::new)
                .singleton(MainController.class, MainController::new, GuiExecutor.class, CommandService.class)
                .singletonFactory(ObjectMapper.class, ObjectMapperFactory::new)
                .singletonFactory(CloseableHttpClient.class, HttpClientFactory::new, Configuration.class)
                .singleton(HttpService.class, HttpService::new, CloseableHttpClient.class, ObjectMapper.class)
                .singleton(LoginService.class, LoginService::new)
                .singleton(CommandService.class, CommandService::new)
                .singleton(GuiExecutor.class, GuiExecutor::new)
                .build();

        final LoginController controller = repo.getBean(LoginController.class);

        final Parent root = (Parent) controller.getView();
        final Scene scene = new Scene(root, 250, 120);

        primaryStage.setTitle("Client Application Login");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(scene.getWidth());
        primaryStage.setMinHeight(scene.getHeight());
        primaryStage.show();
    }
}
