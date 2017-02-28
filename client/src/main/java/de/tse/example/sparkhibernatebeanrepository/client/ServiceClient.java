package de.tse.example.sparkhibernatebeanrepository.client;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoListTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoTO;
import de.tse.example.sparkhibernatebeanrepository.client.base.HttpService;
import de.tse.example.sparkhibernatebeanrepository.client.base.ServiceUrlProvider;

import java.io.IOException;

public class ServiceClient {

    private final HttpService httpService;
    private final ServiceUrlProvider urlProvider;

    public ServiceClient(final BeanAccessor beans) {
        this.httpService = beans.getBean(HttpService.class);
        this.urlProvider = beans.getBean(accessor -> new ServiceUrlProvider(accessor, "data"));
    }

    public InputInfoListTO getInputInfos() {
        try {
            return httpService.get(urlProvider.provide(), InputInfoListTO.class);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public InputInfoTO create(final CreateInputTO input) {
        try {
            return httpService.post(urlProvider.provide(), input, InputInfoTO.class);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete(final InputInfoTO input) {
        try {
            httpService.delete(urlProvider.provide(String.valueOf(input.getId())));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
