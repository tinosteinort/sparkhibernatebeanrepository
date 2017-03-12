package de.tse.example.sparkhibernatebeanrepository.server.functional;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.SavedInputBO;
import spark.Request;
import spark.Response;
import spark.Route;

public class DeleteDataRoute implements Route {

    public static final String DELETE_ID = ":id";

    private final InputService inputService;

    public DeleteDataRoute(final BeanAccessor beans) {
        this.inputService = beans.getBean(InputService.class);
    }

    @Override public Object handle(final Request request, final Response response) throws Exception {
        final long id = Long.valueOf(request.params(DELETE_ID));

        final SavedInputBO load = inputService.load(id);
        if (load != null) {
            inputService.delete(load);
        }

        return null;
    }
}
