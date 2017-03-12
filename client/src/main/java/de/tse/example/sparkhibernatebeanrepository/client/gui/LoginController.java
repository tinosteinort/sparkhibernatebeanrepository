package de.tse.example.sparkhibernatebeanrepository.client.gui;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.api.to.AuthenticationStatus;
import de.tse.example.sparkhibernatebeanrepository.client.LoginService;
import de.tse.example.sparkhibernatebeanrepository.client.base.FxmlController;
import de.tse.example.sparkhibernatebeanrepository.client.base.GuiExecutor;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class LoginController extends FxmlController implements Initializable {

    private final BeanAccessor beans;
    private final Stage stage;
    private final GuiExecutor guiExecutor;
    private final LoginService loginService;

    @FXML private TextField nameField;
    private final StringProperty name = new SimpleStringProperty();

    public LoginController(final BeanAccessor beans) {
        this.beans = beans;
        this.stage = beans.getBean(Stage.class);
        this.guiExecutor = beans.getBean(GuiExecutor.class);
        this.loginService = beans.getBean(LoginService.class);
    }

    @Override public void initialize(final URL location, final ResourceBundle resources) {
        nameField.textProperty().bindBidirectional(name);
    }

    @FXML public void doLogin() {
        if (nameField != null && !"".equals(name.get())) {

            login(name.get(), (AuthenticationStatus status) -> {
                        switch (status) {
                            case AUTHENTICATED:
                                openMainController();
                                break;
                            default:
                                showAuthenticationErrorDialog();
                                break;
                        }
                    }
            );
        }
    }

    private void login(final String name, final Consumer<AuthenticationStatus> consumer) {
        guiExecutor.execute(
                () -> loginService.login(name),
                consumer
        );
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

    private void showAuthenticationErrorDialog() {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText("Authentifizierung fehlgeschlagen");
        alert.setContentText(
                "Anmeldung konnte nicht durchgeführt werden.\nBitte Benutzerkennung und Passwort prüfen.");

        alert.showAndWait();
    }

    @Override protected String getFxml() {
        return "LoginController.fxml";
    }
}
