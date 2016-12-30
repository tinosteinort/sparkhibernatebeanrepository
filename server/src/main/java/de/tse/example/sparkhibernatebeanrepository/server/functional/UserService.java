package de.tse.example.sparkhibernatebeanrepository.server.functional;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.UserBO;
import de.tse.example.sparkhibernatebeanrepository.server.technical.DbService;
import org.hibernate.query.Query;

public class UserService extends DbService {

    public UserService(final BeanAccessor beans) {
        super(beans);
    }

    public void save(final UserBO user) {
        getSession().save(user);
    }

    public UserBO loadByName(final String name) {
        final Query query = getSession().createQuery("FROM UserBO where name=:NAME");
        query.setParameter("NAME", name);
        return uniqueResult(query);
    }
}
