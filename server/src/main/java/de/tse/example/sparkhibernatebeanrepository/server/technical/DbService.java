package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public abstract class DbService {

    private final SessionFactory sessionFactory;

    public DbService(final BeanAccessor beans) {
        this.sessionFactory = beans.getBean(SessionFactory.class);
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected <T> T uniqueResult(final Query query) {
        final List<T> result = query.list();
        switch (result.size()) {
            case 0: return null;
            case 1: return result.get(0);
            default: throw new RuntimeException("unique result expected");
        }
    }
}
