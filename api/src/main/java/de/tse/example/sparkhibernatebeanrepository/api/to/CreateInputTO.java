package de.tse.example.sparkhibernatebeanrepository.api.to;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CreateInputTO {

    private StringProperty inputProperty;

    public CreateInputTO() {

    }

    public CreateInputTO(final String input) {
        setInput(input);
    }

    public String getInput() {
        return inputProperty().get();
    }
    public void setInput(String input) {
        inputProperty().setValue(input);
    }
    public StringProperty inputProperty() {
        if (inputProperty == null) {
            inputProperty = new SimpleStringProperty();
        }
        return inputProperty;
    }

    @Override public String toString() {
        return "CreateInputTO{" +
                "input='" + inputProperty.get() + '\'' +
                '}';
    }
}
