package de.tse.example.sparkhibernatebeanrepository.client.services;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.api.base.AuthenticationStatus;
import de.tse.example.sparkhibernatebeanrepository.api.base.FormParams;
import de.tse.example.sparkhibernatebeanrepository.client.base.HttpService;
import de.tse.example.sparkhibernatebeanrepository.client.base.ServiceUrlProvider;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginService {

    private final HttpService httpService;
    private final ServiceUrlProvider urlProvider;

    public LoginService(final BeanAccessor beans) {
        this.httpService = beans.getBean(HttpService.class);
        this.urlProvider = beans.getPrototypeBean(ServiceUrlProvider::new, "login");
    }

    public AuthenticationStatus login(final String name, final String password) {
        try {
            final List<NameValuePair> credentials = new ArrayList<>(2);
            credentials.add(new BasicNameValuePair(FormParams.USERNAME, name));
            credentials.add(new BasicNameValuePair(FormParams.PASSWORD, password));
            return httpService.postForm(urlProvider.provide(), credentials, AuthenticationStatus.class);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
