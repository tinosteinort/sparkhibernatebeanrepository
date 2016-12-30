package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;

import java.util.concurrent.Callable;

public class TransactionExecutor {

    private final SessionFactory sessionFactory;

    public TransactionExecutor(final BeanAccessor beans) {
        this.sessionFactory = beans.getBean(SessionFactory.class);
    }

    public <T> T executeWithinTransaction(final Callable<T> code) throws Exception {
        final Session session = sessionFactory.openSession();

        final boolean openSession = ManagedSessionContext.hasBind(sessionFactory);

        if (!openSession) {
            ManagedSessionContext.bind(session);
        }
        try {
            return execute(session, code);
        }
        finally {
            if (!openSession) {
                session.close();
                ManagedSessionContext.unbind(session.getSessionFactory());
            }
        }
    }

    private static <T> T execute(final Session session, final Callable<T> code) throws Exception {
        try {
            session.beginTransaction();
            final T result = code.call();
            session.getTransaction().commit();
            return result;
        }
        catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }
}
