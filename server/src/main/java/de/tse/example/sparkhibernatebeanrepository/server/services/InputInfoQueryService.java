package de.tse.example.sparkhibernatebeanrepository.server.services;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.server.bo.SavedInputBO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoListTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoTO;

public class InputInfoQueryService {

    private final InputService inputService;

    public InputInfoQueryService(final BeanAccessor beans) {
        this.inputService = beans.getBean(InputService.class);
    }

    public InputInfoListTO listAll() {
        final InputInfoListTO inputInfos = new InputInfoListTO();
        for (SavedInputBO savedInput : inputService.listAll()) {
            inputInfos.getInputInfos().add(map(savedInput));
        }
        return inputInfos;
    }

    public InputInfoListTO findAllContaining(final String searchValue) {
        final InputInfoListTO searchResult = new InputInfoListTO();
        for (SavedInputBO savedInput : inputService.findAllContaining(searchValue)) {
            searchResult.getInputInfos().add(map(savedInput));
        }
        return searchResult;
    }

    public InputInfoTO map(final SavedInputBO savedInput) {
        final InputInfoTO info = new InputInfoTO();
        info.setId(savedInput.getId());
        info.setData(savedInput.getData());
        info.setCreated(savedInput.getCreated());
        info.setOwner(savedInput.getOwner());
        return info;
    }
}
