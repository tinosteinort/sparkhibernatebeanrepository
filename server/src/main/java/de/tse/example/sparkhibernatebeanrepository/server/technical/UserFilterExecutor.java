package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import org.hibernate.SessionFactory;

import java.util.concurrent.Callable;

public class UserFilterExecutor {

    private final SessionFactory sessionFactory;

    public UserFilterExecutor(final BeanAccessor beans) {
        this.sessionFactory = beans.getBean(SessionFactory.class);
    }

    public <T> T execute(final String username, final Callable<T> code) throws Exception {
        try {
            sessionFactory.getCurrentSession().enableFilter(MyFilters.USER_FILTER).setParameter("NAME", username);
            return code.call();
        }
        finally {
            sessionFactory.getCurrentSession().disableFilter(MyFilters.USER_FILTER);
        }
    }
}
