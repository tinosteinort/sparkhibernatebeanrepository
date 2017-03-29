package de.tse.example.sparkhibernatebeanrepository.server.services;

import de.tse.example.sparkhibernatebeanrepository.server.bo.PasswordBO;
import de.tse.example.sparkhibernatebeanrepository.server.bo.UserBO;
import org.hibernate.query.Query;

public class PasswordService {

    private final DbService dbService;
    private final PasswordHashService passwordHashService;

    public PasswordService(final DbService dbService, final PasswordHashService passwordHashService) {
        this.dbService = dbService;
        this.passwordHashService = passwordHashService;
    }

    public boolean credentialsAreValid(final String user, final String password) {
        return passwordIsValid(loadPassword(user), password);
    }

    private boolean passwordIsValid(final PasswordBO expected, final String password) {
        return expected != null &&
                expected.getPassword().equals(passwordHashService.calculateHash(expected.getUser(), password));
    }

    private PasswordBO loadPassword(final String user) {
        final Query query = dbService.createQuery("select pw from PasswordBO pw " +
                "inner join pw.user as user " +
                "where user.name = :NAME");
        query.setParameter("NAME", user);

        return dbService.uniqueResult(query);
    }

    public void createPassword(final UserBO user, final String password) {
        if (loadPassword(user.getName()) != null) {
            throw new IllegalArgumentException("Password already set");
        }

        final PasswordBO pw = new PasswordBO();
        pw.setUser(user);
        pw.setPassword(passwordHashService.calculateHash(user, password));

        dbService.save(pw);
    }
}
