package de.tse.example.sparkhibernatebeanrepository.server.functional;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.server.functional.bo.SavedInputBO;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoTO;
import de.tse.example.sparkhibernatebeanrepository.server.technical.AbstractMarshallingRoute;
import de.tse.example.sparkhibernatebeanrepository.server.technical.Context;

public class CreateDataRoute extends AbstractMarshallingRoute<CreateInputTO, InputInfoTO> {

    private final InputService inputService;
    private final InputInfoQueryService inputInfoQueryService;

    public CreateDataRoute(final BeanAccessor beans) {
        super(CreateInputTO.class, beans);
        this.inputService = beans.getBean(InputService.class);
        this.inputInfoQueryService = beans.getBean(InputInfoQueryService.class);
    }

    @Override protected InputInfoTO handleRequest(final CreateInputTO input) {
        final SavedInputBO savedInput = inputService.create(input, Context.get().getName());
        return inputInfoQueryService.map(savedInput);
    }
}
