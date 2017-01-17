package de.tse.example.sparkhibernatebeanrepository.client.gui;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.client.base.CredentialProvider;
import de.tse.example.sparkhibernatebeanrepository.client.base.FxmlController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends FxmlController implements Initializable {

    private final BeanAccessor beans;
    private final Stage stage;
    private final CredentialProvider credentialProvider;

    @FXML private TextField nameField;
    private final StringProperty name = new SimpleStringProperty();

    public LoginController(final BeanAccessor beans) {
        this.beans = beans;
        this.stage = beans.getBean(Stage.class);
        this.credentialProvider = beans.getBean(CredentialProvider.class);
    }

    @Override public void initialize(final URL location, final ResourceBundle resources) {
        nameField.textProperty().bindBidirectional(name);
    }

    @FXML public void doLogin() {
        if (nameField != null && !"".equals(name.get())) {
            credentialProvider.setName(name.get());
            openMainController();
        }
    }

    private void openMainController() {

        final MainController controller = beans.getBean(MainController.class);
        final Parent root = (Parent) controller.getView();
        final Scene scene = new Scene(root, 600, 500);

        stage.setTitle("Client Application");
        stage.setScene(scene);
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
        stage.centerOnScreen();

        controller.init();
    }

    @Override protected String getFxml() {
        return "LoginController.fxml";
    }
}
