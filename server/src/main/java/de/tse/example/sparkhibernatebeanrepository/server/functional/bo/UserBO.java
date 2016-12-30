package de.tse.example.sparkhibernatebeanrepository.server.functional.bo;

import de.tse.example.sparkhibernatebeanrepository.server.technical.MyFilters;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;

@Entity
@Table(name = "tbluser")
@FilterDefs({
        @FilterDef(name = MyFilters.USER_FILTER,
                parameters = { @ParamDef(name = "NAME", type = "string") },
                defaultCondition = "owner = :NAME" // defaultCondition wird nicht angezogen. Warum?
        )
})
public class UserBO {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
