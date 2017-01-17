package de.tse.example.sparkhibernatebeanrepository.api.to;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class InputInfoTO {

    private LongProperty idProperty;
    private StringProperty dataProperty;
    private StringProperty ownerProperty;
    private ObjectProperty<LocalDateTime> createdProperty;

    public long getId() {
        return idProperty().get();
    }
    public void setId(long id) {
        idProperty().set(id);
    }
    public LongProperty idProperty() {
        if (idProperty == null) {
            idProperty = new SimpleLongProperty();
        }
        return idProperty;
    }

    public String getData() {
        return dataProperty().get();
    }
    public void setData(String data) {
        dataProperty().set(data);
    }
    public StringProperty dataProperty() {
        if (dataProperty == null) {
            dataProperty = new SimpleStringProperty();
        }
        return dataProperty;
    }

    public String getOwner() {
        return ownerProperty().get();
    }
    public void setOwner(String owner) {
        ownerProperty().set(owner);
    }
    public StringProperty ownerProperty() {
        if (ownerProperty == null) {
            ownerProperty = new SimpleStringProperty();
        }
        return ownerProperty;
    }

    public LocalDateTime getCreated() {
        return createdProperty().get();
    }
    public void setCreated(LocalDateTime created) {
        createdProperty().set(created);
    }
    public ObjectProperty<LocalDateTime> createdProperty() {
        if (createdProperty == null) {
            createdProperty = new SimpleObjectProperty<>();
        }
        return createdProperty;
    }

    @Override public String toString() {
        return "InputInfoTO{" +
                "idProperty=" + idProperty.get() +
                ", dataProperty=" + dataProperty.get() +
                ", ownerProperty=" + ownerProperty.get() +
                ", createdProperty=" + createdProperty.get() +
                '}';
    }
}
