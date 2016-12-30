package de.tse.example.sparkhibernatebeanrepository.api.to;

import java.time.LocalDateTime;

public class InputInfoTO {

    private String data;
    private String owner;
    private LocalDateTime created;

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override public String toString() {
        return "InputInfoTO{" +
                "data='" + data + '\'' +
                ", owner='" + owner + '\'' +
                ", created=" + created +
                '}';
    }
}
