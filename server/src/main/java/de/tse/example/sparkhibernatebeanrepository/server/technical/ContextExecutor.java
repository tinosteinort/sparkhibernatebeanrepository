package de.tse.example.sparkhibernatebeanrepository.server.technical;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.UserBO;
import de.tse.example.sparkhibernatebeanrepository.server.functional.UserService;

import java.util.concurrent.Callable;

public class ContextExecutor {

    private static final Context SYSTEM = new Context("SYSTEM");

    private final UserFilterExecutor userFilterExecutor;
    private final UserService userService;

    public ContextExecutor(final BeanAccessor beans) {
        this.userFilterExecutor = beans.getBean(UserFilterExecutor.class);
        this.userService = beans.getBean(UserService.class);
    }

    private void validate(final String name) throws Exception {
        final UserBO user = userService.loadByName(name);
        if (user == null) {
            throw new RuntimeException("invalid user");
        }
    }

    public <T> T execute(final String name, final Callable<T> code) throws Exception {

        if (Context.get() != null) {
            throw new IllegalStateException("Nested Contexts not allowed");
        }

        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("name must not be null or empty");
        }

        validate(name);

        try {
            Context.set(new Context(name));
            return userFilterExecutor.execute(name, code::call);
        }
        finally {
            Context.clear();
        }
    }

    public <T> T execute(final Callable<T> code) throws Exception {

        if (Context.get() != null) {
            throw new IllegalStateException("Nested Contexts not allowed");
        }

        try {
            Context.set(SYSTEM);
            return code.call();
        }
        finally {
            Context.clear();
        }
    }
}
