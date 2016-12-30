package de.tse.example.sparkhibernatebeanrepository.server.functional;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoListTO;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetDataRoute implements Route {

    private final InputInfoQueryService inputInfoQueryService;

    public GetDataRoute(final BeanAccessor beans) {
        this.inputInfoQueryService = beans.getBean(InputInfoQueryService.class);
    }

    @Override public InputInfoListTO handle(final Request request, final Response response) throws Exception {
        return inputInfoQueryService.listAll();
    }
}
