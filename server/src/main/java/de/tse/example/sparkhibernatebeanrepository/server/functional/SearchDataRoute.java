package de.tse.example.sparkhibernatebeanrepository.server.functional;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.api.to.FilterTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoListTO;
import de.tse.example.sparkhibernatebeanrepository.server.technical.AbstractMarshallingRoute;

public class SearchDataRoute extends AbstractMarshallingRoute<FilterTO, InputInfoListTO> {

    private final InputInfoQueryService inputInfoQueryService;

    public SearchDataRoute(final BeanAccessor beans) {
        super(FilterTO.class, beans);
        this.inputInfoQueryService = beans.getBean(InputInfoQueryService.class);
    }

    @Override protected InputInfoListTO handleRequest(final FilterTO filter) {
        if (filter.getSearchValue() == null || "".equals(filter.getSearchValue())) {
            return inputInfoQueryService.listAll();
        }
        return inputInfoQueryService.findAllContaining(filter.getSearchValue());
    }
}
