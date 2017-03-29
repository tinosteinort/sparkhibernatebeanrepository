package de.tse.example.sparkhibernatebeanrepository.server.functional.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tblpassword")
public class PasswordBO {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @OneToOne
    private UserBO user;

    @Column(name = "password")
    private String password;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public UserBO getUser() {
        return user;
    }
    public void setUser(UserBO user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
