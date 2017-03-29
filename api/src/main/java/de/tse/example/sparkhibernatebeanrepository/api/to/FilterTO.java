package de.tse.example.sparkhibernatebeanrepository.api.to;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FilterTO {

    private StringProperty searchValueProperty;

    public String getSearchValue() {
        return searchValueProperty().get();
    }
    public void setSearchValue(String searchValue) {
        searchValueProperty().set(searchValue);
    }
    public StringProperty searchValueProperty() {
        if (searchValueProperty == null) {
            searchValueProperty = new SimpleStringProperty();
        }
        return searchValueProperty;
    }
}
