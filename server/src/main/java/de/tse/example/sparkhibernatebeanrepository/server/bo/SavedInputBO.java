package de.tse.example.sparkhibernatebeanrepository.server.bo;

import de.tse.example.sparkhibernatebeanrepository.server.base.MyFilters;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tblsavedinput")
@Filters(
        @Filter(name = MyFilters.USER_FILTER, condition = "owner = :NAME") // defaultCondition der FilterDef wird nicht angezogen. Warum?
)
public class SavedInputBO {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "data")
    private String data;

    @Column(name = "owner")
    private String owner;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

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
}
