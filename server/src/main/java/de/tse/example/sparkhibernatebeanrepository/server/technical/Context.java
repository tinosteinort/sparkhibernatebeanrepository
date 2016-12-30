package de.tse.example.sparkhibernatebeanrepository.server.technical;

public class Context {

    private static final ThreadLocal<Context> threadLocalContext = new ThreadLocal<>();

    static void set(final Context context) {
        threadLocalContext.set(context);
    }
    public static Context get() {
        return threadLocalContext.get();
    }
    static void clear() {
        threadLocalContext.remove();
    }


    private final String name;

    Context(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
