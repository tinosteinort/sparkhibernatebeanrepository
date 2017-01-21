package de.tse.example.sparkhibernatebeanrepository.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tinosteinort.beanrepository.BeanRepository;
import de.tse.example.sparkhibernatebeanrepository.client.base.*;
import de.tse.example.sparkhibernatebeanrepository.client.gui.LoginController;
import de.tse.example.sparkhibernatebeanrepository.client.gui.MainController;
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
        config.setTrustStore("certs/localhost-serverkeystore");
        config.setTrustStorePassword("changeit".toCharArray());

        final BeanRepository repo = new BeanRepository.BeanRepositoryBuilder()
                .instance(primaryStage)
                .instance(config)
                .singleton(LoginController.class, LoginController::new)
                .singleton(MainController.class, MainController::new, GuiExecutor.class, ServiceClient.class)
                .singleton(CredentialProvider.class, CredentialProvider::new)
                .singletonFactory(ObjectMapper.class, ObjectMapperTestFactory::new)
                .singletonFactory(CloseableHttpClient.class, HttpClientTestFactory::new, Configuration.class)
                .singleton(HttpService.class, HttpService::new, CloseableHttpClient.class, ObjectMapper.class)
                .singleton(ServiceClient.class, ServiceClient::new, HttpService.class, CredentialProvider.class)
                .singleton(GuiExecutor.class, GuiExecutor::new)
                .build();

        repo.getBean(ServiceClient.class);

        final LoginController controller = repo.getBean(LoginController.class);

        final Parent root = (Parent) controller.getView();
        final Scene scene = new Scene(root, 300, 100);

        primaryStage.setTitle("Client Application Login");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(scene.getWidth());
        primaryStage.setMinHeight(scene.getHeight());
        primaryStage.show();
    }
}
