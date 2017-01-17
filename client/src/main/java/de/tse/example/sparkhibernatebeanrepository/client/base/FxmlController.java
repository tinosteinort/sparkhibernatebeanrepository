package de.tse.example.sparkhibernatebeanrepository.client.base;

import com.github.tinosteinort.beanrepository.BeanRepository;
import com.github.tinosteinort.beanrepository.PostConstructible;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public abstract class FxmlController implements PostConstructible {

    private Node view;

    protected abstract  String getFxml();

    public Node getView() {
        return view;
    }

    @Override public void onPostConstruct(BeanRepository beanRepository) {
        try {
            view = loadFxml();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected Node loadFxml() throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setController(this); // Works only, if no Controller is defined in FXML File
        loader.setLocation(getClass().getResource(getFxml()));
        return loader.load();
    }
}
