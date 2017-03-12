package de.tse.example.sparkhibernatebeanrepository.server.functional;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoListTO;
import spark.Request;
import spark.Response;
import spark.Route;

public class SearchDataRoute implements Route {

    public static final String SEARCH_VALUE = ":searchvalue";

    private final InputInfoQueryService inputInfoQueryService;

    public SearchDataRoute(final BeanAccessor beans) {
        this.inputInfoQueryService = beans.getBean(InputInfoQueryService.class);
    }

    @Override public InputInfoListTO handle(final Request request, final Response response) throws Exception {
        final String searchValue = request.params(SEARCH_VALUE);
        if ("".equals(searchValue) || searchValue == null) {
            throw new IllegalArgumentException("Parameter for search required");
        }
        return inputInfoQueryService.findAllContaining(searchValue);
    }
}
