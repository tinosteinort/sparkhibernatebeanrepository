package de.tse.example.sparkhibernatebeanrepository.client.base;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GuiExecutor {

    public <T> void execute(final Supplier<T> asyncCode, final Consumer<T> onSuccess) {
        executeIntern(asyncCode, (T result) -> {
            if (onSuccess != null) {
                onSuccess.accept(result);
            }
        });
    }

    public void execute(final Runnable asyncCode, final Runnable onSuccess) {
        execute(() -> {
            asyncCode.run();
            return null;
        }, (Void result) -> {
            if (onSuccess != null) {
                onSuccess.run();
            }
        });
    }

    private <T> void executeIntern(final Supplier<T> supplier, final Consumer<T> onSuccess) {
        final Service<T> service = new Service<T>() {
            @Override protected Task<T> createTask() {
                return new Task<T>() {
                    @Override protected T call() throws Exception {
                        return supplier.get();
                    }

                    @Override protected void succeeded() {
                        onSuccess.accept(getValue());
                    }
                };
            }
        };
        // Does not work, fist time the Service is executed. Bug in JDK?
//        service.setOnSucceeded(event -> {
//            onSuccess.accept(service.getValue());
//        });
//        service.setOnFailed(event -> {
//            throw new RuntimeException("Async Service Call failed", service.getException());
//        });

        service.start();
    }
}
