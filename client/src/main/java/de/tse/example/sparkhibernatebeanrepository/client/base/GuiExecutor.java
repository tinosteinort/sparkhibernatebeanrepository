package de.tse.example.sparkhibernatebeanrepository.client.base;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GuiExecutor {

    public <T> void execute(final Supplier<T> asyncCode, final Consumer<T> onSuccess) {
        execute(new Task<T>() {
            @Override protected T call() throws Exception {
                return asyncCode.get();
            }
        }, (T result) -> {
            if (onSuccess != null) {
                onSuccess.accept(result);
            }
        });
    }

    public void execute(final Runnable asyncCode, final Runnable onSuccess) {
        execute(new Task<Void>() {
            @Override protected Void call() throws Exception {
                asyncCode.run();
                return null;
            }
        }, (Void result) -> {
            if (onSuccess != null) {
                onSuccess.run();
            }
        });
    }

    private <T> void execute(final Task<T> task, final Consumer<T> onSuccess) {
        final Service<T> service = new Service<T>() {
            @Override protected Task<T> createTask() {
                return task;
            }
        };
        service.setOnSucceeded(event -> {
            onSuccess.accept(service.getValue());
        });
        service.setOnFailed(event -> {
            throw new RuntimeException("Async Service Call failed", service.getException());
        });

        service.start();
    }
}
