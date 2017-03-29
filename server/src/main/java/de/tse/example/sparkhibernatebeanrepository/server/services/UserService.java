package de.tse.example.sparkhibernatebeanrepository.server.services;

import de.tse.example.sparkhibernatebeanrepository.server.bo.UserBO;
import org.hibernate.query.Query;

public class UserService {

    private final DbService dbService;

    public UserService(final DbService dbService) {
        this.dbService = dbService;
    }

    public void save(final UserBO user) {
        dbService.save(user);
    }

    public UserBO loadByName(final String name) {
        final Query query = dbService.createQuery("from UserBO where name=:NAME");
        query.setParameter("NAME", name);
        return dbService.uniqueResult(query);
    }
}
