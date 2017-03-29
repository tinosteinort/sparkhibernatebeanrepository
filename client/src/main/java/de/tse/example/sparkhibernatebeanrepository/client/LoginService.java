package de.tse.example.sparkhibernatebeanrepository.client;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.api.to.AuthenticationStatus;
import de.tse.example.sparkhibernatebeanrepository.client.base.HttpService;
import de.tse.example.sparkhibernatebeanrepository.client.base.ServiceUrlProvider;

import java.io.IOException;

public class LoginService {

    private final HttpService httpService;
    private final ServiceUrlProvider urlProvider;

    public LoginService(final BeanAccessor beans) {
        this.httpService = beans.getBean(HttpService.class);
        this.urlProvider = beans.getPrototypeBean(ServiceUrlProvider::new, "login");
    }

    public AuthenticationStatus login(final String name, final String password) {
        try {
            return httpService.post(urlProvider.provide(), namePasswordCombination(name, password), AuthenticationStatus.class);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String namePasswordCombination(final String name, final String password) {
        // TODO transfer infos in other format. FormSubmit? Base64 encoded?
        return name + ":" + password;
    }
}
