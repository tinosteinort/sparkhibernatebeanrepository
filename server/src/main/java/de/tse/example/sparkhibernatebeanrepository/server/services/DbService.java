package de.tse.example.sparkhibernatebeanrepository.server.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class DbService {

    private final SessionFactory sessionFactory;

    public DbService(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    public <T> void save(final T obj) {
        currentSession().save(obj);
    }

    public Query createQuery(final String queryString) {
        return currentSession().createQuery(queryString);
    }

    public <T> T uniqueResult(final Query query) {
        final List<T> result = query.list();
        switch (result.size()) {
            case 0: return null;
            case 1: return result.get(0);
            default: throw new RuntimeException("unique result expected");
        }
    }

    public <T> List<T> list(final Query query) {
        return query.list();
    }

    public <T> T loadById(final Class<T> cls, final long id) {
        final Query query = createQuery("from " + cls.getSimpleName() + " where id=:ID");
        query.setParameter("ID", id);
        return uniqueResult(query);
    }

    public <T> List<T> list(final String queryString) {
        final Query query = currentSession().createQuery(queryString);
        return query.list();
    }

    public <T> void delete(final T object) {
        currentSession().delete(object);
    }
}
