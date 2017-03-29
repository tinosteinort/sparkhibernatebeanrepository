package de.tse.example.sparkhibernatebeanrepository.server.functional;

import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.PasswordBO;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.UserBO;
import de.tse.example.sparkhibernatebeanrepository.server.technical.DbService;
import org.hibernate.query.Query;

public class PasswordService {

    private final DbService dbService;

    public PasswordService(final DbService dbService) {
        this.dbService = dbService;
    }

    public boolean credentialsAreValid(final String user, final String password) {
        return passwordIsValid(loadPassword(user), password);
    }

    private boolean passwordIsValid(final PasswordBO expected, final String password) {
        if (expected == null) {
            return false;
        }
        if (!expected.getPassword().equals(calculatePassword(password))) {
            return false;
        }
        return true;
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
        pw.setPassword(calculatePassword(password));

        dbService.save(pw);
    }

    private String calculatePassword(final String pw) {
        return "##PW_" + pw + "_WP##";
    }
}
